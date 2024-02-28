import { Component, EventEmitter, Output } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'inner-profile',
  standalone: true,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  imports: [ReactiveFormsModule, CommonModule ]
})
export class InnerProfileModal {

  @Output() onLeaveProfile = new EventEmitter();

  active: string = "profile";
  waiting: boolean = false; 

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService, private router: Router) { }

  ngOnInit(): void {
  }

  showComponent(active: string): void {
		this.active = active;
	}
  
  profileUpdateForm = this.formBuilder.group({
    name: [''],
    surname: [''],
    status:['']
  });

  onUpdateProfile(){
    this.waiting = true;
    this.axiosService.request(
      "POST",
      "/updateProfile", {
      name: this.profileUpdateForm.value.name,
      surname: this.profileUpdateForm.value.surname,
      status: this.profileUpdateForm.value.status
    })
      .then(
        response => {
          this.waiting = false;
        }).catch(
          error => {

          }
        );
  }

  securityUpdateForm = this.formBuilder.group({
    oldPass: [''],
    newPass: [''],
    newPass2:['']
  });

  onUpdateSecurity(){
    this.waiting = true;
    this.axiosService.request(
      "POST",
      "/updateSecurity", {
      oldPass: this.securityUpdateForm.value.oldPass,
      newPass: this.securityUpdateForm.value.newPass,
      newPass2: this.securityUpdateForm.value.newPass2
    })
      .then(
        response => {
          this.waiting = false;
        }).catch(
          error => {

          }
        );
  }

}
