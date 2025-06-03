"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import DefaultLogin from "../default-login";
import { useForm } from "react-hook-form";
import { loginUserSchema } from "@/utils/validations";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useContext } from "react";
import { AuthContext } from "@/contexts/AuthContext";
import { useRouter } from "next/navigation";

export default function LoginPage() {
  const { signIn } = useContext(AuthContext);
  const router = useRouter();
  

  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<loginUserSchema>({
    resolver: zodResolver(loginUserSchema),
  });

  async function handleLoginUser(data: loginUserSchema) {
    console.log(data)
    await signIn(data);
  }

  return (
    <DefaultLogin>
        <section className="flex flex-col gap-10 mx-auto justify-center min-h-screen items-center">
          <div className="flex flex-col gap-5 w-full items-center">
            <h2 className="font-semibold text-4xl">Realize seu Login</h2>
            <span className="text-gray-600">Entre com seu CPF e Senha</span>
          </div>
          
          <form
            onSubmit={handleSubmit(handleLoginUser)}
            className="flex flex-col gap-5 w-full max-w-md"
          >
            <div>
              <label htmlFor="login">Login</label>
              <Input id="login" {...register("documento")} className="w-full" />
              {errors.documento && (
              <p className="text-red-500 text-xs mt-1">{errors.documento.message}</p>
            )}
            </div>
            <div>
              <label htmlFor="senha">Senha</label>
              <Input id="senha" type="password" {...register("senha")} className="w-full" />

              {errors.senha && (
              <p className="text-red-500 text-xs mt-1">{errors.senha.message}</p>
              )}
            </div>
            <div>
              <span className="text-gray-400 hover:cursor-pointer"
              onClick={() => router.push("/recuperarSenha/informarEmail")}
              >Esqueceu a senha?</span>
            </div>
            <Button
              className="bg-[#FFD700] text-black hover:bg-[#e7db6a] hover:cursor-pointer"
              type="submit"
            >
              Realizar Login
            </Button>
          </form>
        </section>
    </DefaultLogin>

  )
}