import { Component, Input } from '@angular/core';

@Component({
  selector: 'message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class ChatMessage {
  @Input() alignment!:string;
  @Input() avatar!:string;
  @Input() name!:string;
  @Input() text!:string;
  @Input() imageInMessage!:boolean;
  @Input() imagePath!:string;
  @Input() time!:string;
  
}
