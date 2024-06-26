import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'inner-challenge',
  templateUrl: './challenge.component.html',
  standalone: true,
  styleUrls: ['./challenge.component.css'],
  imports: [CommonModule]
})
export class InnerContentChallenge {
  @Input() id!: string;
  @Input() title!: string;
  @Input() date!: any;
  @Input() winner!: any;
  winnerProfile!: any;
  color!: string;

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void {
    this.color = this.randomColor();
    if(this.winner){
      this.getWinnerProfile();
    }
  }

  randomColor(): string {
    let result = '';
    for (let i = 0; i < 6; ++i) {
      const value = Math.floor(16 * Math.random());
      result += value.toString(16);
    }
    return '#' + result;
  };

  getDate(date: any) {
    return date.join('-')
  }

  getWinnerProfile(){
    this.axiosService.request(
      "GET",
      "/getProfileById?userId=" + this.winner, {})
    .then(
      response => {
        this.winnerProfile = response.data;
      }).catch(
        error => {
        }
      );
  }
  
}
