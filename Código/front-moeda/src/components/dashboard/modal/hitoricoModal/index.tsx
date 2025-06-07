'use client'

import  api from "@/service/api"
import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { parseCookies } from "nookies"
import { useAuth } from "@/contexts/AuthContext"

type HistoricoVantagem = {
    nomeAluno: string;
    valorVantagem: number;
    descricaoVantagem: string;
}

interface VantagemFormProps {
    closeModal: () => void;
}

const HistoricoModal = ({closeModal} : VantagemFormProps) => {
    const [historico, setHistorico] = useState<HistoricoVantagem[]>([]);
    const [error, setError] = useState<string | null>(null);
    const router = useRouter();
    const { user, isAuthenticated } = useAuth();

    useEffect(() => {
        console.log('Estado da autenticação:', { isAuthenticated, user });
        
        if (!isAuthenticated) {
            setError("Você precisa estar logado para acessar esta funcionalidade");
            return;
        }

        if (user?.role !== "ROLE_EMPRESA") {
            console.log('Papel do usuário:', user?.role);
            setError("Você precisa estar logado como empresa para acessar esta funcionalidade");
            return;
        }

        getHistoricoVantagens();
    }, [isAuthenticated, user]);

    const getHistoricoVantagens = async () => {
        try {
            const cookies = parseCookies();
            const token = cookies['nextauth.token'];
            if (!token) {
                setError("Você precisa estar logado para acessar esta funcionalidade");
                return;
            }

            console.log('ID da empresa:', user?.id);
            const response = await api.get(`/vantagem-aluno/empresa?empresa=${user?.id}`);
            setHistorico(response.data);
            setError(null);
            console.log('Resposta da API:', response.data);
        } catch (error: any) {
            console.error("Erro ao obter histórico:", error);
            if (error.response?.status === 403) {
                setError("Você não tem permissão para acessar este histórico. Verifique se está logado como empresa.");
            } else if (error.response?.status === 401) {
                setError("Sua sessão expirou. Por favor, faça login novamente");
            } else {
                setError("Erro ao carregar histórico. Tente novamente mais tarde.");
            }
        }
    }

    return (
        <div className="w-full flex flex-col justify-center gap-4 vantagem_form">
            {error ? (
                <div className="text-red-500 text-center p-4">
                    {error}
                </div>
            ) : historico.length === 0 ? (
                <div className="text-gray-500 text-center p-4">
                    Nenhum histórico encontrado
                </div>
            ) : (
                historico.map((item, index) => (
                    <div key={index} className="p-4 border-b last:border-b-0">
                        <h3 className="text-lg font-semibold">{item.descricaoVantagem}</h3>
                        <p className="text-gray-600">Aluno: {item.nomeAluno}</p>
                        <p className="text-gray-600">Valor: {item.valorVantagem} moedas</p>
                    </div>
                ))
            )}
            <button
                type="button"
                className="w-full bg-yellow-500 text-white py-2 rounded hover:bg-yellow-600 transition-colors"
                onClick={closeModal}
            >
                Fechar
            </button>
        </div>
    );
}

export default HistoricoModal;