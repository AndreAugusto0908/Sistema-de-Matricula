"use client";

import { useEffect, useState } from "react";
import DashboardLayout from "@/components/dashboard/layout";
import { EmpresasTable } from "@/components/dashboard/list/empresasList";
import { SearchAddEmpresa } from "@/components/dashboard/searchbar/addEmpresa";
import { ConfiguracaoSecretaria, menuPrincipalSecretaria } from "@/utils/constants";
import { Building2 } from "lucide-react"; // Ícone diferente para empresas
import api from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import handleError from "@/app/ErrorHandling";

interface Empresa {
  id: number;
  nome: string;
  documento: string;
}

export default function GerenciarEmpresa() {
  const [dados, setDados] = useState<Empresa[]>([]);
  const [filtro, setFiltro] = useState("");
  const [dadosFiltrados, setDadosFiltrados] = useState<Empresa[]>([]);

  useEffect(() => {
    const fetchEmpresas = async () => {
      try {
        const res = await api.get(ENDPOINTS.EMPRESA.GETALL);
        setDados(res.data || []);
      } catch (error) {
        handleError(error);
        console.error("Erro ao buscar empresas:", error);
      }
    };

    fetchEmpresas();
  }, []);

  useEffect(() => {
    if (!filtro) {
      setDadosFiltrados(dados);
    } else {
      const termo = filtro.toLowerCase();
      setDadosFiltrados(
        dados.filter((empresa) =>
          empresa.nome.toLowerCase().includes(termo) ||
          empresa.documento.toLowerCase().includes(termo)
        )
      );
    }
  }, [filtro, dados]);

  return (
    <DashboardLayout
      title="Gerenciar Empresas"
      Icon={Building2}
      menuPrincipalItems={menuPrincipalSecretaria}
      configuracaoItems={ConfiguracaoSecretaria}
    >
      <div className="w-full flex flex-col justify-center gap-4">
        {/* Barra de pesquisa + botão adicionar empresa */}
        <div className="w-full mb-4">
          <SearchAddEmpresa setFiltro={setFiltro} />
        </div>

        {/* Tabela de Empresas */}
        <EmpresasTable
          data={dadosFiltrados}
          onDelete={(empresaDeletada) =>
            setDados((prev) => prev.filter((e) => e.id !== empresaDeletada.id))
          }
        />
      </div>
    </DashboardLayout>
  );
}
