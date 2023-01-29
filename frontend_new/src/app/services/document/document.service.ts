import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from 'rxjs';
import {PDFDocument} from "../../models/PDFDocument";
import {MessageResponse} from "../../models/MessageResponse";

import {ACCESS_TOKEN, API_BASE_URL} from '../../constants/app-constants.component';
import {NewDocumentCreateRequest} from "../../models/NewDocumentCreateRequest";

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient) {
  }

  setHeaders() {
    const headers = new HttpHeaders();
    return headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
  }

  getAllDocuments(pageNumber: number, itemsPerPage: number, titleFilter: string): Observable<Array<PDFDocument>> {
    return this.http.get<Array<PDFDocument>>(API_BASE_URL + '/user-documents/' + pageNumber + '/' + itemsPerPage + '?title=' + titleFilter,
      {headers: this.setHeaders()});
  }

  getDocumentById(id: string | null): Observable<PDFDocument> {
    return this.http.get<PDFDocument>(API_BASE_URL + '/getDocumentById?id=' + id, {
      headers: this.setHeaders()
    })
  }

  deleteDocument(pdfDocument: PDFDocument): Observable<HttpResponse<MessageResponse>> {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/deleteDocument', pdfDocument, {headers: this.setHeaders()});
  }

  createNewDocument(createdDocument: NewDocumentCreateRequest) {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/add', createdDocument, {headers: this.setHeaders()});
  }

  renameDocument(id: string, newTitle: string) {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/rename/' + newTitle, id, {headers: this.setHeaders()});
  }

}
