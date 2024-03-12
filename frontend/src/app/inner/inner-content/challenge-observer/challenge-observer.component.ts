import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'challenge-observer',
  templateUrl: './challenge-observer.component.html',
  styleUrls: ['./challenge-observer.component.css']
})
export class InnerContentChallengeObserver {

  private id: number | undefined;
  private subscription: Subscription | undefined;
  waiting!: boolean;
  item!: any;

  constructor(private route: ActivatedRoute, private axiosService: AxiosService, private router: Router) {
    this.subscription = route.params.subscribe(params => this.id = params["id"]);
  }

  ngOnInit() {
    this.waiting = true;
    this.axiosService.request(
      "GET",
      "/getBetById?betId="+this.id, { })
      .then(
        response => {
          this.waiting = false;
          this.item = response.data;
        }).catch(
          error => {
            if (error.response.status === 404) {
              this.router.navigate(['/app/404']);
            }
          }
        );
  }

}
