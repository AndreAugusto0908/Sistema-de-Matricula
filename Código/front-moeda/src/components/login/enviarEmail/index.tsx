"use client";

import { Button } from "@/components/ui/button";
import DefaultLogin from "../default-login";
import { Input } from "@/components/ui/input";
import { toast } from "react-hot-toast";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { api } from "@/service/api";
import { enviarEmailSchema, EnviarEmailSchema } from "@/utils/validations";

export default function EnviarEmail() {

    const router = useRouter();

    const { register, handleSubmit } = useForm<EnviarEmailSchema>({
        resolver: zodResolver(enviarEmailSchema),
    })

    async function handleEnviarEmail(data: EnviarEmailSchema) {
        try {
            const response = await api.post(`/esqueceuSenha/enviarEmail/${data.email}`);
            toast.success("Email enviado com Sucesso!");
            router.push("/informar-codigo");
            return response.data;
        } catch (error) {
            toast.error("Erro ao enviar e-mail. Tente novamente.");
            console.error(error);
        }
    }


    return (
        <DefaultLogin>
            <section className="flex flex-col gap-10 mx-auto justify-center min-h-screen items-center">
                <div className="flex flex-col gap-5 w-full items-center">
                    <h2 className="font-semibold text-4xl">Insira seu Email</h2>
                    <span className="text-gray-600">Insira seu Email para recuperar sua senha</span>
                </div>

                <form
                    onSubmit={handleSubmit(handleEnviarEmail)}
                    className="flex flex-col gap-5 w-full max-w-md"
                >
                    <div>
                        <label htmlFor="email">Email</label>
                        <Input id="email" type="email" autoComplete="off" {...register("email")} className="w-full" />
                    </div>
                    <Button
                        className="bg-[#FFD700] text-black hover:bg-[#e7db6a] hover:cursor-pointer"
                        type="submit"
                    >
                        Enviar Email
                    </Button>
                </form>

            </section>
        </DefaultLogin>
    )
}