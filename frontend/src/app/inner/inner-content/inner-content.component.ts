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
  waiting!: boolean;

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

}
