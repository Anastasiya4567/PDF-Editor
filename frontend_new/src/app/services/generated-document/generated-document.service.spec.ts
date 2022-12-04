import { TestBed } from '@angular/core/testing';

import { GeneratedDocumentService } from './generated-document.service';

describe('GeneratedDocumentService', () => {
  let service: GeneratedDocumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeneratedDocumentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
