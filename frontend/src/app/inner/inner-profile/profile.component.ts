import { Component, EventEmitter, Output } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'inner-profile',
  standalone: true,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  imports: [ReactiveFormsModule, CommonModule, RouterModule]
})
export class InnerProfileModal {

  active: string = "profile";
  waiting: boolean = false; 

  firstName!: string;
  lastName!: string;
  status!: string;

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService, private router: Router) { }

  ngOnInit(): void {
    this.axiosService.request(
      "GET",
      "/profile", {})
      .then(
        response => {
          this.firstName = response.data.firstName;
          this.lastName = response.data.lastName;
          this.status = response.data.status;
        }).catch(
          error => {

          }
        );
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
      "PUT",
      "/profile", {
      firstName: this.profileUpdateForm.value.name,
      lastName: this.profileUpdateForm.value.surname,
      status: this.profileUpdateForm.value.status
    })
      .then(
        response => {
          this.waiting = false;
          this.firstName = response.data.firstName;
          this.lastName = response.data.lastName;
          this.status = response.data.status;
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
