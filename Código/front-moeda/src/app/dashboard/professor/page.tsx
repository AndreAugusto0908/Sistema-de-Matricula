'use client';

import DashboardLayout from "@/components/dashboard/layout";
import { ConfiguracaoProfessor, menuPrincipalProfessor } from "../../../utils/constants";
import { LayoutDashboard, Coins } from "lucide-react";
import HistoricoTransacoes from '../../dashboard/components/HistoricoTransacoes';
import { useAuth } from "@/contexts/AuthContext";
import { useState, useEffect, useContext } from 'react';
import { api } from "@/service/api";
import { AuthContext } from "@/contexts/AuthContext";
import { parseCookies } from "nookies";
import { Combobox } from '@headlessui/react';
import toast from 'react-hot-toast';
import axios from "axios";

interface Alunos{
    data: Aluno[];
}

interface Aluno {
    id: number;
    nome: string;
    documento: string;
    conta: {
        saldo: number;
    };
}

export default function DashboardProfessor() {
    const { user, } = useContext(AuthContext);
    const [saldoProfessor, setSaldoProfessor] = useState<number>(500);
    const [documentoAluno, setDocumentoAluno] = useState('');
    const [valor, setValor] = useState('');
    const [observacao, setObservacao] = useState('');
    const [alunos, setAlunos] = useState<Aluno[]>([]);
    const [aluno, setAluno] = useState<Aluno | null>(null);
    const [query, setQuery] = useState('');
    const [selectedAluno, setSelectedAluno] = useState<Aluno | null>(null);
    const [decodedToken, setDecodedToken] = useState<any | null>(null);
   


   function useUserFromToken() {
        const cookies = parseCookies();
        const token = cookies['nextauth.token'];
        
        if (!token) return null;
        
        try {
            const decodedToken = JSON.parse(atob(token.split('.')[1]));
            return {
                nome: decodedToken.nome,
                documento: decodedToken.documento,
                role: decodedToken.role,
                id: decodedToken.id
            };
        } catch (error) {
            console.error('Erro ao decodificar token:', error);
            return null;
        }
    }

    const fetchSaldoProfessor = async () => {

        if (!user?.documento) return;
        
        try {
            const token = parseCookies()['nextauth.token'];
            const response = await api.get(`/professor/${user.documento}/saldo`);
            setSaldoProfessor(response.data);
        } catch (error) {
            console.error('Erro ao carregar saldo do professor:', error);
            toast.error('Erro ao carregar saldo');
        }
    };

    const fetchAlunos = async () => {
        try {
            const response = await api.get<Alunos>('/aluno')      
            
            if (Array.isArray(response.data)) {
                const alunosFormatados = response.data.map((aluno: any) => ({
                    id: aluno.id,
                    nome: aluno.nome,
                    documento: aluno.documento,
                    conta: {
                        saldo: aluno.conta?.saldo || 0
                    }
                }));
                setAlunos(alunosFormatados);
            }
        } catch (error: any) {
            console.error('Erro detalhado ao carregar alunos:', error);
            toast.error('Erro ao carregar lista de alunos');
        }
    };


    useEffect(() => {

             useUserFromToken();
            fetchSaldoProfessor();
            fetchAlunos();  

    }, [user?.documento],);

    console.log('Estado atual dos alunos:', alunos);

    const filteredAlunos = query === ''
        ? alunos
        : alunos.filter((aluno) =>
            aluno.nome.toLowerCase().includes(query.toLowerCase())
        );

    console.log('Alunos filtrados:', filteredAlunos);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!selectedAluno) {
            toast.error('Selecione um aluno');
            return;
        }
        const valorNum = parseInt(valor, 10);
        if (isNaN(valorNum) || valorNum < 1) {
            toast.error('O valor deve ser um número maior ou igual a 1');
            return;
        }
        try {
            await api.post(`/professor/${user?.documento}/enviar-moedas`, {
                documentoRecebedor: selectedAluno?.documento,
                valor: valorNum,
                observacao: observacao
            });


            toast.success('Moedas enviadas com sucesso!');
            setDocumentoAluno('');
            setValor('');
            setObservacao('');
            setSelectedAluno(null);
            setQuery('');
            
            // Atualizar o saldo do professor após o envio
            const response = await api.get(`/professor/${user?.documento}/saldo`);
            setSaldoProfessor(response.data);
        } catch (error) {
            console.error('Erro ao enviar moedas:', error);
            toast.error('Erro ao enviar moedas');
        }
    };

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
                    <div className="flex items-center gap-2 mb-4">
                        <div className="bg-yellow-100 p-4 rounded-lg flex items-center gap-2">
                            <Coins className="h-6 w-6 text-yellow-600" />
                            <div>
                                <p className="text-sm text-gray-600">Seu saldo atual</p>
                                <p className="text-xl font-bold text-yellow-600">{saldoProfessor} moedas</p>
                            </div>
                        </div>
                    </div>
                    <p className="text-gray-600">Gerencie suas moedas e visualize o histórico de transações.</p>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
                    <div>
                        <div className="bg-white p-6 rounded-lg shadow-md">
                            <h2 className="text-xl font-semibold mb-4">Enviar Moedas</h2>
                            <form onSubmit={handleSubmit} className="space-y-4">
                                <div>
                                    <label className="block text-sm font-medium text-gray-700">Selecione o Aluno</label>
                                    <Combobox value={selectedAluno} onChange={setSelectedAluno}>
                                        <div className="relative mt-1">
                                            <Combobox.Input
                                                className="w-full rounded-md border border-gray-300 bg-white py-2 pl-3 pr-10 shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500 sm:text-sm"
                                                onChange={(event) => setQuery(event.target.value)}
                                                displayValue={(aluno: Aluno) => aluno?.nome || ''}
                                                placeholder="Digite o nome do aluno"
                                            />
                                            <Combobox.Button className="absolute inset-y-0 right-0 flex items-center pr-2">
                                                <svg className="h-5 w-5 text-gray-400" viewBox="0 0 20 20" fill="currentColor">
                                                    <path fillRule="evenodd" d="M10 3a1 1 0 01.707.293l3 3a1 1 0 01-1.414 1.414L10 5.414 7.707 7.707a1 1 0 01-1.414-1.414l3-3A1 1 0 0110 3zm-3.707 9.293a1 1 0 011.414 0L10 14.586l2.293-2.293a1 1 0 011.414 1.414l-3 3a1 1 0 01-1.414 0l-3-3a1 1 0 010-1.414z" clipRule="evenodd" />
                                                </svg>
                                            </Combobox.Button>
                                            <Combobox.Options className="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                                                {filteredAlunos.map((aluno) => (
                                                    <Combobox.Option
                                                        key={aluno.id}
                                                        value={aluno}
                                                        className={({ active }) =>
                                                            `relative cursor-default select-none py-2 pl-3 pr-9 ${
                                                                active ? 'bg-indigo-600 text-white' : 'text-gray-900'
                                                            }`
                                                        }
                                                    >
                                                        <span>{aluno.nome}</span>
                                                    </Combobox.Option>
                                                ))}
                                            </Combobox.Options>
                                        </div>
                                    </Combobox>
                                </div>

                                {selectedAluno && (
                                    <div className="flex items-center justify-between p-3 bg-gray-50 rounded-md">
                                        <span className="text-sm font-medium text-gray-700">Saldo atual do aluno:</span>
                                        <span className="flex items-center text-sm font-semibold text-indigo-600">
                                            <Coins className="h-4 w-4 mr-1" />
                                            {selectedAluno.conta?.saldo || 0}
                                        </span>
                                    </div>
                                )}

                                <div>
                                    <label className="block text-sm font-medium text-gray-700">Valor</label>
                                    <input
                                        type="text"
                                        inputMode="numeric"
                                        pattern="[0-9]*"
                                        value={valor}
                                        onChange={(e) => {
                                            const v = e.target.value;
                                            if (/^\d*$/.test(v)) {
                                                setValor(v);
                                            }
                                        }}
                                        onKeyDown={(e) => {
                                            if (e.key === 'e' || e.key === 'E') {
                                                e.preventDefault();
                                            }
                                        }}
                                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                                        required
                                        min={1}
                                        placeholder="Digite o valor"
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium text-gray-700">Observação</label>
                                    <textarea
                                        value={observacao}
                                        onChange={(e) => setObservacao(e.target.value)}
                                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                                        rows={3}
                                    />
                                </div>
                                <button
                                    type="submit"
                                    className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                                >
                                    Enviar Moedas
                                </button>
                            </form>
                        </div>
                    </div>
                    
                    <div>
                        <HistoricoTransacoes documentoProfessor={user?.documento || ''} />
                    </div>
                </div>
            </div>
        </DashboardLayout>
    );
} 