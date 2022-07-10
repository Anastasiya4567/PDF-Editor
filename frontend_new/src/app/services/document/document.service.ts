import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Observable} from 'rxjs';
import {PDFDocument} from "../../models/PDFDocument";
import {MessageResponse} from "../../models/MessageResponse";

const baseUrl = 'http://localhost:8080';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(private http: HttpClient) { }

  getAllDocuments(pageNumber: number, itemsPerPage: number, titleFilter: string): Observable<Array<PDFDocument>> {
    const headers = new HttpHeaders();
    return this.http.get<Array<PDFDocument>>(baseUrl + '/documents/' + pageNumber + '/' + itemsPerPage + '?title=' + titleFilter,
      {headers: headers});
  }

  deleteDocument(id: string): Observable<HttpResponse<MessageResponse>> {
    const headers = new HttpHeaders();
    return this.http.post<HttpResponse<MessageResponse>>(baseUrl + '/deleteDocument', id, {headers: headers });
  }


  createNewDocument(title: string) {
    const headers = new HttpHeaders();
    return this.http.post<HttpResponse<MessageResponse>>(baseUrl + '/add/' + title, {headers: headers });
  }


}
