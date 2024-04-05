import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, Subscription } from 'rxjs';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'challenge-observer',
  templateUrl: './challenge-observer.component.html',
  styleUrls: ['./challenge-observer.component.css']
})
export class InnerContentChallengeObserver {

  betId!: number;
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
    this.subscription = route.params.subscribe(params => this.betId = params["id"]);
  }

  ngOnInit() {
    this.waiting = true;
    this.getBetById(this.betId)
    this.userAllowedCheck(this.betId);
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

  isResolved(){
    if(this.item.winner != null){
      return true;
    }
    return false;
  }

  ifCreatorWinner(){
    if(this.creatorId()){
      if(this.item.winner === this.creatorProfile.id){
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  ifOpponentWinner(){
    if(this.opponentId()){
      if(this.item.winner === this.opponentProfile.id){
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  getDate(date: any) {
    return date.join('-')
  }

  getBetById(id: number) {
    this.axiosService.request(
      "GET",
      "/getBetById?betId=" + id, {})
      .then(
        response => {
          
          this.item = response.data;
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

  setCreatorProfile(id: number | undefined) {
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

  setOpponentProfile(id: number | undefined) {
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

  setJudgeProfile(id: number | undefined) {
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

  userAllowedCheck(id: number | undefined) {
    this.axiosService.request(
      "GET",
      "/getRole?betId="+id, {})
      .then(
        response => {
          this.waiting = false;
          if(response.data === 'none'){
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

  showOpponentAlert(){
    this.onWrongOpponentLoginError.next();
  }

  showJudgeAlert(){
    this.onWrongJudgeLoginError.next();
  }

  showResolveAlert(){
    this.onWrongResolveError.next();
  }

}
