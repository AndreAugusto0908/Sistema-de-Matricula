"use client";

import { createAlunoSchema } from "@/utils/validations";
import DefaultLogin from "../default-login";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { cpfMask, rgMask } from "@/utils/masks";
import { api } from "@/service/api";
import { toast } from "react-hot-toast";
import { useRouter } from "next/navigation";

export default function RegisterPage(){

    const router = useRouter();

        const { register, handleSubmit, formState: { errors } } = useForm<createAlunoSchema>({
          resolver: zodResolver(createAlunoSchema),
        });

        async function handleRegisterUser(data: createAlunoSchema) {
        try {
            const cleanData = {
              ...data,
              documento: data.documento.replace(/\D/g, ''),
              rg: data.rg.replace(/[^a-zA-Z0-9]/g, '').toUpperCase(),
            };


            const response = await api.post("/aluno/registrar", cleanData);
            toast.success("Aluno registrado com sucesso!");
            router.push("/")
            return response.data;
        } catch (error) {
            toast.error("Erro ao criar aluno. Tente novamente.");
            console.error(error);
        }
        }
    
    return (
    <DefaultLogin>
        <section className="flex flex-col gap-10 mx-auto justify-center min-h-screen items-center">
          <div className="flex flex-col gap-5 w-full items-center">
            <h2 className="font-semibold text-4xl">Realize seu Cadastro</h2>
            <span className="text-gray-600">Realize seu cadastro no Moeda-Estudantil</span>
          </div>
          
          <form
            onSubmit={handleSubmit(handleRegisterUser)}
            className="flex flex-col gap-5 w-full max-w-md"
          >
            <div>
              <label htmlFor="nome">Nome</label>
              <Input id="nome" {...register("nome")} className="w-full" autoComplete="off" />
              {errors.nome && <span className="text-red-500 text-sm">{errors.nome.message}</span>}
            </div>
            <div>
              <label htmlFor="documento">CPF</label>
                <Input
                    id="documento"
                    type="text"
                    className="w-full"
                    autoComplete="off"
                    {...register("documento")}
                    onChange={(e) => {
                    const masked = cpfMask(e.target.value);
                    e.target.value = masked;
                    return register("documento").onChange(e);
                    }}
                />
                {errors.documento && <span className="text-red-500 text-sm">{errors.documento.message}</span>}
            </div>
            <div>
              <label htmlFor="rg">RG</label>
                <Input
                    id="rg"
                    type="text"
                    className="w-full"
                    autoComplete="off"
                    {...register("rg")}
                    onChange={(e) => {
                    const masked = rgMask(e.target.value);
                    e.target.value = masked;
                    return register("rg").onChange(e);
                    }}
                />
                {errors.rg && <span className="text-red-500 text-sm">{errors.rg.message}</span>}
            </div>
            <div>
              <label htmlFor="curso">Curso</label>
              <Input id="curso" type="text" autoComplete="off" {...register("curso")} className="w-full" />
              {errors.curso && <span className="text-red-500 text-sm">{errors.curso.message}</span>}
            </div>
            <div>
              <label htmlFor="email">Email</label>
              <Input id="email" type="email" autoComplete="off" {...register("email")} className="w-full" />
              {errors.email && <span className="text-red-500 text-sm">{errors.email.message}</span>}
            </div>
            <div>
              <label htmlFor="endereco">Endereço</label>
              <Input id="endereco" type="text" autoComplete="off" {...register("endereco")} className="w-full" />
              {errors.endereco && <span className="text-red-500 text-sm">{errors.endereco.message}</span>}
            </div>
            <div>
              <label htmlFor="senha">Senha</label>
              <Input id="senha" type="password" {...register("senha")} className="w-full" />
              {errors.senha && <span className="text-red-500 text-sm">{errors.senha.message}</span>}
            </div>
            <div className="flex flex-row gap-3">
              <span className="text-gray-400">Já possui conta?</span>
              <span className="hover:cursor-pointer text-gray-700"
              onClick={() => router.push("/")}>Realize Login</span>
            </div>

            <Button
              className="bg-[#FFD700] text-black hover:bg-[#e7db6a] hover:cursor-pointer"
              type="submit"
            >
              Realizar Cadastro
            </Button>
          </form>
        </section>
    </DefaultLogin>

  )
}