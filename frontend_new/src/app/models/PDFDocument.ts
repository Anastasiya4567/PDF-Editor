export class PDFDocument {
  id: string;
  title: string;
  sourceCode: string;
  generatedDocumentId: string;

  constructor(obj: PDFDocument) {
    this.id = obj.id;
    this.title = obj.title;
    this.sourceCode = obj.title;
    this.generatedDocumentId = obj.generatedDocumentId
  }
}
