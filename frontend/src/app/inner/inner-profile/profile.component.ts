import { Component, EventEmitter, Output } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';

@Component({
  selector: 'inner-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class InnerProfileComponent {

  @Output() onLeaveProfile = new EventEmitter();

  constructor(private axiosService: AxiosService, private router: Router) { }

  ngOnInit(): void {
    
  }

  

}
