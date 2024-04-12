import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'copy-button',
  templateUrl: './copy-button.component.html',
  standalone: true,
  imports: [NgbTooltipModule, CommonModule],
  styleUrls: ['./copy-button.component.css']
})
export class CopyButton {
  host :string = "http://localhost:4200";

  constructor(private router : Router) {}

  copyMessage() {
    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = this.host + this.router.url;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
  }
}
