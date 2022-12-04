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
      console.log('empty title');
      return;
    }

    this.createNewDocument();
    modal.close();
  }

  createNewDocument() {

    console.log(this.documentData.value.title)
    console.log(this.documentData.value.privateAccess)

    this.createdDocument.title = this.documentData.value.title;
    this.createdDocument.privateAccess = this.documentData.value.privateAccess;

    this.documentService.createNewDocument(this.createdDocument).subscribe(
      (response: any) => {
        console.log(response)
        if (response.status == 200) {
            console.log(response.message)
            this.allDocumentsComponent.getAllDocuments(0)
        } else {
          console.log('Error saving new document')
        }
      })
  }

  closeModal(modal: any) {
    modal.close();
  }

}
