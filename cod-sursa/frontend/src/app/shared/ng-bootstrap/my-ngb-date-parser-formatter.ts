import { Injectable } from '@angular/core';
import { NgbDateParserFormatter, NgbDateStruct } from "@ng-bootstrap/ng-bootstrap";

@Injectable({
  providedIn: 'root'
})
export class MyNgbDateParserFormatter extends NgbDateParserFormatter {
  readonly DELIMITER = '-';

  parse(value: string): NgbDateStruct | null {
    if (value) {
      const date = value.split(this.DELIMITER);
      return {
        day: parseInt(date[0], 10),
        month: parseInt(date[1], 10),
        year: parseInt(date[2], 10),
      };
    }
    return null;
  }

  format(date: NgbDateStruct | null): string {
    return date ? this.addZeroToDayOrMonth(date.day) + this.DELIMITER + this.addZeroToDayOrMonth(date.month) + this.DELIMITER + date.year : '';
  }

  private addZeroToDayOrMonth(value: number): string {
    return value < 10 ? '0' + value : value.toString();
  }
}
