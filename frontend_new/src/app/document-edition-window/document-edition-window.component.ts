import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DocumentAddRequest} from "../models/DocumentAddRequest";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {IKeyboardShortcutListenerOptions, KeyboardKeys} from 'ngx-keyboard-shortcuts';

@Component({
  selector: 'app-document-edition-window',
  templateUrl: './document-edition-window.component.html',
  styleUrls: ['./document-edition-window.component.scss']
})
export class DocumentEditionWindowComponent implements OnInit {

  title: string | null = this.activatedRoute.snapshot.paramMap.get('title')
  host = 'http://localhost:8080';

  sourceCode: FormGroup;
  document: DocumentAddRequest = {title: ''};

  saveKeyboardShortcutDef: IKeyboardShortcutListenerOptions = {
    description: 'recompile source code',
    keyBinding: [KeyboardKeys.Ctrl, 's']
  }

  constructor(private httpClient: HttpClient,
              private router: Router,
              private modalService: NgbModal,
              private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder) {
    this.sourceCode = this.formBuilder.group({
      text: ['', Validators.required]
    })
  }

  ngOnInit(): void {
  }

  backToMainPage() {
    this.router.navigate(['/'], {relativeTo: this.activatedRoute})
  }

  openModal(content: any) {
    this.modalService.open(content);
  }

  saveSourceCode() {

    const headers = new HttpHeaders();
    const newHeaders = headers.append('Content-Type', 'application/json');
    const body = {
      id: "not set yet",
      title: this.title,
      sourceCode : this.sourceCode.value.text
    }

    this.httpClient.post(this.host + '/generateFromSourceText', JSON.stringify(body), {
      headers: newHeaders
    }).subscribe(
      (response: any) => {
        if (response.status == 200) {
          console.log('generated ' + this.title)
          this.backToMainPage();
        } else {
          console.log('Error saving generated document')
        }
      })
  }

  recompile() {
    console.log('recompiled')
  }
}
