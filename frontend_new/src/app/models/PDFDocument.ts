export class PDFDocument {
  id: string;
  title: string;

  constructor(obj: PDFDocument) {
    this.id = obj.id;
    this.title = obj.title;
  }
}
