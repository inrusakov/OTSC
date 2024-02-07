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

  randomColor(): string {
    let result = '';
    for (let i = 0; i < 6; ++i) {
      const value = Math.floor(16 * Math.random());
      result += value.toString(16);
    }
    return '#' + result;
  };

  ngOnInit(): void {
    this.axiosService.request(
			"GET",
			"/currentUserBets", {})
			.then(
				response => {
          this.challenges = response.data;
          console.log(response);
				}).catch(
					error => {
            
					}
				);
  }
}
