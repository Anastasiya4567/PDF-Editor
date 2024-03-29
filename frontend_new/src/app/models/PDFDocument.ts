export class PDFDocument {
  id: string;
  title: string;
  ownerEmail: string;
  privateAccess: boolean;
  sourceCode: string;
  creationDate: Date | string;

  constructor(sourceCode: string) {
    this.sourceCode = sourceCode;
  }
}
