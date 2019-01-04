import { Router } from '@angular/router';
import { Component, OnInit, Input } from "@angular/core";
import { Evento } from '../../../model/evento.model';

@Component({
  selector: "card",
  templateUrl: "card.component.html",
  styleUrls: ["card.component.scss"]
})
export class CardComponent implements OnInit {
  @Input() value: Evento;
  constructor(private router: Router) {}

  ngOnInit() {}

  showDetail(codigo) {
      this.router.navigate(['/eventos/detalhe', codigo]);
  }
}
