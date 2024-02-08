import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'challenge-observer',
  templateUrl: './challenge-observer.component.html',
  styleUrls: ['./challenge-observer.component.css']
})
export class InnerContentChallengeObserver {
  @Input() item!: any;
  @Output() onObserverExit = new EventEmitter(); 

  observerExit(){
    this.onObserverExit.emit();
  }
  
}
