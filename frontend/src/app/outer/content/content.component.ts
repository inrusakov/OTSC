import { Component } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';

@Component({
	selector: 'app-content',
	templateUrl: './content.component.html',
	styleUrls: ['./content.component.css'],
})
export class ContentComponent {
	componentToShow: string = "welcome";

	onCredentialsError: Subject<void> = new Subject<void>();

	constructor(private axiosService: AxiosService, private router: Router) { }

	showComponent(componentToShow: string): void {
		this.componentToShow = componentToShow;
	}

	proceedToApp() {
		this.axiosService.request(
			"GET",
			"/checkAuth", {})
			.then(
				response => {
					this.router.navigate(['/app']);
				}).catch(
					error => {
						this.componentToShow = "login";
					}
				);
	}

	onLogin(input: any): void {
		this.axiosService.request(
			"POST",
			"/login",
			{
				login: input.login,
				password: input.password
			}).then(
				response => {
					this.axiosService.setAuthToken(response.data.token);
					this.componentToShow = "messages";
					this.router.navigate(['/app']);
				}).catch(
					error => {
						this.axiosService.setAuthToken(null);
						this.onCredentialsError.next();
					}
				);

	}

	onRegister(input: any): void {
		this.axiosService.request(
			"POST",
			"/register",
			{
				firstName: input.firstName,
				lastName: input.lastName,
				login: input.login,
				password: input.password
			}).then(
				response => {
					this.axiosService.setAuthToken(response.data.token);
					this.componentToShow = "messages";
					this.router.navigate(['/app']);
				}).catch(
					error => {
						this.axiosService.setAuthToken(null);
						this.onCredentialsError.next();
					}
				);

	}

}
