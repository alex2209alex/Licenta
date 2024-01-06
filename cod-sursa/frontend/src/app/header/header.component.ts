import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'ge-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private router: Router) {
  }

  routeContains(route: string): boolean {
    return this.router.url.includes(route);
  }
}
