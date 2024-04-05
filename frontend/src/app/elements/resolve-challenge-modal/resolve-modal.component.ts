import { CommonModule, JsonPipe, formatDate } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewEncapsulation, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'resolve-modal',
  standalone: true,
  templateUrl: './resolve-modal.component.html',
  styleUrls: ['./resolve-modal.component.css'],
  encapsulation: ViewEncapsulation.None,
  imports: [ReactiveFormsModule, JsonPipe, CommonModule]
})
export class ResolveChallengeModal {
  @Output() onSubmitEvent = new EventEmitter();
  @Output() onErrorEvent = new EventEmitter();

  @Input() betId!: number;
  @Input() challenge!: any;
  @Input() creatorProfile!: any;
  @Input() opponentProfile!: any;

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService) { }

  private modalService = inject(NgbModal);

  challengeForm = this.formBuilder.group({
    creatorCheck: [false],
    opponentCheck: [false]
  });

  onSubmit() {
    if (this.challengeForm.value.creatorCheck) {
      this.onCreatorWin();
    } else {
      this.onOpponentWin();
    }
  }

  onCreatorWin() {
    this.axiosService.request(
      "POST",
      "/resolve?betId=" + this.betId + "&winner=" + this.challenge.creator, {
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

  onOpponentWin() {
    this.axiosService.request(
      "POST",
      "/resolve?betId=" + this.betId + "&winner=" + this.challenge.opponent, {
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

  onCreatorCheckChange($event: any) {
    if (this.challengeForm.value.opponentCheck) {
      this.challengeForm.setValue({ creatorCheck: true, opponentCheck: false })
    }
  }

  onOpponentCheckChange($event: any) {
    if (this.challengeForm.value.creatorCheck) {
      this.challengeForm.setValue({ creatorCheck: false, opponentCheck: true })
    }
  }

  ifFormValid() {
    return this.challengeForm.value.creatorCheck || this.challengeForm.value.opponentCheck;
  }

  openLg(content: TemplateRef<any>) {
    this.modalService.open(content, { size: 'lg' });
  }
}
