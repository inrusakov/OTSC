import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'button-logout',
  templateUrl: './button-logout.component.html',
  styleUrls: ['./button-logout.component.css']
})
export class ButtonLogout {
	@Output() logoutEvent = new EventEmitter();
}
