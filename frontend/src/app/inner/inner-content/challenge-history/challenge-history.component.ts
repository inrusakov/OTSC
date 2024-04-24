import { Component, EventEmitter, Output } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'challenge-history',
  templateUrl: './challenge-history.component.html',
  styleUrls: ['./challenge-history.component.css'],
})
export class HistoryComponent {

  waiting!: boolean;

  historyEmpty!: boolean;

  items!: any;

  start!: number;
  end!: number;
  page!: number;
  pageSize!: number;
  maxSize!: number;

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit(){
    this.waiting = true;
    this.page = 1;
    this.pageSize = 8;
    this.maxSize = 8;
    this.updateStartAndEnd();

    this.axiosService.request(
      "GET",
      "/history", {})
      .then(
        response => {
          this.waiting = false;
          this.items = response.data;
          if (this.items.length < 1) {
            this.historyEmpty = true;
          }
          
        }).catch(
          error => {
          }
        );
  }

  updateStartAndEnd() {
    this.start = (this.page - 1) * this.pageSize;
    this.end = this.page * this.pageSize;
  }

  identify(index: any, item: { id: any; }) {
    return item.id;
  }

}
