"use client";

import { useForm, FormProvider, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useState } from "react";
import { EmpresaFormData, empresaSchema } from "@/utils/validations";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Building2 } from "lucide-react";
import { toast } from "sonner";
import { api } from "@/service/api";
import { ENDPOINTS } from "@/service/endpoints";
import { IMaskInput } from "react-imask";
import handleError from "@/app/ErrorHandling";

interface EmpresaFormProps {
  closeModal: () => void;
}

export function EmpresaForm({ closeModal }: EmpresaFormProps) {
  const methods = useForm<EmpresaFormData>({
    resolver: zodResolver(empresaSchema),
  });

  const { control, register, handleSubmit, reset } = methods;
  const [loading, setLoading] = useState(false);

  const onSubmit = async (data: EmpresaFormData) => {
    try {
      setLoading(true);
      await api.post(ENDPOINTS.EMPRESA.POST, data);
      toast.success("Empresa cadastrada com sucesso!");
      closeModal();
      reset();
    } catch (error) {
      console.error("Erro ao cadastrar empresa:", error);
      handleError(error);
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
          <Building2 />
          <h2 className="text-xl font-semibold text-center text-[#161616]">Cadastrar Empresa</h2>
        </div>

        {/* Nome */}
        <Input
          {...register("nome")}
          placeholder="Nome da Empresa"
          className="bg-[#F5F5F5] text-black"
        />

        {/* Documento (CNPJ ou CPF) */}
        <Controller
          name="documento"
          control={control}
          render={({ field }) => (
            <IMaskInput
              {...field}
              mask="00.000.000/0000-00" // ou CPF: "000.000.000-00"
              placeholder="Documento (CNPJ)"
              className="bg-[#F5F5F5] text-black p-2 rounded-md w-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-yellow-400"
            />
          )}
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
