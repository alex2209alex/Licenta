import { Injectable } from "@angular/core";
import { API_URL } from "../../app.constants";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Generic } from "./generic.model";
import { CompanyType } from "./company-type";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private url: string = API_URL + "/firme"

  constructor(private http: HttpClient) { }

  getAllOfType(companyType: CompanyType): Observable<Generic[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("companyType", companyType);
    return this.http.get<Generic[]>(this.url, {params:queryParams});
  }
}
