import React, { useState, useEffect } from 'react';
import axios from 'axios';
import toast from 'react-hot-toast';
import { Coins } from 'lucide-react';
import { Combobox } from '@headlessui/react';

interface Aluno {
  id: number;
  nome: string;
  documento: string;
  saldo: number;
}

interface EnviarMoedasProps {
    documentoProfessor: string;
}

export function EnviarMoedas({ documentoProfessor }: EnviarMoedasProps) {
    const [documentoAluno, setDocumentoAluno] = useState('');
    const [valor, setValor] = useState('');
    const [observacao, setObservacao] = useState('');
    const [alunos, setAlunos] = useState<Aluno[]>([]);
    const [query, setQuery] = useState('');
    const [selectedAluno, setSelectedAluno] = useState<Aluno | null>(null);

    useEffect(() => {
        const fetchAlunos = async () => {
            try {
                const response = await axios.get('/api/aluno');
                setAlunos(response.data);
            } catch (error) {
                console.error('Erro ao carregar alunos:', error);
                toast.error('Erro ao carregar lista de alunos');
            }
        };
        fetchAlunos();
    }, []);

    const filteredAlunos = query === ''
        ? alunos
        : alunos.filter((aluno) =>
            aluno.nome.toLowerCase().includes(query.toLowerCase()) ||
            aluno.documento.toLowerCase().includes(query.toLowerCase())
        );

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
            await axios.post('/api/transacao', {
                documentoOrigem: documentoProfessor,
                documentoRecebedor: selectedAluno.documento,
                valor: valorNum,
                observacao
            });
            toast.success('Moedas enviadas com sucesso!');
            setDocumentoAluno('');
            setValor('');
            setObservacao('');
            setSelectedAluno(null);
            setQuery('');
        } catch (error) {
            toast.error('Erro ao enviar moedas');
        }
    };

    return (
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
                                onFocus={() => setQuery('')}
                                displayValue={(aluno: Aluno) => aluno?.nome || ''}
                                placeholder="Digite o nome ou documento do aluno"
                            />
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
                                        <div className="flex items-center justify-between">
                                            <span>{aluno.nome}</span>
                                            <span className="flex items-center text-sm text-gray-500">
                                                <Coins className="h-4 w-4 mr-1" />
                                                {aluno.saldo}
                                            </span>
                                        </div>
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
                            {selectedAluno.saldo}
                        </span>
                    </div>
                )}

                <div>
                    <label className="block text-sm font-medium text-gray-700">Valor</label>
                    <input
                        type="number"
                        value={valor}
                        onChange={(e) => {
                            const v = e.target.value;
                            // Permite apenas números e vazio
                            if (/^\d*$/.test(v)) {
                                setValor(v);
                            }
                        }}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
                        required
                        min={1}
                        step="1"
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
    );
} 