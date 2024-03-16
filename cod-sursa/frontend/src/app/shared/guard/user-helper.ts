import { Injectable } from "@angular/core";
import { KeycloakService } from "keycloak-angular";
import { Role } from "./role";

@Injectable({
  providedIn: 'root'
})
export class UserHelper {
  constructor(private keycloakService: KeycloakService) {
  }

  isDispecerANR(): boolean {
    const roles =  this.keycloakService.getUserRoles(true);
    return roles.includes(Role.ROLE_DISPECER_ANR);
  }

  isDispecerAPMC(): boolean {
    const roles =  this.keycloakService.getUserRoles(true);
    return roles.includes(Role.ROLE_DISPECER_APMC);
  }

  isDispecerPilotaj(): boolean {
    const roles =  this.keycloakService.getUserRoles(true);
    return roles.includes(Role.ROLE_DISPECER_PILOTAJ);
  }

  isAgentNava(): boolean {
    const roles =  this.keycloakService.getUserRoles(true);
    return roles.includes(Role.ROLE_AGENT_NAVA);
  }

  isOperator(): boolean {
    const roles =  this.keycloakService.getUserRoles(true);
    return roles.includes(Role.ROLE_OPERATOR);
  }
}
