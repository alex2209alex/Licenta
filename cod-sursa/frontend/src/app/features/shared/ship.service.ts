import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from 'src/app/app.constants';
import { Generic } from './generic.model';

@Injectable({
  providedIn: 'root'
})
export class ShipService {
  private url: string = API_URL + "/nave"

  constructor(private http: HttpClient) { }

  getAll(): Observable<Generic[]> {
    return this.http.get<Generic[]>(this.url);
  }
}
