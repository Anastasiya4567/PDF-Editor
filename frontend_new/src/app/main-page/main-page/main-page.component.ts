import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NewDocumentCreateRequest} from "../../models/NewDocumentCreateRequest";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AllDocumentsComponent} from "../all-documents-list/all-documents-list.component";
import {DocumentService} from "../../services/document/document.service";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {

  documentData: FormGroup;
  createdDocument = new NewDocumentCreateRequest();
  message: string;

  isSubmitted = false;

  constructor(private http: HttpClient,
              private formBuilder: FormBuilder,
              private httpClient: HttpClient,
              private modalService: NgbModal,
              private allDocumentsComponent: AllDocumentsComponent,
              private documentService: DocumentService) {
    this.documentData = this.formBuilder.group({
      title: ['', Validators.required],
      privateAccess: true
    })
  }

  ngOnInit(): void {
  }

  openModal(content: any) {
    this.modalService.open(content);
  }

  onSubmit(modal: any) {
    this.isSubmitted = true;

    if (this.documentData.invalid) {
      return;
    }

    this.createNewDocument();
    modal.close();
  }

  createNewDocument() {
    this.message = '';

    this.createdDocument.title = this.documentData.value.title;
    this.createdDocument.privateAccess = this.documentData.value.privateAccess;

    this.documentService.createNewDocument(this.createdDocument).subscribe(
      (response: any) => {
        if (response.status == 200) {
          this.allDocumentsComponent.getAllDocuments(0)
        } else {
          this.message = response.message;
        }
      })
  }

  closeModal(modal: any) {
    modal.close();
  }

}
