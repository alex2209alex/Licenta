import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BpEditComponent } from './bp-edit.component';

describe('BpEditComponent', () => {
  let component: BpEditComponent;
  let fixture: ComponentFixture<BpEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BpEditComponent]
    });
    fixture = TestBed.createComponent(BpEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
