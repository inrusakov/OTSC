import { Component, EventEmitter, Output } from '@angular/core';
import { NgbPaginationConfig } from '@ng-bootstrap/ng-bootstrap';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'contest-content',
  templateUrl: './contest-content.component.html',
  styleUrls: ['./contest-content.component.css'],
})
export class ContestContentComponent {

  constructor(private axiosService: AxiosService, config: NgbPaginationConfig) {
    config.size = 'md';
    config.boundaryLinks = true;
  }

  challengesCreated!: any;
  challengesOpponent!: any;
  challengesJudge!: any;

  waitingCreated!: boolean;
  waitingOpponent!: boolean;
  waitingJudge!: boolean;

  createdEmpty: boolean = false;
  opponentEmpty: boolean = false;
  judgeEmpty: boolean = false;

  start!: number;
  end!: number;
  page!: number;
  pageSize!: number;
  maxSize!: number;

  updateStartAndEnd() {
    this.start = (this.page - 1) * this.pageSize;
    this.end = this.page * this.pageSize;
  }

  identify(index: any, item: { id: any; }) {
    return item.id;
  }

  ngOnInit(): void {
    this.page = 1;
    this.pageSize = 3;
    this.maxSize = 8;
    this.updateStartAndEnd();

    this.waitingCreated = true;
    this.waitingOpponent = true;
    this.waitingJudge = true;

    this.axiosService.request(
      "GET",
      "/myCreatedContests", {})
      .then(
        response => {
          this.waitingCreated = false;
          this.challengesCreated = response.data;
          if (this.challengesCreated.length < 1) {
            this.createdEmpty = true;
          }
        }).catch(
          error => {
          }
        );

    this.axiosService.request(
      "GET",
      "/myOpponentContests", {})
      .then(
        response => {
          this.waitingOpponent = false;
          this.challengesOpponent = response.data;
          if (this.challengesOpponent.length < 1) {
            this.opponentEmpty = true;
          }
        }).catch(
          error => {
          }
        );

    this.axiosService.request(
      "GET",
      "/myJudgeContests", {})
      .then(
        response => {
          this.waitingJudge = false;
          this.challengesJudge = response.data;
          if (this.challengesJudge.length < 1) {
            this.judgeEmpty = true;
          }
        }).catch(
          error => {
          }
        );
  }

}
