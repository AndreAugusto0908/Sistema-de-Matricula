"use client";

import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import DashboardLayout from "@/components/dashboard/layout";
import VisualizarEmpresa from "@/components/dashboard/view/VizualizarEmpresa";
import { ConfiguracaoSecretaria, menuPrincipalSecretaria } from "@/utils/constants";
import { Building2 } from "lucide-react";

interface Empresa {
  id: number;
  nome: string;
  documento: string;
  modo: "visualizar" | "editar";
}

export default function ViewEmpresa() {
  const searchParams = useSearchParams();
  const [empresa, setEmpresa] = useState<Empresa | null>(null);

  useEffect(() => {
    const empresaParam = searchParams.get("empresa");
    if (empresaParam) {
      try {
        const parsedEmpresa = JSON.parse(decodeURIComponent(empresaParam));
        setEmpresa(parsedEmpresa);
      } catch (error) {
        console.error("Erro ao parsear empresa:", error);
        setEmpresa(null);
      }
    }
  }, [searchParams]);

  if (!empresa) {
    return <div className="flex justify-center items-center h-96">Carregando empresa...</div>;
  }

  return (
    <DashboardLayout
      title="Gerenciar Empresa"
      Icon={Building2}
      menuPrincipalItems={menuPrincipalSecretaria}
      configuracaoItems={ConfiguracaoSecretaria}
    >
      <VisualizarEmpresa empresa={empresa} />
    </DashboardLayout>
  );
}
