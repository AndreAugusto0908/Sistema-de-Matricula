import { MouseEventHandler } from "react"
import "./index.css"
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

const FormModal = ({buttons, inputs, modalTitle}: FormModalProps) => {

    return (
        <div className="form_modal">
            <h1 className="text-center text-2xl font-bold mb-4">{modalTitle}</h1>
            {inputs.map((inpt, index) => {
                return (
            <div className="relative margin_login_input" key={index}>
                <input type={inpt.tipo} id={`input_form_modal${inpt.title}`}  className="block px-2.5 pb-2.5 pt-4 w-full text-sm text-gray-900 bg-transparent rounded-lg border-1 border-gray-300 appearance-none dark:text-black dark:border-gray-600 dark:focus:border-yellow-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " />
                <label htmlFor={`input_form_modal${inpt.title}`} className="absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-yellow-600 peer-focus:dark:text-yellow-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 rtl:peer-focus:translate-x-1/4 rtl:peer-focus:left-auto start-1">{ inpt.title }</label>
            </div>
                )
            })}
            {buttons.map((btn, index) => {
                return (
                    <button type="button" key={index} className={btn.className} onClick={btn.action}>{btn.title}</button>
                )
            })}
        </div>
    )

}


export default FormModal;