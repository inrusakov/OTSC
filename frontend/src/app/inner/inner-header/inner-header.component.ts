import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AxiosService } from 'src/app/axios.service';


@Component({
  selector: 'inner-header',
  templateUrl: './inner-header.component.html',
  styleUrls: ['./inner-header.component.css']
})
export class InnerHeaderComponent {
	@Input() pageTitle!: string;

  constructor(private axiosService: AxiosService, private router: Router){}

  logOut(){
    this.axiosService.setAuthToken(null);
	  this.router.navigate(['/']);
  }
  
}
