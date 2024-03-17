import { Component } from '@angular/core';
import { UserHelper } from "../../../shared/guard/user-helper";

@Component({
  selector: 'ge-bp-home',
  templateUrl: './bp-home.component.html',
  styleUrls: ['./bp-home.component.css']
})
export class BpHomeComponent {
  constructor(private userHelper: UserHelper) {
  }

  showAddBtn(): boolean {
    return this.userHelper.isAgentNava();
  }
}
