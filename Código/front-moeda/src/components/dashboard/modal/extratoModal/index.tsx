'use client'

import api from "@/service/api"
import FormModal from "../../modal/formModal/index"
import { MouseEventHandler, useContext, useEffect, useState } from "react"
import handleError from "@/app/ErrorHandling"
import { AuthContext } from "@/contexts/AuthContext"


type User = {
    nome: string;
    documento: string;
    role: string;
    id: string;
}


type ButtonObject = {
    title: String,
    action: MouseEventHandler<HTMLButtonElement>,
    className: string
}

type InputObject = {
    tipo: string,
    title: string,
    idInput?: string
}

interface FormModalProps {
    buttons: Array<ButtonObject>,
    inputs: Array<InputObject>,
    modalTitle?: string
}



interface VantagemFormProps {
    closeModal: () => void;
}

const ExtratoModal = ({closeModal} : VantagemFormProps) => {
    const [transacoes, setTransacoes] = useState<any[]>([]);
    useEffect(() => {
        getTransactions();

    }, []);

    const getTransactions = async () => {
        try {
            const response = await api.get('/transacao/obterExtrato');
            setTransacoes(response.data);
            console.log(response.data);
        } catch (error) {
            console.error("Erro ao obter transações:", error);
        }
    }

    

return (
  <div className="w-full max-w-md mx-auto bg-white rounded-lg shadow-lg p-6 space-y-4">
    <h2 className="text-xl font-bold text-gray-800 mb-2">Extrato de Transações</h2>

    <div className="divide-y divide-gray-200">
      {transacoes.map((transacao, index) => (
        <div key={index} className="py-3">
          <h3 className="text-base font-semibold text-gray-700">
            Valor de moedas: {transacao.quantidadeMoeadas}
          </h3>
          <p className="text-sm text-gray-500">Origem: {transacao.origem.id}</p>
        </div>
      ))}
    </div>

    <button
      type="button"
      className="w-full mt-4 bg-yellow-500 text-white font-medium py-2 px-4 rounded-md hover:bg-yellow-600 transition-all"
      onClick={closeModal}
    >
      Fechar
    </button>
  </div>
);
}

export default ExtratoModal;