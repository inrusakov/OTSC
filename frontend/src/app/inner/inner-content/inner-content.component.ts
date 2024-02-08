import { Component } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';
import { InnerContentChallenge } from './challenge/challenge.component';

@Component({
  selector: 'inner-content',
  templateUrl: './inner-content.component.html',
  styleUrls: ['./inner-content.component.css']
})
export class InnerContentComponent {

  constructor(private axiosService: AxiosService) { }

  challenges!: any;
  waiting!: boolean;

  obsActive: boolean = false;
  currentChallenge!: any;

  ngOnInit(): void {
    this.waiting = true;
    this.axiosService.request(
      "GET",
      "/currentUserBets", {})
      .then(
        response => {
          this.waiting = false;
          this.challenges = response.data;
        }).catch(
          error => {

          }
        );
  }

  proceedToObserver(challenge: any) {
    this.obsActive = true;
    this.currentChallenge = challenge;
  }

  exitObserver(){
    this.obsActive = false;
    this.currentChallenge = null;
  }

}
