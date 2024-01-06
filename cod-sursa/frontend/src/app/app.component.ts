import { Component, OnInit } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'ge-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private translate: TranslateService, private http: HttpClient) {
    translate.use('ro');
  }

  ngOnInit(): void {
    this.http.get("http://localhost:8080/message").subscribe().unsubscribe();
  }
}
