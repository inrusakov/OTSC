import { CommonModule, JsonPipe, formatDate } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewEncapsulation, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AxiosService } from 'src/app/axios.service';
import Utils from 'src/app/Utils';

@Component({
  selector: 'create-contest-modal',
  standalone: true,
  templateUrl: './contest-modal.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [ReactiveFormsModule, JsonPipe, CommonModule]
})
export class InnerContestModal {
  @Output() onSubmitEvent = new EventEmitter();

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService, private router: Router) { }

  private modalService = inject(NgbModal);
  waiting: boolean = false;

  challengeForm = this.formBuilder.group({
    title: ['', Validators.required],
    description: [''],
    size: [3, Validators.required],
  });

  onSubmit() {
    this.waiting = true;
    this.axiosService.request(
      "POST",
      "/createContest", {
      title: this.challengeForm.value.title,
      description: this.challengeForm.value.description,
      date: Utils.getDate(new Date()),
      size: this.challengeForm.value.size
    })
      .then(
        response => {
          this.waiting = false;
          this.router.navigate(['/app/contest']);
          this.onSubmitEvent.emit();
        }).catch(
          error => {

          }
        );
  }

  openLg(content: TemplateRef<any>) {
    this.modalService.open(content, { size: 'lg' });
  }
}
