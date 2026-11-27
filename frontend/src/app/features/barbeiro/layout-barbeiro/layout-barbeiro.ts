import { Component, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { BarraNavegacao } from '../../../shared/components/barra-navegacao/barra-navegacao';

@Component({
  selector: 'app-layout-barbeiro',
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    BarraNavegacao,
  ],
  templateUrl: './layout-barbeiro.html',
  styleUrl: './layout-barbeiro.css',
})
export class LayoutBarbeiro {
  protected readonly menuAberto = signal(true);

  protected alternarMenu(): void {
    this.menuAberto.update((aberto) => !aberto);
  }
}
