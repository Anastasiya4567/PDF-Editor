import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {IKeyboardShortcutListenerOptions, KeyboardKeys} from 'ngx-keyboard-shortcuts';
import {PDFDocument} from "../models/PDFDocument";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {GeneratedDocument} from "../models/GeneratedDocument";

@Component({
  selector: 'app-document-edition-window',
  templateUrl: './document-edition-window.component.html',
  styleUrls: ['./document-edition-window.component.scss']
})
export class DocumentEditionWindowComponent implements OnInit {

  title: string | null = this.activatedRoute.snapshot.paramMap.get('title')
  host = 'http://localhost:8080';
  document: PDFDocument;
  generatedDocument: GeneratedDocument;
  sourceCode: FormGroup;

  saveKeyboardShortcutDef: IKeyboardShortcutListenerOptions = {
    description: 'recompile source code',
    keyBinding: [KeyboardKeys.Ctrl, 's']
  }

  constructor(private httpClient: HttpClient,
              private router: Router,
              private modalService: NgbModal,
              private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder,
              private sanitizer: DomSanitizer) {
    this.sourceCode = this.formBuilder.group({
      text: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.getDocumentByTitle();
  }

  convertImage(generatedPage: String) : SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + generatedPage);
  }

  private getDocumentByTitle() {
    const headers = new HttpHeaders();
    this.httpClient.get(this.host + '/getDocumentByTitle?title=' + this.title, {
      headers: headers
    }).subscribe(
      (response: any) => {
        this.document = response;
        this.getGeneratedDocument();
        console.log(this.document);
      });

  }

  backToMainPage() {
    this.router.navigate(['/'], {relativeTo: this.activatedRoute})
  }

  openModal(content: any) {
    this.modalService.open(content);
  }

  closeModal(modal: any) {
    modal.close();
  }

  saveSourceCode(modal: any) {
    if (this.sourceCode.invalid)
      return;

    const headers = new HttpHeaders();
    const newHeaders = headers.append('Content-Type', 'application/json');
    const body = {
      id: this.document.id,
      title: this.title,
      sourceCode: this.sourceCode.value.text
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

    this.closeModal(modal);
  }

  recompile() {
    console.log('recompiled')
  }

  private getGeneratedDocument() {
    const headers = new HttpHeaders();
    console.log(this.document.generatedDocumentId)

    this.httpClient.get(this.host + '/getGeneratedDocument?id=' + this.document.generatedDocumentId, {
      headers: headers
    }).subscribe(
      (response: any) => {
        console.log(response)
        this.generatedDocument = response;
      })
  }
}
