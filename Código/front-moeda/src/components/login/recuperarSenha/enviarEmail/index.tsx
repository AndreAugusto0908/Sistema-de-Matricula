"use client";

import { Button } from "@/components/ui/button";
import DefaultLogin from "../../default-login";
import { Input } from "@/components/ui/input";
import { toast } from "react-hot-toast";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { api } from "@/service/api";
import { enviarEmailSchema, EnviarEmailSchema } from "@/utils/validations";

export default function EnviarEmail() {

    const router = useRouter();

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting },
    } = useForm<EnviarEmailSchema>({
        resolver: zodResolver(enviarEmailSchema),
    });

    console.log("Erros de validação:", errors);

    async function handleEnviarEmail(data: EnviarEmailSchema) {
        try {
            console.log("Erros de validação:", errors);
            const response = await api.post(`/esqueceuSenha/enviarEmail/${data.email}`);
            toast.success("Email enviado com Sucesso!");
            router.push("/recuperarSenha/informarCodigo");
            sessionStorage.setItem("email", data.email);
            return response.data;
            } catch (error: any) {
                const errorMessage = error?.response?.data?.message || "Erro ao enviar e-mail. Tente novamente.";
                toast.error(errorMessage);
                console.error("Erro ao enviar email:", error);
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
                        {errors.email && (
                            <p className="text-red-500 text-xs mt-1">{errors.email.message}</p>
                        )}
                    </div>
                    <Button
                        className="bg-[#FFD700] text-black hover:bg-[#e7db6a] hover:cursor-pointer w-full"
                        type="submit"
                    >
                        Enviar Email
                    </Button>
                </form>

            </section>
        </DefaultLogin>
    )
}