import { Component, Input } from '@angular/core';

@Component({
  selector: 'inner-challenge',
  templateUrl: './challenge.component.html',
  styleUrls: ['./challenge.component.css']
})
export class InnerContentChallenge {
  @Input() id!: string;
  @Input() title!: string; 
  @Input() description!: string;
  color!: string;

  ngOnInit(): void{
    this.color = this.randomColor();
  }

  randomColor(): string {
    let result = '';
    for (let i = 0; i < 6; ++i) {
      const value = Math.floor(16 * Math.random());
      result += value.toString(16);
    }
    return '#' + result;
  };
  
}
