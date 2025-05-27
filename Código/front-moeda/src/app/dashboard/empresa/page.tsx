'use client';

import DashboardLayout from "@/components/dashboard/layout";
import { ConfiguracaoEmpresa, menuPrincipalEmpresa } from "@/utils/constants";
import { LayoutDashboard } from "lucide-react";
import { useContext } from "react";
import { AuthContext } from "@/contexts/AuthContext";
export default function DashboardEmpresa() {

    const { user } = useContext(AuthContext);

    return (
      <DashboardLayout
        title="Visão Geral"
        Icon={LayoutDashboard}
        menuPrincipalItems={menuPrincipalEmpresa}
        configuracaoItems={ConfiguracaoEmpresa}
      >
        <h1 className="text-2xl font-bold">Bem-vindo ao Painel {user?.nome}</h1>
        <p>Este é o painel principal.</p>
      </DashboardLayout>
    );
  }