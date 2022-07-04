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

  isSubmitted = false;

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

  onSubmit(modal: any) {
    this.isSubmitted = true;

    if (this.documentData.invalid) {
      console.log('empty title');
      return;
    }

    if (this.checkIfValid()) {
      this.sendNewDocumentRequest();
      modal.close()
    }
  }

  sendNewDocumentRequest() {
    const headers = new HttpHeaders();

    this.httpClient.post(this.host + '/add?title=' + this.documentData.value.title, {
      headers: headers
    }).subscribe(
      (response: any) => {
        if (response.status == 200) {
          console.log('added ' + this.documentData.value.title)
          this.allDocumentsComponent.getAllDocuments(0)
        } else {
          console.log('Error saving new document')
        }
      })
  }

  closeModal(modal: any) {
    modal.close();
  }

  checkIfValid() : any {
    const headers = new HttpHeaders();

    this.httpClient.get(this.host + '/checkIfUnique?title=' + this.documentData.value.title, {
      headers: headers
    }).subscribe(
      (response: any) => {
        console.log(response.status)
        if (response.status == 200) {
          console.log('checked if unique: ' + this.document.title)
          console.log(response)
          return response;
        } else {
          console.log('Error checking if new document title is unique')
        }
      })
  }
}
