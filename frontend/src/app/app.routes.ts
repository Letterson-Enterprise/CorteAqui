import { Routes } from '@angular/router';
import { autenticacaoGuard } from './core/guards/autenticacao.guard';
import { papelGuard } from './core/guards/papel.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'entrar' },
  {
    path: 'entrar',
    loadComponent: () => import('./features/autenticacao/login/login').then((m) => m.Login),
  },
  {
    path: 'cadastro/cliente',
    loadComponent: () =>
      import('./features/autenticacao/cadastro-cliente/cadastro-cliente').then(
        (m) => m.CadastroCliente,
      ),
  },
  {
    path: 'cadastro/barbeiro',
    loadComponent: () =>
      import('./features/autenticacao/cadastro-barbeiro/cadastro-barbeiro').then(
        (m) => m.CadastroBarbeiro,
      ),
  },
  {
    path: 'barbeiro',
    canActivate: [autenticacaoGuard, papelGuard('BARBEIRO')],
    loadComponent: () =>
      import('./features/barbeiro/layout-barbeiro/layout-barbeiro').then((m) => m.LayoutBarbeiro),
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'painel' },
      {
        path: 'painel',
        loadComponent: () =>
          import('./features/barbeiro/painel-barbeiro/painel-barbeiro').then(
            (m) => m.PainelBarbeiro,
          ),
      },
      {
        path: 'categorias',
        loadComponent: () =>
          import('./features/barbeiro/categorias/categorias').then((m) => m.Categorias),
      },
      {
        path: 'servicos',
        loadComponent: () =>
          import('./features/barbeiro/servicos/servicos').then((m) => m.Servicos),
      },
      {
        path: 'horarios',
        loadComponent: () =>
          import('./features/barbeiro/horarios/horarios').then((m) => m.Horarios),
      },
    ],
  },
  {
    path: 'cliente',
    canActivate: [autenticacaoGuard, papelGuard('CLIENTE')],
    loadComponent: () =>
      import('./features/cliente/layout-cliente/layout-cliente').then((m) => m.LayoutCliente),
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'agendar' },
      {
        path: 'agendar',
        loadComponent: () =>
          import('./features/cliente/agendamento/agendamento').then((m) => m.Agendamento),
      },
      {
        path: 'configuracoes',
        loadComponent: () =>
          import('./features/cliente/configuracoes/configuracoes').then(
            (m) => m.Configuracoes,
          ),
      },
      {
        path: 'perfil',
        loadComponent: () => import('./features/perfil/perfil').then((m) => m.Perfil),
      },
    ],
  },
  { path: '**', redirectTo: 'entrar' },
];
