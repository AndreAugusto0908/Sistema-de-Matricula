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
    <div className="w-full flex flex-col justify-center gap-4 vantagem_form">
        {transacoes.map((transacao, index) => {
            return (
                <div key={index} className="p-4 border-b last:border-b-0 ">
                    <h3 className="text-lg font-semibold">Valor de moedas: {transacao.quantidadeMoeadas}</h3>
                    <p className="text-gray-600">Origem: {transacao.origem.id}</p>
                </div>
            );
        })}
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

export default ExtratoModal;