export class GeneratedDocument {
  id: string;
  documentId: string;
  generatedPages: Array<string>

  constructor(obj: GeneratedDocument) {
    this.id = obj.id;
    this.documentId = obj.documentId;
    this.generatedPages = obj.generatedPages;
  }
}
