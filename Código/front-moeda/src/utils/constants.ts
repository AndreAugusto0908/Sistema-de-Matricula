import { LayoutDashboard, Coins, History, User, Settings, Building2, Users, LogOut, Briefcase } from "lucide-react";

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
export const menuPrincipalEmpresa = [
  {
    nome: "Visão Geral",
    url: "/dashboard/empresa",
    icon: LayoutDashboard
  },
  {
    nome: "Gerenciar Vantagens",
    url: "/dashboard/empresa/gerenciar-vantagens",
    icon: Briefcase
  }
];

export const ConfiguracaoEmpresa = [
  {
    nome: "Perfil",
    url: "/dashboard/empresa/perfil",
    icon: User
  },
  {
    nome: "Configurações",
    url: "/dashboard/empresa/configuracoes",
    icon: Settings
  }
];
export const menuPrincipalaluno = [
  {
    nome: "Visão Geral",
    url: "/dashboard/aluno",
    icon: LayoutDashboard
  },
  {
    nome: "Gerenciar Vantagens",
    url: "/dashboard/aluno/gerenciar-vantagens",
    icon: Briefcase
  }
];

export const Configuracaoaluno = [
  {
    nome: "Perfil",
    url: "/dashboard/aluno/perfil",
    icon: User
  },
  {
    nome: "Configurações",
    url: "/dashboard/aluno/configuracoes",
    icon: Settings
  }
];

export const menuPrincipalProfessor = [
  {
    nome: "Visão Geral",
    url: "/dashboard/professor",
    icon: LayoutDashboard
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