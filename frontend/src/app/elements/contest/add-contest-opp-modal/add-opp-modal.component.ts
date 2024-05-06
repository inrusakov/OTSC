import { CommonModule, JsonPipe, formatDate } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewEncapsulation, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'add-contest-opp-modal',
  standalone: true,
  templateUrl: './add-opp-modal.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [ReactiveFormsModule, JsonPipe, CommonModule]
})
export class AddContestOpponentModal {
  @Output() onSubmitEvent = new EventEmitter();
  @Output() onErrorEvent = new EventEmitter();
  @Input() contestId!: string;

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService, private router: Router) { }

  private modalService = inject(NgbModal);

  challengeForm = this.formBuilder.group({
    login: ['', Validators.required]
  });

  onSubmit() {
    this.axiosService.request(
      "POST",
      "/setContestOpponent?contestId=" + this.contestId + "&opponent=" + this.challengeForm.value.login, {
    })
      .then(
        response => {
          this.onSubmitEvent.emit()
        }).catch(
          error => {
            this.onErrorEvent.emit();
          }
        );
  }

  openLg(content: TemplateRef<any>) {
    this.modalService.open(content, { size: 'lg' });
  }
}
