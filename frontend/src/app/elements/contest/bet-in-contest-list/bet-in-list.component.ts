import { CommonModule, JsonPipe, formatDate } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewEncapsulation, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'bet-in-list',
  templateUrl: './bet-in-list.component.html',
  standalone: true,
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./bet-in-list.component.css'],
  imports:[CommonModule, RouterModule]
})
export class BetInListComponent {

  constructor(private router: Router, private axiosService: AxiosService){}

  @Input() key!: number;
  @Input() value!: any;
  winnerSet!: boolean;
  winner!: any;

  ngOnInit(){
    this.winnerSet = false;
    if(this.value.winner != null){
      this.getWinnerProfile(this.value.winner);
    } 
  }

  getWinnerProfile(winner: any){
    this.axiosService.request(
      "GET",
      "/getProfileById?userId=" + winner, {})
      .then(
        response => {
          this.winner = response.data;
          this.winnerSet = true;
        }).catch(
          error => {
          }
        );
  }
  
}
