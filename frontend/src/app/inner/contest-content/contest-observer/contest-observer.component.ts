import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'contest-observer',
  templateUrl: './contest-observer.component.html',
  styleUrls: ['./contest-observer.component.css']
})
export class InnerContentContestObserver {

  contestId!: string;
  betMap = new Map<number, any>();
  private subscription: Subscription | undefined;
  thisUserRole!: string;
  isUserAllowed!: boolean;

  onWrongOpponentLoginError: Subject<void> = new Subject<void>();
  onWrongJudgeLoginError: Subject<void> = new Subject<void>();
  onWrongResolveError: Subject<void> = new Subject<void>();

  waiting!: boolean;
  item!: any;

  creatorProfile!: any;
  opponentProfile!: any;
  judgeProfile!: any;

  constructor(private route: ActivatedRoute, private axiosService: AxiosService, private router: Router) {
    this.subscription = route.params.subscribe(params => this.contestId = params["id"]);
  }

  ngOnInit() {
    this.waiting = true;
    this.getBetById(this.contestId)
    this.userAllowedCheck(this.contestId);
  }

  creatorId() {
    if (this.creatorProfile != null) {
      return true;
    } else {
      return false;
    }
  }

  opponentId() {
    if (this.opponentProfile != null) {
      return true;
    } else {
      return false;
    }
  }

  judgeId() {
    if (this.judgeProfile != null) {
      return true;
    } else {
      return false;
    }
  }

  isResolved() {
    if (this.item.winner != null) {
      return true;
    }
    return false;
  }

  ifCreatorWinner() {
    if (this.creatorId()) {
      if (this.item.winner === this.creatorProfile.id) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  ifOpponentWinner() {
    if (this.opponentId()) {
      if (this.item.winner === this.opponentProfile.id) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  isFinished() {
    if (this.item.size === this.item.currentSize) {
      return true;
    } else {
      return false;
    }
  }

  getBetById(id: string) {
    this.axiosService.request(
      "GET",
      "/getContestById?contestId=" + id, {})
      .then(
        response => {
          this.item = response.data;
          this.betMap = this.item.betMap;
          this.setCreatorProfile(this.item.creator);
          if (this.item.opponent != null) {
            this.setOpponentProfile(this.item.opponent);
          }
          if (this.item.judge != null) {
            this.setJudgeProfile(this.item.judge);
          }
        }).catch(
          error => {
            if (error.response.status === 404) {
              this.router.navigate(['/app/404']);
            }
          }
        );
  }

  setCreatorProfile(id: string | undefined) {
    this.axiosService.request(
      "GET",
      "/getProfileById?userId=" + id, {})
      .then(
        response => {
          this.creatorProfile = response.data;
        }).catch(
          error => {
          }
        );
  }

  setOpponentProfile(id: string | undefined) {
    this.axiosService.request(
      "GET",
      "/getProfileById?userId=" + id, {})
      .then(
        response => {
          this.opponentProfile = response.data;
        }).catch(
          error => {
          }
        );
  }

  setJudgeProfile(id: string | undefined) {
    this.axiosService.request(
      "GET",
      "/getProfileById?userId=" + id, {})
      .then(
        response => {
          this.judgeProfile = response.data;
        }).catch(
          error => {
          }
        );
  }

  userAllowedCheck(id: string | undefined) {
    this.axiosService.request(
      "GET",
      "/getContestRole?contestId=" + id, {})
      .then(
        response => {
          this.waiting = false;
          if (response.data === 'none') {
            this.isUserAllowed = false;
          } else {
            this.isUserAllowed = true;
          }
          this.thisUserRole = response.data;
        }).catch(
          error => {
          }
        );
  }

  showOpponentAlert() {
    this.onWrongOpponentLoginError.next();
  }

  showJudgeAlert() {
    this.onWrongJudgeLoginError.next();
  }

  showResolveAlert() {
    this.onWrongResolveError.next();
  }

}
