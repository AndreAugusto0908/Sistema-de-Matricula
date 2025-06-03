"use client";

import { useEffect, useState } from "react";
import DashboardLayout from "@/components/dashboard/layout";
import { AlunosTable } from "@/components/dashboard/list/alunoList";
import { SearchAddAluno } from "@/components/dashboard/searchbar/addAluno";
import { ConfiguracaoSecretaria, menuPrincipalSecretaria } from "@/utils/constants";
import { Users } from "lucide-react";
import { api } from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import handleError from "@/app/ErrorHandling";

interface Aluno {
  id: number;
  nome: string;
  curso: string;
  documento: string;
}

export default function GerenciarAluno() {
  const [filtro, setFiltro] = useState("");
  const [dados, setDados] = useState<Aluno[]>([]);

  const fetchAlunos = async () => {
    try {
      const { data } = await api.get<Aluno[]>(`${ENDPOINTS.ALUNO.GETALL}`);
      setDados(data);
    } catch (error) {
      handleError(error);
      console.error("Erro ao buscar alunos:", error);
    }
  };

  useEffect(() => {
    fetchAlunos();
  }, []);

  const dadosFiltrados = dados.filter((aluno) =>
    aluno.nome.toLowerCase().includes(filtro.toLowerCase())
  );

  return (
    <DashboardLayout
      title="Gerenciar Alunos"
      Icon={Users}
      menuPrincipalItems={menuPrincipalSecretaria}
      configuracaoItems={ConfiguracaoSecretaria}
    >
      <div className="w-full flex flex-col justify-center gap-4">
        {/* Barra de pesquisa + botão adicionar aluno */}
        <div className="w-full mb-4">
          <SearchAddAluno setFiltro={setFiltro} />
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
