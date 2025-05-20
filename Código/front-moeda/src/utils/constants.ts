import { LayoutDashboard, Coins, History, User, Settings, Building2, Users, LogOut } from "lucide-react";

export const userDropdownItems = [
  {
    nome: "Perfil",
    url: "/perfil",
    icon: User
  },
  {
    nome: "Configurações",
    url: "/configuracoes",
    icon: Settings
  },
  {
    nome: "Sair",
    url: "/logout",
    icon: LogOut
  }
];

export const menuPrincipalSecretaria = [
  {
    nome: "Visão Geral",
    url: "/dashboard/secretaria",
    icon: LayoutDashboard
  },
  {
    nome: "Gerenciar Alunos",
    url: "/dashboard/secretaria/gerenciar-aluno",
    icon: Users
  },
  {
    nome: "Gerenciar Empresas",
    url: "/dashboard/secretaria/gerenciar-empresas",
    icon: Building2
  }
];

export const ConfiguracaoSecretaria = [
  {
    nome: "Perfil",
    url: "/dashboard/secretaria/perfil",
    icon: User
  },
  {
    nome: "Configurações",
    url: "/dashboard/secretaria/configuracoes",
    icon: Settings
  }
];

export const menuPrincipalProfessor = [
  {
    nome: "Visão Geral",
    url: "/dashboard/professor",
    icon: LayoutDashboard
  },
  {
    nome: "Enviar Moedas",
    url: "/dashboard/professor/enviar-moedas",
    icon: Coins
  },
  {
    nome: "Histórico",
    url: "/dashboard/professor/historico",
    icon: History
  }
];

export const ConfiguracaoProfessor = [
  {
    nome: "Perfil",
    url: "/dashboard/professor/perfil",
    icon: User
  },
  {
    nome: "Configurações",
    url: "/dashboard/professor/configuracoes",
    icon: Settings
  }
]; 