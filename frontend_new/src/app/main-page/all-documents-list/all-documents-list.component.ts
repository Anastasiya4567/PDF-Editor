import { Component, OnInit } from '@angular/core';
import {PDFDocument} from "../../models/PDFDocument";
import {Page} from "ngx-pagination/dist/pagination-controls.directive";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-all-documents-list',
  templateUrl: './all-documents-list.component.html',
  styleUrls: ['./all-documents-list.component.scss']
})
export class AllDocumentsComponent implements OnInit {

  documents: PDFDocument[] = [];
  host = 'http://localhost:8080';
  currentPage: Page = {label: 'label', value: 1};
  page: number = 0;
  itemsPerPage: number = 5;
  totalItems: number = 0;

  constructor(
    private http: HttpClient,
    private router: Router,
    private activatedRoute: ActivatedRoute) {
  }

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

  editDocument(document: PDFDocument) {
    // send id also
    this.router.navigate(['document/' + document.title], {relativeTo: this.activatedRoute})
  }
}
