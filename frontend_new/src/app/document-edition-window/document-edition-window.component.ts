import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {IKeyboardShortcutListenerOptions, KeyboardKeys} from 'ngx-keyboard-shortcuts';
import {PDFDocument} from "../models/PDFDocument";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {GeneratedDocument} from "../models/GeneratedDocument";
import {CookieService} from "ngx-cookie-service";
import {GeneratedDocumentService} from "../services/generated-document/generated-document.service";
import {DocumentService} from "../services/document/document.service";

@Component({
  selector: 'app-document-edition-window',
  templateUrl: './document-edition-window.component.html',
  styleUrls: ['./document-edition-window.component.scss']
})
export class DocumentEditionWindowComponent implements OnInit {

  id: string | null = this.activatedRoute.snapshot.paramMap.get('id')
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
              private cookieService: CookieService,
              private sanitizer: DomSanitizer,
              private documentService: DocumentService,
              private generatedDocumentService: GeneratedDocumentService) {
    this.sourceCode = this.formBuilder.group({
      text: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.getDocumentById();
  }

  convertImage(generatedPage: String): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + generatedPage);
  }

  private getDocumentById() {

    this.documentService.getDocumentById(this.id).subscribe(
      (response: any) => {
        this.document = response;
        this.getGeneratedDocument();
        console.log(this.document);
      });
  }

  backToMainPage() {
    this.router.navigate(['/main-page'], {relativeTo: this.activatedRoute})
  }

  openModal(content: any) {
    this.modalService.open(content);
  }

  closeModal(modal: any) {
    modal.close();
  }

  saveSourceCode() {
    if (this.sourceCode.invalid)
      return;

    const body = {
      id: this.document.id,
      title: this.document.title,
      sourceCode: this.sourceCode.value.text
    }

    this.generatedDocumentService.generateDocument(body).subscribe(
      (response: any) => {
        if (response.status == 200) {
          console.log('generated ' + this.document.title)
          this.backToMainPage();
        } else {
          console.log('Error saving generated document')
        }
      })
  }

  saveSourceCodeAndExit(modal: any) {
    this.saveSourceCode();
    this.closeModal(modal);
  }

  recompile() {
    console.log('recompiled')
    this.saveSourceCode();
    this.getDocumentById();
  }

  private getGeneratedDocument() {
    console.log(this.document.generatedDocumentId)

    this.generatedDocumentService.getGeneratedDocument(this.document.generatedDocumentId).subscribe(
      (response: any) => {
        console.log(response)
        this.generatedDocument = response;
      })
  }
}
