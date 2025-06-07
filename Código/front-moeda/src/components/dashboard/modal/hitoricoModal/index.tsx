'use client'

import  api from "@/service/api"
import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { parseCookies } from "nookies"
import { useAuth } from "@/contexts/AuthContext"

type Vantagem = {
    id: number;
    valorMoedas: number;
    descricao: string;
    foto: string;
    empresa: {
        id: number;
        nome: string;
    }
}

interface VantagemFormProps {
    closeModal: () => void;
}

const HistoricoModal = ({closeModal} : VantagemFormProps) => {
    const [vantagens, setVantagens] = useState<Vantagem[]>([]);
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

        getVantagens();
    }, [isAuthenticated, user]);

    const getVantagens = async () => {
        try {
            const cookies = parseCookies();
            const token = cookies['nextauth.token'];
            if (!token) {
                setError("Você precisa estar logado para acessar esta funcionalidade");
                return;
            }

            console.log('ID da empresa:', user?.id);
            const response = await api.get(`/vantagem/obterPorEmpresa?empresa=${user?.id}`);
            setVantagens(response.data);
            setError(null);
            console.log('Resposta da API:', response.data);
        } catch (error: any) {
            console.error("Erro ao obter vantagens:", error);
            if (error.response?.status === 403) {
                setError("Você não tem permissão para acessar estas vantagens. Verifique se está logado como empresa.");
            } else if (error.response?.status === 401) {
                setError("Sua sessão expirou. Por favor, faça login novamente");
            } else {
                setError("Erro ao carregar vantagens. Tente novamente mais tarde.");
            }
        }
    }

    return (
        <div className="w-full flex flex-col justify-center gap-4 vantagem_form">
            {error ? (
                <div className="text-red-500 text-center p-4">
                    {error}
                </div>
            ) : vantagens.length === 0 ? (
                <div className="text-gray-500 text-center p-4">
                    Nenhuma vantagem encontrada
                </div>
            ) : (
                vantagens.map((vantagem) => (
                    <div key={vantagem.id} className="p-4 border-b last:border-b-0">
                        <h3 className="text-lg font-semibold">{vantagem.descricao}</h3>
                        <p className="text-gray-600">Valor: {vantagem.valorMoedas} moedas</p>
                        <p className="text-gray-600">Empresa: {vantagem.empresa.nome}</p>
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