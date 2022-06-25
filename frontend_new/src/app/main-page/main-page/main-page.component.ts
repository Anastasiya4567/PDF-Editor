import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DocumentAddRequest} from "../../models/DocumentAddRequest";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AllDocumentsComponent} from "../all-documents-list/all-documents-list.component";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {

  host = 'http://localhost:8080';
  documentData: FormGroup;
  document: DocumentAddRequest = {title: ''};

  constructor(private http: HttpClient,
              private formBuilder: FormBuilder,
              private httpClient: HttpClient,
              private modalService: NgbModal,
              private allDocumentsComponent: AllDocumentsComponent) {
    this.documentData = this.formBuilder.group({
      title: ['', Validators.required]
    })
  }

  ngOnInit(): void {
  }

  openModal(content: any) {
    this.modalService.open(content);
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
      headers: headers
    }).subscribe(
      (response: any) => {
        if (response.status == 200) {
          console.log('added ' + this.document.title)
          this.allDocumentsComponent.getAllDocuments(0)
        } else {
          console.log('Error saving new document')
        }
      })
  }
}
