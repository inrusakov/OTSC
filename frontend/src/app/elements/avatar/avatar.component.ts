import { Component, Input } from '@angular/core';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'avatar',
  templateUrl: './avatar.component.html',
  styleUrls: ['./avatar.component.css']
})
export class AvatarComponent {
  @Input() width!: number;
  @Input() height!: number;

  profile!: any;
  waiting: boolean = false;

  constructor(private axiosService: AxiosService) { }

  ngOnInit() {
    this.axiosService.request(
      "GET",
      "/profile", {})
      .then(
        response => {
          this.profile = response.data;
          if (response.data.avatar === null) {
            this.waiting = false;
          } else {
            this.waiting = true;
          }
        }).catch(
          error => {
          }
        );
  }
}
