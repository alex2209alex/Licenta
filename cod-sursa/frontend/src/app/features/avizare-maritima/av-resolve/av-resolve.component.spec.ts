import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvResolveComponent } from './av-resolve.component';

describe('AvResolveComponent', () => {
  let component: AvResolveComponent;
  let fixture: ComponentFixture<AvResolveComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvResolveComponent]
    });
    fixture = TestBed.createComponent(AvResolveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
