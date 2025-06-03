"use client";

import { useRef, useState } from "react";
import DefaultLogin from "../../default-login";
import toast from "react-hot-toast";
import { useRouter } from "next/navigation";
import { api } from "@/service/api";
import { Button } from "@/components/ui/button";
import handleError from "@/app/ErrorHandling";

export default function InformarCodigo() {
    const [code, setCode] = useState(Array(6).fill(""));
    const inputsRef = useRef([]);

    const router = useRouter();

    async function handleVerificarCodigo() {
        const email = sessionStorage.getItem("email");
        const otp = code.join("");

        if (!email || otp.length < 6) {
            toast.error("Código ou e-mail inválido.");
            return;
        }

        try {
            await api.post(`/esqueceuSenha/verificarOtp/${otp}/${email}`);
            toast.success("Código verificado com sucesso!");
            router.push("/recuperarSenha/novaSenha");
        } catch (error) {
            handleError(error);
            console.error(error);
        }
    }

    const handleChange = (value: string, index: number) => {
        if (!/^\d?$/.test(value)) return;
        const newCode = [...code];
        newCode[index] = value;
        setCode(newCode);
        if (value && index < 5) {
            inputsRef.current[index + 1]?.focus();
        }
    };

    const handleKeyDown = (e, index) => {
        if (e.key === "Backspace" && !code[index] && index > 0) {
            inputsRef.current[index - 1]?.focus();
        }
    };
    return (
        <DefaultLogin>
            <section className="flex flex-col gap-10 mx-auto justify-center min-h-screen items-center">
                <div className="flex flex-col gap-5 w-full items-center">
                    <h2 className="font-semibold text-4xl">Insira seu Código</h2>
                    <span className="text-gray-600">Insira seu código para recuperar sua senha</span>
                </div>

                <div className="flex gap-3">
                    {code.map((digit, index) => (
                        <input
                            key={index}
                            type="text"
                            inputMode="numeric"
                            maxLength={1}
                            value={digit}
                            ref={(el) => (inputsRef.current[index] = el)}
                            onChange={(e) => handleChange(e.target.value, index)}
                            onKeyDown={(e) => handleKeyDown(e, index)}
                            className="w-12 h-12 text-center border-2 rounded text-2xl"
                        />
                    ))}
                </div>

                    <Button
                        className="bg-[#FFD700] text-black hover:bg-[#e7db6a] hover:cursor-pointer pr-20 pl-20"
                        type="submit"
                        onClick={handleVerificarCodigo}
                    >
                        Enviar Email
                    </Button>
            </section>
        </DefaultLogin>
    )
}