import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-document-edition-window',
  templateUrl: './document-edition-window.component.html',
  styleUrls: ['./document-edition-window.component.scss']
})
export class DocumentEditionWindowComponent implements OnInit {

  constructor(private router: Router,
              private modalService: NgbModal,
              private activatedRoute: ActivatedRoute) {
  }

  title: string | null = this.activatedRoute.snapshot.paramMap.get('title')

  ngOnInit(): void {
  }

  backToMainPage() {
    this.router.navigate(['/'], {relativeTo: this.activatedRoute})
  }

  openModal(content: any) {
    this.modalService.open(content);
  }


}
