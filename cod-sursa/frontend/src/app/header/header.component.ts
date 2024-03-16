import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { KeycloakService } from "keycloak-angular";
import { UserService } from "../shared/service/user.service";
import { BehaviorSubject } from "rxjs";

@Component({
  selector: 'ge-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public userName= new BehaviorSubject('');

  constructor(private router: Router, private keycloakService: KeycloakService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getUserName().subscribe((value) => {
       this.userName.next(value.userName);
    });
  }

  routeContains(route: string): boolean {
    return this.router.url.includes(route);
  }

  logout() {
    this.keycloakService.logout();
  }
}
