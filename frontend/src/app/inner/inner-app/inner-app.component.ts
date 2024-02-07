import { Component } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';

@Component({
  selector: 'inner-app',
  templateUrl: './inner-app.component.html',
  styleUrls: ['./inner-app.component.css']
})
export class InnerAppComponent {
  componentToShow: string = "welcome";

  constructor(private axiosService: AxiosService, private router: Router) { }

  showComponent(componentToShow: string): void {
    this.componentToShow = componentToShow;
  }

  ngOnInit(): void {
    this.axiosService.request(
			"GET",
			"/checkAuth", {})
			.then(
				response => {
				}).catch(
					error => {
            this.axiosService.setAuthToken(null);
						this.router.navigate(['/']);
					}
				);
  }

}
