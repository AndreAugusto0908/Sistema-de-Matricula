'use client'
import VisualizarVantagens from "@/components/dashboard/view/VisualizarVantagens";
import { Briefcase } from "lucide-react";
import { ConfiguracaoEmpresa, menuPrincipalEmpresa } from "@/utils/constants";
import DashboardLayout from "@/components/dashboard/layout";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";

interface Vantagem {
  id: number;
  valorMoedas: number;
  descricao: string;
  foto: string;
  modo: "visualizar" | "editar";
}

const viewVantagem = () => {
    const searchParams = useSearchParams();
    const [vantagem, setVantagem] = useState<Vantagem | null>(null);

    useEffect(() => {
      const vantagemParam = searchParams.get("vantagem");
      if (vantagemParam) {
        try {
          const parsedVantagem = JSON.parse(decodeURIComponent(vantagemParam));
          setVantagem(parsedVantagem);
        } catch (error) {
          console.error("Erro ao parsear vantagem:", error);
          setVantagem(null);
        }
      }
    }, [searchParams]);

  if (!vantagem) {
    return <div className="flex justify-center items-center h-96">Carregando vantagem...</div>;
  }

      return (
    <DashboardLayout
      title="Gerenciar Vantagem"
      Icon={Briefcase}
      menuPrincipalItems={menuPrincipalEmpresa}
      configuracaoItems={ConfiguracaoEmpresa}
    >
      <VisualizarVantagens vantagem={vantagem} />
    </DashboardLayout>
  );
}

export default viewVantagem