"use client";

import { useEffect, useState } from "react";
import DashboardLayout from "@/components/dashboard/layout";
import { AlunosTable } from "@/components/dashboard/list/alunoList";
import { SearchAddAluno } from "@/components/dashboard/searchbar/addAluno";
import { ConfiguracaoSecretaria, menuPrincipalSecretaria } from "@/utils/constants";
import { Users } from "lucide-react";
import { api } from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import { SearchAddEmpresa } from "@/components/dashboard/searchbar/addEmpresa";

interface Aluno {
  id: number;
  nome: string;
  curso: string;
  documento: string;
}

export default function GerenciarAluno() {
  return (
    <DashboardLayout
      title="Gerenciar Empresas"
      Icon={Users}
      menuPrincipalItems={menuPrincipalSecretaria}
      configuracaoItems={ConfiguracaoSecretaria}
    >
      <div className="w-full flex flex-col justify-center gap-4">
        {/* Barra de pesquisa + botão adicionar aluno */}
        <div className="w-full mb-4">
          <SearchAddEmpresa setFiltro={setFiltro} />
        </div>

        {/* Tabela de Alunos */}
        <AlunosTable
          data={dadosFiltrados}
          onDelete={(alunoDeletado) => {
            setDados((prev) => prev.filter((a) => a.id !== alunoDeletado.id));
          }}
        />
      </div>
    </DashboardLayout>
  );
}
