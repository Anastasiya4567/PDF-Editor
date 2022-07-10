import { Component, OnInit } from '@angular/core';
import {PDFDocument} from "../../models/PDFDocument";
import {Page} from "ngx-pagination/dist/pagination-controls.directive";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormControl} from "@angular/forms";
import {DocumentService} from "../../services/document/document.service";

@Component({
  selector: 'app-all-documents-list',
  templateUrl: './all-documents-list.component.html',
  styleUrls: ['./all-documents-list.component.scss']
})
export class AllDocumentsComponent implements OnInit {

  documents: PDFDocument[] = [];
  host = 'http://localhost:8080';
  currentPage: Page;
  page: number = 0;
  itemsPerPage: number = 5;
  totalItems: number = 0;
  id: string;
  titleFilter = new FormControl('');
  deleted: boolean = false;
  alertMessage: string;

  constructor(
    private http: HttpClient,
    private router: Router,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private documentService: DocumentService) {
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
    // const nextHeader = headersObject.append('Content-Type', 'application/json');
    // const actualHeader = nextHeader.append('Authorization', 'Bearer ' + token);
    this.documentService.getAllDocuments(pageNumber, this.itemsPerPage, this.titleFilter.value).subscribe(
      (response: any) => {
        this.currentPage = response as Page;
        this.documents = response['content'];
        console.log(this.documents);
        this.totalItems = response['totalElements'];
      }, error => {
        console.log(error)
      }
    )
  }

  editDocument(document: PDFDocument) {
    this.router.navigate(['document/' + document.title], {
      relativeTo: this.activatedRoute
    })
  }

  deleteDocument(modal: any) {
    this.documentService.deleteDocument(this.id).subscribe(
      (response: any) => {
        this.alertMessage = response.message;
        this.deleted = true;
        this.getAllDocuments(0);
      }, error => {
        console.log(error)
    })
    this.closeModal(modal);
  }

  openModal(content: any, id: string) {
    this.id = id;
    this.modalService.open(content);
  }

  closeModal(modal: any) {
    modal.close();
  }

  resetFilter() {
    this.titleFilter.reset('');
    this.getAllDocuments(0);
  }

  clearAlert() {
    this.deleted = false;
  }
}
