'use client'

import DashboardLayout from "@/components/dashboard/layout";
import { ConfiguracaoEmpresa, menuPrincipalEmpresa } from "@/utils/constants";
import { Briefcase } from "lucide-react";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "@/contexts/AuthContext";
import { parseCookies } from "nookies";
import { VantagemsTable } from "@/components/dashboard/list/vantagemList";
import { SearchAddVantagem } from "@/components/dashboard/searchbar/addVantagem";
import api from "@/service/api";
import handleError from "@/app/ErrorHandling";


interface Vantagem {
  id: number;
  valorMoedas: number;
  descricao: string;
  foto: string;
}

const GerenciarVantagens = () => {
  const { user, } = useContext(AuthContext);
  const [filtro, setFiltro] = useState("");

    const [ vantagens, setVantagens ] = useState<Vantagem[]>([])

    useEffect(() =>{
      makeRequest()
    }, [])

    const makeRequest = async () => {
      try {
        const cookies = parseCookies();
        const res = await api.get<Vantagem[]>(`/vantagem/obterPorEmpresa?empresa=${user?.documento}`)
        console.log("Vantagens:", res);
        setVantagens(res.data)
      } catch (error) {
        console.error("Erro ao buscar vantagens:", error);
        handleError(error);
      }
  }

    const dadosFiltrados = vantagens.filter((vantagem) =>
        vantagem.descricao.toLowerCase().includes(filtro.toLowerCase())
    );

  return (
    <DashboardLayout
        title="Gerenciar Vantagens"
        Icon={Briefcase}
        menuPrincipalItems={menuPrincipalEmpresa}
        configuracaoItems={ConfiguracaoEmpresa}
      >
      <div className="w-full flex flex-col justify-center gap-4">
        {/* Barra de pesquisa + botão adicionar aluno */}
        <div className="w-full mb-4">
          <SearchAddVantagem setFiltro={setFiltro} />
        </div>

        {/* Tabela de Alunos */}
        <VantagemsTable
          data={dadosFiltrados}
          onDelete={(alunoDeletado) => {
            setVantagens((prev) => prev.filter((a) => a.id !== alunoDeletado.id));
          }}
        />
      </div>
      </DashboardLayout>
  )
}

export default GerenciarVantagens;