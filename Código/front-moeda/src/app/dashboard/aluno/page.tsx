'use client';

import handleError from "@/app/ErrorHandling";
import DashboardLayout from "@/components/dashboard/layout";
import VantagensCard from "@/components/dashboard/vantagens/vantagensCard";
import { AuthContext } from "@/contexts/AuthContext";
import api from "@/service/api";
import { Configuracaoaluno, menuPrincipalaluno } from "@/utils/constants";
import { error } from "console";
import { LayoutDashboard } from "lucide-react";
import { Crete_Round } from "next/font/google";
import { useContext, useEffect, useState } from "react";
import toast from "react-hot-toast";

interface Vantagem {
  idVantagem: number;
  nomeEmpresaVantagem: string;
  descricaoVantagem: string;
  valorVantagem: string;
}

export default function DashboardAluno() {
  const { user } = useContext(AuthContext)
  const [ vantagens, setVantagens ] = useState<Vantagem[]>([])

  useEffect(() =>{
    vantagensDisponiveis()
  }, [])

  const vantagensDisponiveis = async () => {
    try{
      const response = await api.get<Vantagem[]>(`vantagem-aluno/obterVantagensDiponiveisPorAluno?id=${user?.id}`)
      setVantagens(response.data)
    }catch(error){
      handleError(error);
    }
  }

const resgatarVantagem = async (vantagem: Vantagem) => {
  try {
    const response = await api.post("vantagem-aluno/resgatar", {
      idAluno: user?.id,
      idVantagem: vantagem.idVantagem
    });
    await vantagensDisponiveis();
    if (user) {
      const valor = Number(vantagem.valorVantagem.replace(',', '.'));
      if (!isNaN(valor)) {
        user.saldo = Number(user.saldo) - valor;
      } else {
        console.error('Valor da vantagem inválido:', vantagem.valorVantagem);
      }
      toast.success("Vantagem Resgatada com Sucesso")
    }
  } catch (error) {
    handleError(error);
  }
};


    return (
      <DashboardLayout
        title="Visão Geral"
        Icon={LayoutDashboard}
        menuPrincipalItems={menuPrincipalaluno}
        configuracaoItems={Configuracaoaluno}
      >
        <main>
        <section className="flex flex-wrap justify-between pr-10 pl-10">
          <div>
            <p className="text-lg sm:text-xl font-medium text-gray-700">👋 Bem-vindo, <span className="font-semibold">{user?.nome}</span></p>
          </div>
          <div className="mt-4 sm:mt-0">
            <p className="text-sm text-gray-500">Saldo disponível</p>
            <p className="text-2xl font-bold text-green-600">R$ {Number(user?.saldo).toFixed(2)}</p>
          </div>
        </section>
        
        <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 px-4">
          {vantagens.map((vantagem) => (
            <VantagensCard
              key={vantagem.idVantagem}
              descricao={vantagem.descricaoVantagem}
              valor={vantagem.valorVantagem}
              empresa={vantagem.nomeEmpresaVantagem}
              onResgatar={() => resgatarVantagem(vantagem)}
            />
          ))}
        </section>
        </main>
      </DashboardLayout>
    );
  }