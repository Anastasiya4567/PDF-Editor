export class GeneratedDocument {
  id: string;
  generatedPages: Array<string>

  constructor(obj: GeneratedDocument) {
    this.id = obj.id;
    this.generatedPages = obj.generatedPages;
  }
}
