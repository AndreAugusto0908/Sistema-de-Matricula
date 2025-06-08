'use client'

import DashboardLayout from "@/components/dashboard/layout";
import { ConfiguracaoEmpresa, menuPrincipalaluno } from "@/utils/constants";
import { Briefcase } from "lucide-react";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "@/contexts/AuthContext";
import { parseCookies } from "nookies";
import { VantagemsTable } from "@/components/dashboard/list/vantagemList";
import { SearchGerenciarVantagemAluno } from "@/components/dashboard/searchbar/manageVantagem";
import api from "@/service/api";
import handleError from "@/app/ErrorHandling";
import VantagensCard from "@/components/dashboard/vantagens/vantagensCard";


interface Vantagem {
  idVantagem: number;
  nomeEmpresa: string;
  descricao: string;
  valor: number;
  nomeAluno: string;
  cupom: string;
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
        const res = await api.get<Vantagem[]>(`/vantagem-aluno/obterPorAluno?id=${user?.id}`)
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
        menuPrincipalItems={menuPrincipalaluno}
        configuracaoItems={ConfiguracaoEmpresa}
      >
      <div className="w-full flex flex-col justify-center gap-4">
        {/* Barra de pesquisa + botão adicionar aluno */}
        <div className="w-full mb-4">
          <SearchGerenciarVantagemAluno setFiltro={setFiltro} />
        </div>

        
        <div>
        <h2 className="text-xl font-semibold text-gray-800 mb-2">Vantagens Resgatadas</h2>
        </div>

        <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {dadosFiltrados.map((vantagem) => (
            <VantagensCard
            key={vantagem.idVantagem}
            descricao={vantagem.descricao}
            valor={vantagem.valor.toFixed(2)}
            empresa={vantagem.nomeEmpresa}
            cupom={vantagem.cupom}
            />
        ))}
        </section>

      </div>
      </DashboardLayout>
  )
}

export default GerenciarVantagens;