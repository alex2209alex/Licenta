import { Component, OnInit } from '@angular/core';
import { AvService } from '../shared/av.service';

@Component({
  selector: 'ge-av-home',
  templateUrl: './av-home.component.html',
  styleUrls: ['./av-home.component.css']
})
export class AvHomeComponent implements OnInit {
  constructor(private apiService: AvService) {
  }

  ngOnInit(): void {
    this.apiService.search({
      documentStatus: 0
    }).subscribe(
      (resData) => {
        console.log(resData);
      }
    );
  }
}
