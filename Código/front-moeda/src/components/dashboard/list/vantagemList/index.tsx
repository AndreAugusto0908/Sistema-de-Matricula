"use client";

import { useState } from "react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { ConfirmationModal } from "../../modal/confirmationModal";
import { toast } from "sonner";
import api from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import { Eye, Trash2, Pencil } from "lucide-react"; // Adicionado Pencil (ícone lápis)
import { useRouter } from "next/navigation";
import handleError from "@/app/ErrorHandling";

interface Vantagem {
  id: number;
  valorMoedas: number;
  descricao: string;
  foto: string;
}

interface VantagemsTableProps {
  data: Vantagem[];
  onView?: (vantagem: Vantagem) => void;
  onDelete?: (vantagem: Vantagem) => void;
}

export function VantagemsTable({ data, onView, onDelete }: VantagemsTableProps) {
  const router = useRouter();
  const [modalOpen, setModalOpen] = useState(false);
  const [vantagemSelecionado, setVantagemSelecionado] = useState<Vantagem | null>(null);

  const handleConfirmDelete = async () => {
    if (!vantagemSelecionado) return;
    try {
      const vantagemDTO = {
        id: vantagemSelecionado.id
      };
  
      await api.delete(ENDPOINTS.ALUNO.DELETE, { data: vantagemDTO }); 
      // <- Aqui importante passar { data: body } no axios.delete!
  
      toast.success("Vantagem removido com sucesso!");
      onDelete?.(vantagemSelecionado);
    } catch (error) {
      console.error(error);
      handleError(error);
    } finally {
      setModalOpen(false);
      setVantagemSelecionado(null);
    }
  };

  const goToViewVantagem = (vantagem: Vantagem, modo: "visualizar" | "editar") => {
    const query = encodeURIComponent(JSON.stringify({ ...vantagem, modo }));
    router.push(`/dashboard/secretaria/gerenciar-vantagem/viewVantagem?vantagem=${query}`);
  };

  return (
    <>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Descrição</TableHead>
              <TableHead>Custo</TableHead>
              <TableHead className="text-center">Ações</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {data.length > 0 ? (
              data.map((vantagem) => (
                <TableRow key={vantagem.id}>
                  <TableCell className="max-w-[150px] truncate" title="Descrição">
                    {vantagem.descricao}
                  </TableCell>
                  <TableCell className="max-w-[150px] truncate" title="Valor">
                    {vantagem.valorMoedas}
                  </TableCell>


                  <TableCell className="flex gap-2 justify-center">
                    {/* Visualizar */}
                    <button
                      onClick={() => goToViewVantagem(vantagem, "visualizar")}
                      className="text-blue-600 hover:text-blue-800"
                    >
                      <Eye className="w-4 h-4" />
                    </button>

                    {/* Editar */}
                    <button
                      onClick={() => goToViewVantagem(vantagem, "editar")}
                      className="text-green-600 hover:text-green-800"
                    >
                      <Pencil className="w-4 h-4" />
                    </button>

                    {/* Deletar */}
                    <button
                      onClick={() => {
                        setVantagemSelecionado(vantagem);
                        setModalOpen(true);
                      }}
                      className="text-red-500 hover:text-red-700"
                    >
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={4} className="h-24 text-center">
                  Nenhum vantagem encontrada.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      {/* Modal de Confirmação */}
      {vantagemSelecionado && (
        <ConfirmationModal
          open={modalOpen}
          title={
            <>
              Deseja realmente remover a vantagem <br />
              <span className="font-bold">{vantagemSelecionado.descricao}</span>?
            </>
          }
          onConfirm={handleConfirmDelete}
          onCancel={() => {
            setModalOpen(false);
            setVantagemSelecionado(null);
          }}
        />
      )}
    </>
  );
}
