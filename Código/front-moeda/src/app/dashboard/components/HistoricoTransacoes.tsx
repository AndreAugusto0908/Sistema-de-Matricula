import React, { useEffect, useState } from 'react';
import axios from 'axios';

interface Transacao {
    id: number;
    data: string;
    quantidadeMoedas: number;
    nomeOrigem: string;
    nomeDestino: string;
    mensagem: string;
}

interface HistoricoTransacoesProps {
    documentoProfessor: string;
}

export default function HistoricoTransacoes({ documentoProfessor }: HistoricoTransacoesProps) {
    const [transacoes, setTransacoes] = useState<Transacao[]>([]);

    useEffect(() => {
        const fetchTransacoes = async () => {
            try {
                console.log('Buscando transações para o professor:', documentoProfessor);
                const response = await axios.get(`http://localhost:8080/professor/${documentoProfessor}/transacoes`);
                console.log('Resposta da API:', response.data);
                setTransacoes(response.data);
            } catch (error) {
                console.error('Erro ao carregar transações:', error);
            }
        };

        if (documentoProfessor) {
            fetchTransacoes();
        }
    }, [documentoProfessor]);

    return (
        <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-semibold mb-4">Histórico de Transações</h2>
            <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Data</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Valor</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Destino</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Mensagem</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {transacoes.map((transacao) => (
                            <tr key={transacao.id}>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {new Date(transacao.data).toLocaleDateString()}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {transacao.quantidadeMoedas}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {transacao.nomeDestino}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                                    {transacao.mensagem}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
} 