"use client";

import { useState } from "react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { ConfirmationModal } from "../../modal/confirmationModal";
import { toast } from "sonner";
import { api } from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import { Eye, Trash2, Pencil } from "lucide-react";
import { useRouter } from "next/navigation";

interface Empresa {
  id: number;
  nome: string;
  documento: string;
}

interface EmpresasTableProps {
  data: Empresa[];
  onDelete?: (empresa: Empresa) => void;
}

export function EmpresasTable({ data, onDelete }: EmpresasTableProps) {
  const router = useRouter();
  const [modalOpen, setModalOpen] = useState(false);
  const [empresaSelecionada, setEmpresaSelecionada] = useState<Empresa | null>(null);
  
  const handleConfirmDelete = async () => {
    if (!empresaSelecionada) return;
    try {
      await api.delete(`${ENDPOINTS.EMPRESA.DELETE}?id=${empresaSelecionada.id}`);
      toast.success("Empresa removida com sucesso!");
      onDelete?.(empresaSelecionada);
    } catch (error) {
      console.error(error);
      toast.error("Erro ao remover a empresa.");
    } finally {
      setModalOpen(false);
      setEmpresaSelecionada(null);
    }
  };
  
  

  const goToViewEmpresa = (empresa: Empresa, modo: "visualizar" | "editar") => {
    const query = encodeURIComponent(JSON.stringify({ ...empresa, modo }));
    router.push(`/dashboard/secretaria/gerenciar-empresas/ViewEmpresa?empresa=${query}`);
  };

  return (
    <>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Nome</TableHead>
              <TableHead>Documento</TableHead>
              <TableHead className="text-center">Ações</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {data.length > 0 ? (
              data.map((empresa) => (
                <TableRow key={empresa.id}>
                  <TableCell className="max-w-[200px] truncate" title={empresa.nome}>
                    {empresa.nome}
                  </TableCell>
                  <TableCell className="max-w-[200px] truncate" title={empresa.documento}>
                    {empresa.documento}
                  </TableCell>
                  <TableCell className="flex gap-2 justify-center">
                    {/* Visualizar */}
                    <button
                      onClick={() => goToViewEmpresa(empresa, "visualizar")}
                      className="text-blue-600 hover:text-blue-800"
                    >
                      <Eye className="w-4 h-4" />
                    </button>

                    {/* Editar */}
                    <button
                      onClick={() => goToViewEmpresa(empresa, "editar")}
                      className="text-green-600 hover:text-green-800"
                    >
                      <Pencil className="w-4 h-4" />
                    </button>

                    {/* Deletar */}
                    <button
                      onClick={() => {
                        setEmpresaSelecionada(empresa);
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
                <TableCell colSpan={3} className="h-24 text-center">
                  Nenhuma empresa encontrada.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      {/* Modal de Confirmação */}
      {empresaSelecionada && (
        <ConfirmationModal
          open={modalOpen}
          title={
            <>
              Deseja realmente remover a empresa <br />
              <span className="font-bold">{empresaSelecionada.nome}</span>?
            </>
          }
          onConfirm={handleConfirmDelete}
          onCancel={() => {
            setModalOpen(false);
            setEmpresaSelecionada(null);
          }}
        />
      )}
    </>
  );
}
