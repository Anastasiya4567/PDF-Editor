export class PDFDocument {
  id: string;
  title: string;
  sourceCode: string;
  creationDate: Date | string;
  generatedDocumentId: string;

  constructor(obj: PDFDocument) {
    this.id = obj.id;
    this.title = obj.title;
    this.sourceCode = obj.title;
    this.creationDate = obj.creationDate;
    this.generatedDocumentId = obj.generatedDocumentId
  }
}
