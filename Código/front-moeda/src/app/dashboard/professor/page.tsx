'use client';

import DashboardLayout from "@/components/dashboard/layout";
import { ConfiguracaoProfessor, menuPrincipalProfessor } from "../../../utils/constants";
import { LayoutDashboard } from "lucide-react";
import { EnviarMoedas } from '../../dashboard/components/EnviarMoedas';
import HistoricoTransacoes from '../../dashboard/components/HistoricoTransacoes';
import { useAuth } from "@/contexts/AuthContext";

export default function DashboardProfessor() {
    const { user } = useAuth();

    return (
        <DashboardLayout
            title="Visão Geral"
            Icon={LayoutDashboard}
            menuPrincipalItems={menuPrincipalProfessor}
            configuracaoItems={ConfiguracaoProfessor}
        >
            <div className="w-full flex flex-col gap-8">
                <div>
                    <h1 className="text-2xl font-bold mb-4">Bem-vindo {user?.nome} ao Painel do Professor</h1>
                    <p className="text-gray-600">Gerencie suas moedas e visualize o histórico de transações.</p>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                    <div>
                        <EnviarMoedas documentoProfessor={user?.documento || ''} />
                    </div>
                    
                    <div>
                        <HistoricoTransacoes documentoProfessor={user?.documento || ''} />
                    </div>
                </div>
            </div>
        </DashboardLayout>
    );
} 