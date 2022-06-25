import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentEditionWindowComponent } from './document-edition-window.component';

describe('DocumentEditionWindowComponent', () => {
  let component: DocumentEditionWindowComponent;
  let fixture: ComponentFixture<DocumentEditionWindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentEditionWindowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DocumentEditionWindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
