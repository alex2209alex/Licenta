import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvEditComponent } from './av-edit.component';

describe('AvEditComponent', () => {
  let component: AvEditComponent;
  let fixture: ComponentFixture<AvEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvEditComponent]
    });
    fixture = TestBed.createComponent(AvEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
