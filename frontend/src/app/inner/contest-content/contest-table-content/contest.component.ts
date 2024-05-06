import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'inner-contest',
  templateUrl: './contest.component.html',
  standalone: true,
  styleUrls: ['./contest.component.css'],
  imports:[CommonModule]
})
export class InnerContestContent {
  @Input() id!: string;
  @Input() title!: string;
  @Input() date!: any; 
  @Input() winner!: any;
  winnerProfile!: any;
  color!: string;

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(): void{
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
