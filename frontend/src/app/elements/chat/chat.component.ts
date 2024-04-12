import { Component, Input } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class Chat {
  @Input() betId!: string;
  @Input() thisUserRole!: string;
  @Input() isUserAllowed!: boolean;

  alignment!: string;

  waiting!: boolean;
  chat!: any;

  constructor(private axiosService: AxiosService) {
  }

  ngOnInit() {
    this.waiting = true;
    this.getChatByBetId(this.betId);
    switch (this.thisUserRole) {
      case 'none': {
        break;
      }
      case 'creator': {
        this.alignment = 'right';
        break;
      }
      case 'opponent': {
        this.alignment = 'left';
        break;
      }
      case 'judge': {
        this.alignment = 'right';
        break;
      }
      default: {
        break;
      }
    }
  }

  getChatByBetId(betId: string) {
    this.axiosService.request(
      "GET",
      "/getChat?betId=" + betId, {})
      .then(
        response => {
          this.chat = response.data;
          this.waiting = false;
        }).catch(
          error => {
          }
        );
  }
}
