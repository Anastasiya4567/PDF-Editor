import { Component, OnInit } from '@angular/core';
import {Page} from "ngx-pagination/dist/pagination-controls.directive";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {PDFDocument} from "../../models/PDFDocument";

@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.scss']
})
export class StartPageComponent implements OnInit {

  documents: PDFDocument[] = [];
  host = 'http://localhost:8080';
  currentPage: Page = {label: 'label', value: 1};
  page: number = 0;
  itemsPerPage: number = 5;
  totalItems: number = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getAllDocuments(0);
  }

  pageChange(pageIndex: number): void {
    if (pageIndex >= 0) {
      this.page = pageIndex;
      this.getAllDocuments(pageIndex-1);
    }
  }

  getAllDocuments(pageNumber: number): void {

    // const token = this.cookieService.get('token');
    //
    const headers = new HttpHeaders();
    // const nextHeader = headersObject.append('Content-Type', 'application/json');
    // const actualHeader = nextHeader.append('Authorization', 'Bearer ' + token);

    this.http.get(this.host + '/documents/' + pageNumber + '/' + this.itemsPerPage, {
      headers: headers }).subscribe(
      (response: any) => {
        this.currentPage = response as Page;
        this.documents = response['content'];
        console.log(this.documents);
        this.totalItems = response['totalElements'];
      });
  }

}
