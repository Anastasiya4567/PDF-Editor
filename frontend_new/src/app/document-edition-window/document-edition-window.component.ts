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
import {Image} from "../models/Image";
import jsPDF from "jspdf";

@Component({
  selector: 'app-document-edition-window',
  templateUrl: './document-edition-window.component.html',
  styleUrls: ['./document-edition-window.component.scss']
})
export class DocumentEditionWindowComponent implements OnInit {

  id: string | null = this.activatedRoute.snapshot.paramMap.get('id')
  document: PDFDocument = new PDFDocument('');
  generatedDocument: GeneratedDocument;
  sourceCode: FormGroup;
  images: Image[] = [];
  loading: boolean = false;

  saveKeyboardShortcutDef: IKeyboardShortcutListenerOptions = {
    description: 'recompile source code',
    // TODO
    keyBinding: [KeyboardKeys.Ctrl, 'r']
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
    });
  }

  ngOnInit(): void {
    this.getDocumentById();
  }

  convertPage(generatedPage: String): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + generatedPage);
  }

  private getDocumentById() {
    this.documentService.getDocumentById(this.id).subscribe(
      (response: any) => {
        this.document = response;
        this.sourceCode.value.text = this.document.sourceCode;
        this.getGeneratedDocument();
        this.getImages();
      });
  }

  backToMainPage() {
    this.router.navigate(['/main-page'], {relativeTo: this.activatedRoute})
  }

  openModal(content: any) {
    if (this.document.sourceCode != this.sourceCode.value.text)
      this.modalService.open(content);
    else
      this.backToMainPage();
  }

  closeModal(modal: any) {
    modal.close();
  }

  public onFileChanged(event: any): void {

    for (const file of event.target.files) {
      const fileReader = new FileReader();

      fileReader.onloadend = (e) => {
        const data = fileReader.result as string;
        const image = new Image();
        image.name = file.name;
        image.data = data.slice(data.indexOf('base64,') + 'base64,'.length, data.length);
        image.extension = data.slice(11, data.indexOf(';base64,'));
        this.saveImage(image);
        this.images.push(image);
      };
      fileReader.readAsDataURL(file);
    }
  }

  convertImage(image: Image): string {
    return ('data:image/' + image.extension + ';base64,' + image.data);
  }

  save() {
    if (this.sourceCode.invalid)
      return;

    const body = {
      id: this.document.id,
      sourceCode: this.sourceCode.value.text
    }

    this.generatedDocumentService.generateDocument(body).subscribe(
      (response: any) => {
        if (response.status == 200) {
          console.log('generated ' + this.document.title)
          // this.getDocumentById();
        } else {
          console.log('Error saving generated document')
        }
      })
  }

  saveSourceCodeAndExit(modal: any) {
    this.save();
    this.closeModal(modal);
    this.backToMainPage();
  }

  recompile() {
    console.log('recompiled')
    this.save();
  }

  private getGeneratedDocument() {
    // checks
    this.generatedDocumentService.getGeneratedDocument(this.document.id).subscribe(
      (response: any) => {
        console.log(response)
        this.generatedDocument = response;
      })
  }

  private getImages() {
    this.generatedDocumentService.getImages(this.document.id).subscribe(
      (response: any) => {
        console.log(response)
        this.images = response;
      })
  }

  private saveImage(image: Image) {
    this.generatedDocumentService.saveImage(image, this.document.id).subscribe(
      (response: any) => {
        console.log(response)
      })
  }

  download() {
    let doc = new jsPDF('p', 'px', 'a4');
    const width = doc.internal.pageSize.getWidth();
    const height = doc.internal.pageSize.getHeight();

    for (let i = 0; i < this.generatedDocument.generatedPages.length; i++) {
      let pageData: any = this.generatedDocument.generatedPages[i];

      doc.addImage(
        pageData,
        'JPG',
        10,
        10,
        width,
        height
      );
      if (!this.isLastPage(i)) {
        doc.addPage();
      }
    }
    doc.save(this.document.title + '.pdf');
  }

  private isLastPage(pageNumber: number) {
    return pageNumber == this.generatedDocument.generatedPages.length - 1;
  }
}
