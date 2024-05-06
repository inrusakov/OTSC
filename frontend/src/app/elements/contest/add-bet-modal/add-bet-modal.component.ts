import { CommonModule, JsonPipe, formatDate } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewEncapsulation, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AxiosService } from 'src/app/axios.service';
import Utils from 'src/app/Utils';

@Component({
  selector: 'add-bet-modal',
  standalone: true,
  templateUrl: './add-bet-modal.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [ReactiveFormsModule, JsonPipe, CommonModule]
})
export class AddBetModal {
  @Output() onSubmitEvent = new EventEmitter();

  @Input() contestId!: String;
  @Input() creator!: boolean;
  @Input() unlocked!: boolean;
  @Input() finished!: boolean;

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService, private router: Router) { }

  private modalService = inject(NgbModal);
  waiting: boolean = false;

  challengeForm = this.formBuilder.group({
    title: ['', Validators.required],
    description: ['']
  });

  onSubmit() {
    this.waiting = true;
    this.axiosService.request(
      "POST",
      "/addBetToContest", {
      title: this.challengeForm.value.title,
      description: this.challengeForm.value.description,
      date: Utils.getDate(new Date()),
      contest: this.contestId
    })
      .then(
        response => {
          this.waiting = false;
          this.onSubmitEvent.emit();
        }).catch(
          error => {

          }
        );
  }

  padTo2Digits(num: number) {
    return num.toString().padStart(2, '0');
  }

  getDate(date: Date){
    return(
      [
        date.getFullYear(),
        this.padTo2Digits(date.getMonth() + 1),
        this.padTo2Digits(date.getDate()),

      ].join('-') + ' ' +
      [
        date.getHours(),
        '0'+date.getMinutes()
      ].join(':')
    )
  }

  openLg(content: TemplateRef<any>) {
    this.modalService.open(content, { size: 'lg' });
  }
}
