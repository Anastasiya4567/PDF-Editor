import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DocumentAddRequest} from "../../models/DocumentAddRequest";

@Component({
  selector: 'app-new-document-modal',
  templateUrl: './new-document-modal.component.html',
  styleUrls: ['./new-document-modal.component.scss']
})
export class NewDocumentModalComponent implements OnInit {

  // NOT WORKING!

  @Output()
  newDocumentEmitter = new EventEmitter<DocumentAddRequest>();

  documentData: FormGroup;
  document: DocumentAddRequest = {title: ''};

  constructor(private formBuilder: FormBuilder) {
    this.documentData = this.formBuilder.group({
      title: ['', Validators.required]
    })
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.newDocumentEmitter.emit(document);
  }

}
