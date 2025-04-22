import { 
    User, 
    Settings, 
    Home, 
    Users, 
    GraduationCap, 
    Briefcase,
    LogOut
  } from "lucide-react";
  
  export const menuPrincipalSecretaria = [
    { nome: "Visão Geral", url: "/dashboard/secretaria", icon: Home },
    { nome: "Gerenciar Alunos", url: "/dashboard/secretaria/gerenciar-aluno", icon: Users },
    { nome: "Gerenciar Professores", url: "/dashboard/secretaria/gerenciar-professores", icon: GraduationCap },
    { nome: "Gerenciar Empresas", url: "/dashboard/secretaria/gerenciar-empresas", icon: Briefcase },
  ];
  
  export const ConfiguracaoSecretaria = [
    { nome: "Meu Perfil", url: "/dashboard/secretaria/meuPerfil", icon: User },
    { nome: "Configurações", url: "/dashboard/secretaria/configuracoes", icon: Settings },
  ];

  export const userDropdownItems = [
    { label: "Meu Perfil", url: "/dashboard/secretaria/perfil", icon: User },
    { label: "Configurações", url: "/dashboard/secretaria/configuracoes", icon: Settings },
    { label: "Sair", url: "/", icon: LogOut }
  ];

  