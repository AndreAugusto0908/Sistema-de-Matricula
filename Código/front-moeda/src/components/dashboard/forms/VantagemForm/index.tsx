import FormModal from "../../modal/formModal/index"
import { MouseEventHandler } from "react"



type ButtonObject = {
    title: String,
    action: MouseEventHandler<HTMLButtonElement>,
    className: string
}

type InputObject = {
    tipo: string,
    title: string
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
    const formModal: FormModalProps = {
    modalTitle: "Adicionar Vantagem",
    buttons: [
        {
            title: "Salvar",
            action: () => { console.log("salvar"); closeModal() },
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
            title: "Descrição"
        },
        {
            tipo: "number",
            title: "Preço"
        },
        {
            tipo: "text",
            title: "link da imagem"
        }
    ]
}
    return (    
    <div className="w-full flex flex-col justify-center gap-4 vantagem_form">
        <FormModal buttons={formModal.buttons} inputs={formModal.inputs} modalTitle={formModal.modalTitle}/>
    </div>
    )

}



 