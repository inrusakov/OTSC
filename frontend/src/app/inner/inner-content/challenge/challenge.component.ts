import { Component, Input } from '@angular/core';

@Component({
  selector: 'inner-challenge',
  templateUrl: './challenge.component.html',
  styleUrls: ['./challenge.component.css']
})
export class InnerContentChallenge {
  @Input() username!: string; 
  @Input() title!: string;
  @Input() color!: string;
}
