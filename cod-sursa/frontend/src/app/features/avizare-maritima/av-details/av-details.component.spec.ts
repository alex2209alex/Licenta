import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvDetailsComponent } from './av-details.component';

describe('AvDetailsComponent', () => {
  let component: AvDetailsComponent;
  let fixture: ComponentFixture<AvDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvDetailsComponent]
    });
    fixture = TestBed.createComponent(AvDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
