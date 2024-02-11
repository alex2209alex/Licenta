import { Injectable } from "@angular/core";
import { API_URL } from "../../app.constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Generic } from "./generic.model";

@Injectable({
  providedIn: 'root'
})
export class CargoService {
  private url: string = API_URL + "/marfuri"

  constructor(private http: HttpClient) { }

  getAll(): Observable<Generic[]> {
    return this.http.get<Generic[]>(this.url);
  }
}
