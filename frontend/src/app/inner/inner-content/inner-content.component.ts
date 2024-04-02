import { Component } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'inner-content',
  templateUrl: './inner-content.component.html',
  styleUrls: ['./inner-content.component.css']
})
export class InnerContentComponent {

  constructor(private axiosService: AxiosService) { }

  challenges!: any;
  challengesParticipating!: any;

  waitingCreated!: boolean;
  waitingPart!: boolean;

  ngOnInit(): void {
    this.waitingCreated = true;
    this.waitingPart = true;
    this.axiosService.request(
      "GET",
      "/currentUserBets", {})
      .then(
        response => {
          this.waitingCreated = false;
          this.challenges = response.data;
        }).catch(
          error => {

          }
        );

    this.axiosService.request(
      "GET",
      "/userParticipatingBets", {})
      .then(
        response => {
          this.waitingPart = false;
          this.challengesParticipating = response.data;
        }).catch(
          error => {

          }
        );
  }

}
