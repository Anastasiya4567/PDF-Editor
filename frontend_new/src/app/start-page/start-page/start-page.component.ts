import { Component, OnInit } from '@angular/core';
import {Page} from "ngx-pagination/dist/pagination-controls.directive";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {PDFDocument} from "../../models/PDFDocument";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DocumentAddRequest} from "../../models/DocumentAddRequest";

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
  clicked: boolean = false;
  documentData: FormGroup;
  document: DocumentAddRequest = {title: ''};

  constructor(private http: HttpClient,
              private formBuilder: FormBuilder,
              private httpClient: HttpClient) {
    this.documentData = this.formBuilder.group({
      title: ['', Validators.required]
    })
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

  openModal() {
    this.clicked = true;
    console.log('clicked')
  }

  closeModal() {
    this.clicked = false;
    console.log('closed')
  }

  onSubmit() {
    console.log(this.documentData.value.title)
    // check if empty
    this.document.title = this.documentData.value.title;

    this.sendNewDocumentRequest();
  }

  sendNewDocumentRequest() {
    const headers = new HttpHeaders();

    this.httpClient.post(this.host + '/add?title=' + this.document.title, {
      headers: headers }).subscribe(
      (response: any) => {
        if (response.status == 200) {
          console.log('added ' + this.document.title)
          this.getAllDocuments(0)
        } else {
          console.log('Error saving new document')
        }
      })
  }
}
