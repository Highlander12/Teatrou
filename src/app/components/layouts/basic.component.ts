import { Component } from '@angular/core';
import { detectBody } from '../../app.helpers';

declare var jQuery:any;

@Component({
  selector: 'basic',
  templateUrl: 'basic.component.html',
  host: {
    '(window:resize)': 'onResize()'
  }
})
export class BasicComponent {

  public ngOnInit():any {
    detectBody();
  }

  public onResize(){
    detectBody();
  }

}
