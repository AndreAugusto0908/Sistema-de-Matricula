'use client'

import api from "@/service/api"
import FormModal from "../../modal/formModal/index"
import { MouseEventHandler, useContext } from "react"
import handleError from "@/app/ErrorHandling"
import { AuthContext } from "@/contexts/AuthContext"
import toast from "react-hot-toast"
import { CheckCircle } from "lucide-react"


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
    onVantagemCriada?: () => void;
}



export const VantagemForm = ({closeModal, onVantagemCriada} : VantagemFormProps) => {
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
            
            // Mostrar toast de sucesso com ícone
            toast.custom((t) => (
                <div className={`${
                    t.visible ? 'animate-enter' : 'animate-leave'
                } max-w-md w-full bg-white shadow-lg rounded-lg pointer-events-auto flex ring-1 ring-black ring-opacity-5`}>
                    <div className="flex-1 w-0 p-4">
                        <div className="flex items-start">
                            <div className="flex-shrink-0 pt-0.5">
                                <CheckCircle className="h-10 w-10 text-green-500" />
                            </div>
                            <div className="ml-3 flex-1">
                                <p className="text-sm font-medium text-gray-900">
                                    Vantagem adicionada com sucesso!
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            ), {
                duration: 3000,
                position: 'top-center',
            });
            
            // Chamar a função de callback para atualizar a lista
            if (onVantagemCriada) {
                onVantagemCriada();
            }
            
            closeModal();
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



 