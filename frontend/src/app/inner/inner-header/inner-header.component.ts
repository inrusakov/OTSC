import { Component, EventEmitter, Input, Output } from '@angular/core';


@Component({
  selector: 'inner-header',
  templateUrl: './inner-header.component.html',
  styleUrls: ['./inner-header.component.css']
})
export class InnerHeaderComponent {
	@Input() pageTitle!: string;
}
