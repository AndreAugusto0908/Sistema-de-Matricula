"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ConfirmationModal } from "../../modal/confirmationModal";
import { api } from "@/service/api";
import { ENDPOINTS } from "@/service/endpoints";
import { toast } from "sonner";
import handleError from "@/app/ErrorHandling";

interface Aluno {
  id: number;
  tipoConta: string;
  documento: string;
  nome: string;
  instituicao: string;
  curso: string;
  senha: string;
  rg: string;
  endereco: string;
  email: string;
  modo: "visualizar" | "editar";
}

interface VisualizarAlunoProps {
  aluno: Aluno;
}

export default function VisualizarAluno({ aluno }: VisualizarAlunoProps) {
  const router = useRouter();
  const [isEditing, setIsEditing] = useState(aluno.modo === "editar");
  const [formData, setFormData] = useState<Aluno>(aluno);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    if (aluno) {
      setFormData(aluno);
    }
  }, [aluno]);

  const handleInputChange = (field: keyof Aluno, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleSave = async () => {
    try {
      const alunoDTO = {
        id: formData.id,
        nome: formData.nome,
        documento: formData.documento,
        curso: formData.curso,
        senha: formData.senha,
        endereco: formData.endereco,
        email: formData.email,
        rg: formData.rg,
      };
  
      await api.put(ENDPOINTS.ALUNO.ATUALIZAR, alunoDTO);
      toast.success("Aluno atualizado com sucesso!");
      setIsEditing(false);
    } catch (error) {
      console.error(error);
      handleError(error);
    } finally {
      setModalOpen(false);
    }
  };
  

  const handleBack = () => {
    router.back();
  };

  return (
    <div className="max-w-6xl mx-auto p-6 bg-white shadow-lg rounded-lg mt-10 space-y-6">
      <div className="flex justify-between items-center border-b pb-4">
        <div>
          <h1 className="text-2xl font-bold text-[#3FAFC3]">Visualização - Aluno</h1>
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
          <Field label="Nome" value={formData.nome} editable={isEditing} onChange={(v) => handleInputChange("nome", v)} />
          <Field label="Tipo de Conta" value={formData.tipoConta} editable={false} />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Field label="Documento" value={formData.documento} editable={isEditing} onChange={(v) => handleInputChange("documento", v)} />
          <Field label="Instituição" value={formData.instituicao} editable={isEditing} onChange={(v) => handleInputChange("instituicao", v)} />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Field label="Curso" value={formData.curso} editable={isEditing} onChange={(v) => handleInputChange("curso", v)} />
          <Field label="RG" value={formData.rg} editable={isEditing} onChange={(v) => handleInputChange("rg", v)} />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Field label="Endereço" value={formData.endereco} editable={isEditing} onChange={(v) => handleInputChange("endereco", v)} />
          <Field label="Email" value={formData.email} editable={isEditing} onChange={(v) => handleInputChange("email", v)} />
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Field label="Senha" value={formData.senha} editable={isEditing} onChange={(v) => handleInputChange("senha", v)} />
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

function Field({ label, value, editable, onChange }: { label: string; value: string; editable: boolean; onChange?: (value: string) => void }) {
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
