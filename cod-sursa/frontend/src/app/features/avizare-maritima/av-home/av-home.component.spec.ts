import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvHomeComponent } from './av-home.component';

describe('AvHomeComponent', () => {
  let component: AvHomeComponent;
  let fixture: ComponentFixture<AvHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvHomeComponent]
    });
    fixture = TestBed.createComponent(AvHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
