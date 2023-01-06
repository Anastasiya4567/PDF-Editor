import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ACCESS_TOKEN, API_BASE_URL} from "../../constants/app-constants.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {GeneratedDocument} from "../../models/GeneratedDocument";
import {MessageResponse} from "../../models/MessageResponse";
import {Image} from "../../models/Image";

@Injectable({
  providedIn: 'root'
})
export class GeneratedDocumentService {

  constructor(private http: HttpClient,
              private cookieService: CookieService) {
  }

  getGeneratedDocument(generatedDocumentId: string): Observable<GeneratedDocument> {
    return this.http.get<GeneratedDocument>(API_BASE_URL + '/getGeneratedDocument?id=' + generatedDocumentId,
      {headers: this.setHeaders()});
  }

  generateDocument(body: any): Observable<MessageResponse> {
    const headers = this.setHeaders().append('Content-Type', 'application/json');

    return this.http.post<MessageResponse>(API_BASE_URL + '/generateFromSourceCode', JSON.stringify(body), {
      headers: headers
    })
  }

  private setHeaders() {
    const headers = new HttpHeaders();
    return headers.append('Authorization', 'Bearer ' + this.cookieService.get(ACCESS_TOKEN));
  }

  getImages(documentId: string) {
    return this.http.get<Array<Image>>(API_BASE_URL + '/getImages?id=' + documentId,
      {headers: this.setHeaders()});
  }

  saveImage(image: Image, documentId: string) {
    const headers = this.setHeaders().append('Content-Type', 'application/json');

    return this.http.post<MessageResponse>(API_BASE_URL + '/addImage?id=' + documentId, JSON.stringify(image),
      {headers: headers});
  }
}
