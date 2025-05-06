"use client";

import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import DashboardLayout from "@/components/dashboard/layout";
import VisualizarAluno from "@/components/dashboard/view/VizualizarAluno";
import { ConfiguracaoSecretaria, menuPrincipalSecretaria } from "@/utils/constants";
import { Users } from "lucide-react";

interface Aluno {
  id: number;
  tipoConta: string;
  documento: string;
  nome: string;
  instituicao: string;
  curso: string;
  senha: string;
  rg: string;
  endereco: string;
  email: string;
  modo: "visualizar" | "editar";
}

export default function ViewAluno() {
  const searchParams = useSearchParams();
  const [aluno, setAluno] = useState<Aluno | null>(null);

  useEffect(() => {
    const alunoParam = searchParams.get("aluno");
    if (alunoParam) {
      try {
        const parsedAluno = JSON.parse(decodeURIComponent(alunoParam));
        setAluno(parsedAluno);
      } catch (error) {
        console.error("Erro ao parsear aluno:", error);
        setAluno(null);
      }
    }
  }, [searchParams]);

  if (!aluno) {
    return <div className="flex justify-center items-center h-96">Carregando aluno...</div>;
  }

  return (
    <DashboardLayout
      title="Gerenciar Aluno"
      Icon={Users}
      menuPrincipalItems={menuPrincipalSecretaria}
      configuracaoItems={ConfiguracaoSecretaria}
    >
      <VisualizarAluno aluno={aluno} />
    </DashboardLayout>
  );
}
