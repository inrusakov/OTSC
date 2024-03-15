import { Component, Input } from '@angular/core';

@Component({
  selector: 'profile-p',
  templateUrl: './profile-p.component.html',
  styleUrls: ['./profile-p.component.css']
})
export class ProfileParagraph {
  @Input() profile!: any;

  
}
