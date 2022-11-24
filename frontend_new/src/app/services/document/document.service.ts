import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from 'rxjs';
import {PDFDocument} from "../../models/PDFDocument";
import {MessageResponse} from "../../models/MessageResponse";

import { API_BASE_URL, ACCESS_TOKEN } from '../../constants/app-constants.component';
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient,
              private cookieService: CookieService) { }

  setHeaders() {
    const headers = new HttpHeaders();
    return headers.append('Authorization', 'Bearer ' + this.cookieService.get(ACCESS_TOKEN));
  }

  getAllDocuments(pageNumber: number, itemsPerPage: number, titleFilter: string): Observable<Array<PDFDocument>> {
    return this.http.get<Array<PDFDocument>>(API_BASE_URL + '/documents/' + pageNumber + '/' + itemsPerPage + '?title=' + titleFilter,
      {headers: this.setHeaders() });
  }

  deleteDocument(pdfDocument: PDFDocument): Observable<HttpResponse<MessageResponse>> {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/deleteDocument', pdfDocument, {headers: this.setHeaders() });
  }

  createNewDocument(title: string) {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/add/' + title, {},  {headers: this.setHeaders() });
  }

  renameDocument(id: string, newTitle: string) {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/rename/' + newTitle, id, {headers: this.setHeaders() });
  }

}
