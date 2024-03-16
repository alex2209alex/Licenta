import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  protected authenticated: boolean = false;
  protected roles: string[] = [];

  constructor(
    protected router: Router,
    protected keycloakService: KeycloakService
  ) {}

  async canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean | UrlTree> {
    try {
      this.authenticated = await this.keycloakService.isLoggedIn();
      this.roles =  this.keycloakService.getUserRoles(true);
      if (!this.authenticated) {
        await this.keycloakService.login({
          redirectUri: window.location.origin + state.url
        });
      }
      const allowedRoles = route.data['hasAnyRole'];
      if (!Array.isArray(allowedRoles) || allowedRoles.length === 0) {
        return true;
      }
      for (let index in allowedRoles) {
        if (this.roles.includes(allowedRoles[index])) {
          return true;
        }
      }
      return false;
    } catch (error) {
      throw new Error(
        'A aparut o eroare:' + error
      );
    }
  }
}
