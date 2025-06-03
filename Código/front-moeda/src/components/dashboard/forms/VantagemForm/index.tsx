'use client'

import { api } from "@/service/api"
import FormModal from "../../modal/formModal/index"
import { MouseEventHandler, useContext } from "react"
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



export const VantagemForm = ({closeModal} : VantagemFormProps) => {
    const { user } = useContext(AuthContext);

    const criarVantagem = async (descricao: string, preco: number, imagem: string) => {
        try {
            const response = await api.post("/vantagem/criar", {
                descricao: descricao,
                valorMoedas: preco,
                foto: imagem,
                empresa: user?.id
            });
            console.log("Vantagem criada com sucesso:", response);
        } catch (error) {
            console.error("Erro ao criar vantagem:", error);
            handleError(error);
            throw error;
        }
    }

    const formModal: FormModalProps = {
    modalTitle: "Adicionar Vantagem",
    buttons: [
        {
            title: "Salvar",
            action: (event) => { 
                event.preventDefault();
                const descricao = (document.querySelector('#input_form_modalDescrição') as HTMLInputElement).value;
                const preco = parseFloat((document.querySelector('#input_form_modalPreço') as HTMLInputElement).value);
                const imagem = (document.querySelector('#input_form_modallink') as HTMLInputElement).value;
                criarVantagem(descricao, preco, imagem)
                closeModal() 
            },
            className: "bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-2 px-4 rounded"
        },
        {
            title: "Cancelar",
            action: () => { console.log("cancelar"); closeModal() },
            className: "bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
        }
    ],
    inputs: [
        {
            tipo: "text",
            title: "Descrição",
            idInput: "input_form_modalDescrição"
        },
        {
            tipo: "number",
            title: "Preço",
            idInput: "input_form_modalPreço"
        },
        {
            tipo: "text",
            title: "link da imagem",
            idInput: "input_form_modallink"
        }
    ]
}
    return (    
    <div className="w-full flex flex-col justify-center gap-4 vantagem_form">
        <FormModal buttons={formModal.buttons} inputs={formModal.inputs} modalTitle={formModal.modalTitle}/>
    </div>
    )

}



 