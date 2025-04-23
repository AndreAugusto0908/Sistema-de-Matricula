"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ConfirmationModal } from "../../modal/confirmationModal";
import { api } from "@/service/api";
import { ENDPOINTS } from "@/service/endpoints";
import { toast } from "sonner";

interface Empresa {
  id: number;
  nome: string;
  documento: string;
  modo: "visualizar" | "editar";
}

interface VisualizarEmpresaProps {
  empresa: Empresa;
}

export default function VisualizarEmpresa({ empresa }: VisualizarEmpresaProps) {
  const router = useRouter();
  const [isEditing, setIsEditing] = useState(empresa.modo === "editar");
  const [formData, setFormData] = useState<Empresa>(empresa);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    if (empresa) {
      setFormData(empresa);
    }
  }, [empresa]);

  const handleInputChange = (field: keyof Empresa, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleSave = async () => {
    try {
      const empresaDTO = {
        id: formData.id,
        nome: formData.nome,
        documento: formData.documento,
      };

      await api.put(ENDPOINTS.EMPRESA.ATUALIZAR, empresaDTO);
      toast.success("Empresa atualizada com sucesso!");
      setIsEditing(false);
    } catch (error) {
      console.error(error);
      toast.error("Erro ao atualizar empresa.");
    } finally {
      setModalOpen(false);
    }
  };

  const handleBack = () => {
    router.back();
  };

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg mt-10 space-y-6">
      <div className="flex justify-between items-center border-b pb-4">
        <div>
          <h1 className="text-2xl font-bold text-[#3FAFC3]">Visualização - Empresa</h1>
          <p className="text-gray-500 text-sm">
            {isEditing ? "Editando informações." : "Campos bloqueados para visualização."}
          </p>
        </div>

        <div className="flex gap-2">
          <Button variant="outline" onClick={handleBack}>
            Voltar
          </Button>

          {!isEditing ? (
            <Button className="bg-[#3FAFC3] hover:bg-[#2e8a9c] text-white" onClick={() => setIsEditing(true)}>
              Editar
            </Button>
          ) : (
            <>
              <Button variant="outline" onClick={() => setIsEditing(false)}>
                Cancelar
              </Button>
              <Button className="bg-[#3FAFC3] hover:bg-[#2e8a9c] text-white" onClick={() => setModalOpen(true)}>
                Salvar Alterações
              </Button>
            </>
          )}
        </div>
      </div>

      {/* Campos */}
      <section className="space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Field
            label="Nome da Empresa"
            value={formData.nome}
            editable={isEditing}
            onChange={(v) => handleInputChange("nome", v)}
          />
          <Field
            label="Documento (CNPJ)"
            value={formData.documento}
            editable={isEditing}
            onChange={(v) => handleInputChange("documento", v)}
          />
        </div>
      </section>

      {/* Modal de confirmação */}
      <ConfirmationModal
        open={modalOpen}
        title="Deseja realmente salvar as alterações?"
        onConfirm={handleSave}
        onCancel={() => setModalOpen(false)}
      />
    </div>
  );
}

function Field({
  label,
  value,
  editable,
  onChange,
}: {
  label: string;
  value: string;
  editable: boolean;
  onChange?: (value: string) => void;
}) {
  return (
    <div className="flex flex-col">
      <label className="text-sm font-medium text-gray-600 mb-1">{label}</label>
      <Input
        value={value}
        readOnly={!editable}
        onChange={editable && onChange ? (e) => onChange(e.target.value) : undefined}
        className={editable ? "" : "bg-gray-100 cursor-not-allowed"}
      />
    </div>
  );
}
