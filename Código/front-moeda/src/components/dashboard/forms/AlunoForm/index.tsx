"use client";

import { useState } from "react";
import { useForm, FormProvider, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { AlunoFormData, alunoSchema } from "@/utils/validations";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Pill } from "lucide-react";
import api from "@/service/api";
import { ENDPOINTS } from "@/service/endpoints";
import { toast } from "sonner";
import { IMaskInput } from "react-imask";
import handleError from "@/app/ErrorHandling";

interface AlunoFormProps {
  closeModal: () => void;
}

export function AlunoForm({ closeModal }: AlunoFormProps) {
  const methods = useForm<AlunoFormData>({
    resolver: zodResolver(alunoSchema),
  });

  const { control, register, handleSubmit, reset } = methods;

  const [loading, setLoading] = useState(false);

  const onSubmit = async (data: AlunoFormData) => {
    try {
      setLoading(true);
      await api.post(ENDPOINTS.ALUNO.POST, data);
      toast.success("Aluno cadastrado com sucesso!");
      closeModal();
      reset();
    } catch (error) {
      handleError(error);
      console.error("Erro ao cadastrar aluno:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <FormProvider {...methods}>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="flex flex-col gap-5 max-w-md mx-auto bg-white p-6 rounded-lg shadow-md"
      >
        <div className="flex flex-row gap-3 justify-center border-b pb-4">
       
          <h2 className="text-xl font-semibold text-center text-[#161616]">Cadastrar Aluno</h2>
        </div>

        {/* Nome */}
        <Input
          {...register("nome")}
          placeholder="Nome do Aluno"
          className="bg-[#F5F5F5] text-black"
        />

        {/* Documento (CPF) com máscara */}
        <Controller
          name="documento"
          control={control}
          render={({ field }) => (
            <IMaskInput
              {...field}
              placeholder="Documento (CPF)"
              className="bg-[#F5F5F5] text-black p-2 rounded-md w-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-yellow-400"
            />
          )}
        />

        {/* Curso */}
        <Input
          {...register("curso")}
          placeholder="Curso"
          className="bg-[#F5F5F5] text-black"
        />

        {/* Email */}
        <Input
          {...register("email")}
          type="email"
          placeholder="Email"
          className="bg-[#F5F5F5] text-black"
        />

        {/* Endereço */}
        <Input
          {...register("endereco")}
          placeholder="Endereço"
          className="bg-[#F5F5F5] text-black"
        />

        {/* RG com máscara */}
        <Controller
          name="rg"
          control={control}
          render={({ field }) => (
            <IMaskInput
              {...field}
              mask="00.000.000-0"
              placeholder="RG"
              className="bg-[#F5F5F5] text-black p-2 rounded-md w-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-yellow-400"
            />
          )}
        />

        {/* Senha */}
        <Input
          {...register("senha")}
          type="password"
          placeholder="Senha"
          className="bg-[#F5F5F5] text-black"
        />

        {/* Botões */}
        <div className="flex justify-between mt-6">
          <Button variant="outline" onClick={closeModal}>
            Cancelar
          </Button>

          <Button
            className="bg-[#FFD700] text-black hover:bg-[#e6c200]"
            type="submit"
            disabled={loading}
          >
            {loading ? "Enviando..." : "Confirmar"}
          </Button>
        </div>
      </form>
    </FormProvider>
  );
}
