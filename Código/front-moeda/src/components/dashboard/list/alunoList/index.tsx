"use client";

import { useState } from "react";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { ConfirmationModal } from "../../modal/confirmationModal";
import { toast } from "sonner";
import { api } from "@/service/api";
import ENDPOINTS from "@/service/endpoints";
import { Eye, Trash2, Pencil } from "lucide-react"; // Adicionado Pencil (ícone lápis)
import { useRouter } from "next/navigation";

interface Aluno {
  id: number;
  nome: string;
  curso: string;
  documento: string;
}

interface AlunosTableProps {
  data: Aluno[];
  onView?: (aluno: Aluno) => void;
  onDelete?: (aluno: Aluno) => void;
}

export function AlunosTable({ data, onView, onDelete }: AlunosTableProps) {
  const router = useRouter();
  const [modalOpen, setModalOpen] = useState(false);
  const [alunoSelecionado, setAlunoSelecionado] = useState<Aluno | null>(null);

  const handleConfirmDelete = async () => {
    if (!alunoSelecionado) return;
    try {
      const alunoDTO = {
        id: alunoSelecionado.id
      };
  
      await api.delete(ENDPOINTS.ALUNO.DELETE, { data: alunoDTO }); 
      // <- Aqui importante passar { data: body } no axios.delete!
  
      toast.success("Aluno removido com sucesso!");
      onDelete?.(alunoSelecionado);
    } catch (error) {
      console.error(error);
      handl
    } finally {
      setModalOpen(false);
      setAlunoSelecionado(null);
    }
  };

  const goToViewAluno = (aluno: Aluno, modo: "visualizar" | "editar") => {
    const query = encodeURIComponent(JSON.stringify({ ...aluno, modo }));
    router.push(`/dashboard/secretaria/gerenciar-aluno/viewAluno?aluno=${query}`);
  };

  return (
    <>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Nome</TableHead>
              <TableHead>Curso</TableHead>
              <TableHead>Documento</TableHead>
              <TableHead className="text-center">Ações</TableHead>
            </TableRow>
          </TableHeader>

          <TableBody>
            {data.length > 0 ? (
              data.map((aluno) => (
                <TableRow key={aluno.id}>
                  <TableCell className="max-w-[150px] truncate" title={aluno.nome}>
                    {aluno.nome}
                  </TableCell>
                  <TableCell className="max-w-[150px] truncate" title={aluno.curso}>
                    {aluno.curso}
                  </TableCell>
                  <TableCell className="max-w-[150px] truncate" title={aluno.documento}>
                    {aluno.documento}
                  </TableCell>

                  <TableCell className="flex gap-2 justify-center">
                    {/* Visualizar */}
                    <button
                      onClick={() => goToViewAluno(aluno, "visualizar")}
                      className="text-blue-600 hover:text-blue-800"
                    >
                      <Eye className="w-4 h-4" />
                    </button>

                    {/* Editar */}
                    <button
                      onClick={() => goToViewAluno(aluno, "editar")}
                      className="text-green-600 hover:text-green-800"
                    >
                      <Pencil className="w-4 h-4" />
                    </button>

                    {/* Deletar */}
                    <button
                      onClick={() => {
                        setAlunoSelecionado(aluno);
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
                  Nenhum aluno encontrado.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      {/* Modal de Confirmação */}
      {alunoSelecionado && (
        <ConfirmationModal
          open={modalOpen}
          title={
            <>
              Deseja realmente remover o aluno <br />
              <span className="font-bold">{alunoSelecionado.nome}</span>?
            </>
          }
          onConfirm={handleConfirmDelete}
          onCancel={() => {
            setModalOpen(false);
            setAlunoSelecionado(null);
          }}
        />
      )}
    </>
  );
}
