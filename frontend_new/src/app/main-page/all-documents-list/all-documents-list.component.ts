import { Component, OnInit } from '@angular/core';
import {PDFDocument} from "../../models/PDFDocument";
import {Page} from "ngx-pagination/dist/pagination-controls.directive";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormControl} from "@angular/forms";
import {DocumentService} from "../../services/document/document.service";
import {dateFormat} from "../../shared/shared.config";

@Component({
  selector: 'app-all-documents-list',
  templateUrl: './all-documents-list.component.html',
  styleUrls: ['./all-documents-list.component.scss']
})
export class AllDocumentsComponent implements OnInit {

  documents: PDFDocument[] = [];
  currentPage: Page;
  page: number = 0;
  itemsPerPage: number = 5;
  totalItems: number = 0;
  document: PDFDocument;
  titleFilter = new FormControl('');
  deleted: boolean = false;
  alertMessage: string;
  newTitle = new FormControl('');
  isSubmitted = false;
  dateFormat = dateFormat.long;

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
    this.documentService.getAllDocuments(pageNumber, this.itemsPerPage, this.titleFilter.value).subscribe(
      (response: any) => {
        this.currentPage = response as Page;
        this.documents = response['content'];
        console.log(this.documents)
        this.page = response['number'] + 1;
        this.totalItems = response['totalElements'];
      }, error => {
        console.log(error)
      }
    )
  }

  editDocument(document: PDFDocument) {
    console.log('id: ' + document.id)
    this.router.navigate(['document/' + document.id], {
      relativeTo: this.activatedRoute
    })
  }

  deleteDocument(modal: any) {
    this.documentService.deleteDocument(this.document).subscribe(
      (response: any) => {
        this.alertMessage = response.message;
        this.deleted = true;
        this.getAllDocuments(0);
      }, error => {
        console.log(error)
    })
    this.closeModal(modal);
  }

  openModal(content: any, document: PDFDocument) {
    this.document = document;
    this.newTitle.setValue(document.title);
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

  onSubmit(modal: any) {
    this.isSubmitted = true;

    if (this.newTitle.invalid) {
      console.log('empty title');
      return;
    }

    this.renameDocument();
    modal.close();
  }

  private renameDocument() {
    this.documentService.renameDocument(this.document.id, this.newTitle.value).subscribe(
      (response: any) => {
        console.log(response)
        console.log(this.page)
          this.getAllDocuments(this.page-1)
        }, error => {
        console.log(error)
      })
  }
}
