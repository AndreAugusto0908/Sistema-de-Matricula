import { z } from "zod";

export const alunoSchema = z.object({
  nome: z.string().min(3, "Nome é obrigatório"),
  documento: z.string().min(3, "Documento é obrigatório"),
  curso: z.string().min(2, "Curso é obrigatório"),
  email: z.string().email("Email inválido"),
  endereco: z.string().min(5, "Endereço é obrigatório"),
  rg: z.string().min(5, "RG é obrigatório"),
  senha: z.string().min(6, "Senha deve ter no mínimo 6 caracteres"),
});

export type AlunoFormData = z.infer<typeof alunoSchema>;
