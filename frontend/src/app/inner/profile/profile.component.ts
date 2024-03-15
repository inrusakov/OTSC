import { Component, EventEmitter, Output } from '@angular/core';
import { AxiosService } from '../../axios.service';
import { Router, RouterModule } from '@angular/router';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbdAlertSelfclosing } from '../../elements/self-closing-alert/alert.component';
import { Subject } from 'rxjs';

@Component({
  selector: 'inner-profile',
  standalone: true,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  imports: [ReactiveFormsModule, CommonModule, RouterModule, NgbdAlertSelfclosing]
})
export class InnerProfileModal {

  onPassSubmited: Subject<void> = new Subject<void>();
  onPassError: Subject<void> = new Subject<void>();

  active: string = "profile";
  waiting: boolean = false;

  login!: string;
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
          this.login = response.data.login;
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
    status: ['']
  });

  onUpdateProfile() {
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
    oldPass: ['', Validators.required],
    newPass: ['', Validators.required],
    newPass2: ['', Validators.required]
  });

  onUpdateSecurity() {
    this.waiting = true;
    this.axiosService.request(
      "POST",
      "/changePass", {
      login: this.login,
      oldPass: this.securityUpdateForm.value.oldPass,
      newPass: this.securityUpdateForm.value.newPass
    })
      .then(
        response => {
          this.waiting = false;
          this.onPassSubmited.next();
        }).catch(
          error => {
            this.waiting = false;
            this.onPassError.next();
          }
        );
  }

}
