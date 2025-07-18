"use client";

import { Input } from "@/components/ui/input";
import DefaultLogin from "../../default-login";
import { resetarSenhaSchema, ResetarSenhaSchema } from "@/utils/validations";
import toast from "react-hot-toast";
import { api } from "@/service/api";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "@/components/ui/button";
import handleError from "@/app/ErrorHandling";

export default function NovaSenha(){

        const router = useRouter();

        const {
            register,
            handleSubmit,
            formState: { errors, isSubmitting },
        } = useForm<ResetarSenhaSchema>({
            resolver: zodResolver(resetarSenhaSchema),
        });

    async function handleResetarSEnha(data: ResetarSenhaSchema) {
        try {
            const email = sessionStorage.getItem("email");

            const response = await api.post(`/esqueceuSenha/alterarsenha/${email}`, data);
            toast.success("Senha Alterada Com Sucesso!");
            sessionStorage.removeItem("email");
            router.push("/");
            return response.data;
        } catch (error) {
            handleError(error);
            console.error(error);
        }
    }


    return(
        <DefaultLogin>
            <section className="flex flex-col gap-10 mx-auto justify-center min-h-screen items-center">
                <div className="flex flex-col gap-5 w-full items-center">
                    <h2 className="font-semibold text-4xl">Insira sua Nova Senha</h2>
                    <span className="text-gray-600">Insira sua senha e confirme ela</span>
                </div>

                <form
                    onSubmit={handleSubmit(handleResetarSEnha)}
                    className="flex flex-col gap-5 w-full max-w-md"
                >
                    <div>
                        <label htmlFor="senha">Nova Senha</label>
                        <Input id="senha" type="password" autoComplete="off" {...register("senha")} className="w-full" />
                        {errors.senha && (
                            <p className="text-red-500 text-xs mt-1">{errors.senha.message}</p>
                        )}
                    </div>
                    <div>
                        <label htmlFor="confirmarSenha">Confirmar Senha</label>
                        <Input id="confirmarSenha" type="password" autoComplete="off" {...register("confirmarSenha")} className="w-full" />
                        {errors.confirmarSenha && (
                            <p className="text-red-500 text-xs mt-1">{errors.confirmarSenha.message}</p>
                        )}
                    </div>
                    <Button
                        className="bg-[#FFD700] text-black hover:bg-[#e7db6a] hover:cursor-pointer w-full"
                        type="submit"
                    >
                        Resetar Senha
                    </Button>
                </form>
            </section>
        </DefaultLogin>
    )
}