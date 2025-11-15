import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternshipOfferDetailComponent } from './internship-offer-detail.component';

describe('InternshipOfferDetailComponent', () => {
  let component: InternshipOfferDetailComponent;
  let fixture: ComponentFixture<InternshipOfferDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InternshipOfferDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(InternshipOfferDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
