import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AvSearch } from './av-search.model';
import { Observable } from 'rxjs';
import { AvListItem } from './av-list-item.model';
import { API_URL } from 'src/app/app.constants';
import { AvizareMaritima } from "./av.model";
import { Resolution } from "../../shared/resolution.model";

@Injectable({
  providedIn: 'root'
})
export class AvService {
  private url: string = API_URL + "/avizari-maritime"

  constructor(private http: HttpClient) { }

  search(search: AvSearch): Observable<AvListItem[]> {
    let queryParams = new HttpParams();
    if (search.documentStatus) {
      queryParams = queryParams.append("documentStatus", search.documentStatus);
    }

    return this.http.get<AvListItem[]>(this.url, {params:queryParams});
  }

  add(item: AvizareMaritima): Observable<void> {
    return this.http.post<void>(this.url, item);
  }

  update(item: AvizareMaritima, id: number): Observable<void> {
    return this.http.put<void>(this.url + '/' + id, item);
  }

  findById(id: number): Observable<AvizareMaritima> {
    return this.http.get<AvizareMaritima>(this.url + "/" + id);
  }

  resolve(resolution: Resolution, id: number): Observable<void> {
    return this.http.post<void>(this.url + "/resolve/" + id, resolution);
  }
}
