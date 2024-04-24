import { CommonModule, JsonPipe, formatDate } from '@angular/common';
import { Component, EventEmitter, Input, Output, TemplateRef, ViewEncapsulation, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AxiosService } from 'src/app/axios.service';

@Component({
  selector: 'add-message-modal',
  standalone: true,
  templateUrl: './message-modal.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [ReactiveFormsModule, JsonPipe, CommonModule]
})
export class MessageModal {
  selectedFiles?: FileList;
  currentFile?: File;
  progress = 0;
  message = '';
  preview = '';
  uploadedFile!: string;

  @Input() betId!: string;
  @Input() alignment!: string;
  @Output() onSubmitEvent = new EventEmitter();

  constructor(private formBuilder: FormBuilder, private axiosService: AxiosService, private router: Router) { }

  private modalService = inject(NgbModal);
  waiting: boolean = false;

  messageForm = this.formBuilder.group({
    text: ['']
  });

  padTo2Digits(num: number) {
    return num.toString().padStart(2, '0');
  }

  getDate(date: Date) {
    return (
      [
        date.getFullYear(),
        this.padTo2Digits(date.getMonth() + 1),
        this.padTo2Digits(date.getDate()),
      ].join('-')
    )
  }

  openLg(content: TemplateRef<any>) {
    this.modalService.open(content, { size: 'lg' });
  }

  closeModal() {
    this.modalService.dismissAll();
  }

  selectFile(event: any): void {
    this.message = '';
    this.preview = '';
    this.selectedFiles = event.target.files;

    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      if (file) {
        this.preview = '';
        this.currentFile = file;
        const reader = new FileReader();
        reader.onload = (e: any) => {
          console.log(e.target.result);
          this.preview = e.target.result;
        };
        reader.readAsDataURL(this.currentFile);
      }
    }
  }

  onSubmit(): void {
    if (this.selectedFiles) {
      const file: File | null = this.selectedFiles.item(0);
      if (file) {
        this.currentFile = file;
        this.axiosService.sendFile(
          "POST",
          "/photos/add?title=" + "image", { image: this.currentFile })
          .then(
            response => {
              this.uploadedFile = response.data;
              this.axiosService.request(
                "POST",
                "/addMessage",
                {
                  betId: this.betId,
                  alignment: this.alignment,
                  avatar: 'link',
                  text: this.messageForm.value.text,
                  imageInMessage: true,
                  imagePath: this.uploadedFile,
                  time: this.getDate(new Date)
                })
                .then(
                  response => {
                    this.waiting = false;
                    this.onSubmitEvent.emit();
                  }).catch(
                    error => {
                    }
                  );
            }).catch(
              error => {
                console.log(error);
                if (error.error && error.error.message) {
                  this.message = error.error.message;
                } else {
                  this.message = 'Could not upload the image!';
                }
                this.currentFile = undefined;
              }
            );
      }
      this.selectedFiles = undefined;
    } else {
      this.axiosService.request(
        "POST",
        "/addMessage",
        {
          betId: this.betId,
          alignment: this.alignment,
          avatar: 'link',
          text: this.messageForm.value.text,
          imageInMessage: false,
          imagePath: 'null',
          time: this.getDate(new Date)
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
  }
}
