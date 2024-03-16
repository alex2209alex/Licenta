import { Injectable } from "@angular/core";
import { API_URL } from "../../app.constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url: string = API_URL + "/utilizatori"

  constructor(private http: HttpClient) { }

  getUserName(): Observable<{userName: string}> {
    return this.http.get<{userName: string}>(this.url);
  }
}
