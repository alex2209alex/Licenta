import { Injectable } from "@angular/core";
import { API_URL } from "../../../app.constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Generic } from "../../shared/generic.model";
import { MaritimeCall } from "./maritime-call.model";

@Injectable({
  providedIn: 'root'
})
export class MaritimeCallService {
  private url: string = API_URL + "/escale"

  constructor(private http: HttpClient) { }

  findAllThatCanHaveNewPilotageBulletin(): Observable<Generic[]> {
    return this.http.get<MaritimeCall[]>(this.url);
  }
}
