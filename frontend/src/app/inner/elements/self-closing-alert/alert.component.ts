import { Component, Input, ViewChild } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { debounceTime, tap } from 'rxjs/operators';
import { NgbAlert, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { CommonModule } from '@angular/common';

@Component({
	selector: 'ngbd-alert-selfclosing',
	standalone: true,
	imports: [NgbAlertModule, NgbAlert, CommonModule],
	templateUrl: './alert.component.html',
})
export class NgbdAlertSelfclosing {

  private eventsSubscription!: Subscription;
  @Input() events!: Observable<void>;
  @Input() message!: string;
  @Input() type!: string;

  ngOnInit(){
    this.eventsSubscription = this.events.subscribe(() => this.changeSuccessMessage());
  }
  
  ngOnDestroy() {
    this.eventsSubscription.unsubscribe();
  }

	private _message$ = new Subject<string>();

	staticAlertClosed = false;
	successMessage = '';

	@ViewChild('staticAlert', { static: false })
  staticAlert!: NgbAlert;
	@ViewChild('selfClosingAlert', { static: false })
  selfClosingAlert!: NgbAlert;

	constructor() {
		setTimeout(() => this.staticAlert.close(), 20000);

		this._message$
			.pipe(
				takeUntilDestroyed(),
				tap((message) => (this.successMessage = message)),
				debounceTime(5000),
			)
			.subscribe(() => this.selfClosingAlert?.close());
	}

	public changeSuccessMessage() {
		this._message$.next(this.message);
	}
}