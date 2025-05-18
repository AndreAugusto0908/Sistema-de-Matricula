'use client';

import DashboardLayout from "@/components/dashboard/layout";
import { ConfiguracaoSecretaria, menuPrincipalSecretaria } from "@/utils/constants";
import { LayoutDashboard } from "lucide-react";

export default function DashboardAluno() {
    return (
      <DashboardLayout
        title="Visão Geral"
        Icon={LayoutDashboard}
        menuPrincipalItems={menuPrincipalSecretaria}
        configuracaoItems={ConfiguracaoSecretaria}
      >
        <h1 className="text-2xl font-bold">Bem-vindo (Nome do Aluno) ao Painel</h1>
        <p>Este é o painel principal.</p>
      </DashboardLayout>
    );
  }