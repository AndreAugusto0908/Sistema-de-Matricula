import { api } from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import { useRouter } from "next/router";
import { useEffect, useState } from "react"
import { toast } from "sonner";
import { Button } from "@/components/ui/button";
import { ConfirmationModal } from "../../modal/confirmationModal";
import { Input } from "@/components/ui/input";



interface Vantagem {
  id: number;
  valorMoedas: number;
  descricao: string;
  foto: string;
  modo: "visualizar" | "editar";
}

interface VisualizarAlunoProps {
  vantagem: Vantagem;
}

const VisualizarVantagens = ({vantagem}:VisualizarAlunoProps) => {

  const router = useRouter();
  const [isEditing, setIsEditing] = useState(vantagem.modo === "editar");
  const [formData, setFormData] = useState<Vantagem>(vantagem);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    if (vantagem) {
      setFormData(vantagem);
    }
  }, [vantagem]);

  const handleInputChange = (field: keyof Vantagem, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleSave = async () => {
    try {
      const vantagemDTO = {
        id: formData.id,
        valorMoedas: formData.valorMoedas,
        descricao: formData.descricao,
        foto: formData.foto,
      };
  
      await api.put(ENDPOINTS.ALUNO.ATUALIZAR, vantagemDTO);
      toast.success("Vantagem atualizado com sucesso!");
      setIsEditing(false);
    } catch (error) {
      console.error(error);
      toast.error("Erro ao atualizar vantagem.");
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
          <h1 className="text-2xl font-bold text-[#3FAFC3]">Visualização - Vantagem</h1>
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
          <Field label="Nome" value={formData.descricao} editable={isEditing} onChange={(v) => handleInputChange("descricao", v)} />
          <Field label="Tipo de Conta" value={formData.valorMoedas + ""} editable={false} />
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

export default VisualizarVantagens;