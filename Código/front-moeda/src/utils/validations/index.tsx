import { z } from "zod";

export const createAlunoSchema = z.object({
  nome: z.string().min(3, "Nome é obrigatório"),
  documento: z.string().min(3, "Documento é obrigatório"),
  curso: z.string().min(2, "Curso é obrigatório"),
  email: z.string().email("Email inválido"),
  endereco: z.string().min(5, "Endereço é obrigatório"),
  rg: z.string().min(5, "RG é obrigatório"),
  senha: z.string().min(6, "Senha deve ter no mínimo 6 caracteres"),
});

export type createAlunoSchema = z.infer<typeof createAlunoSchema>;

export const loginUserSchema = z.object({
    documento: z
    .string()
    .min(11, "O CPF deve ter pelo menos 11 caracteres")
    .regex(/^\d{11}$/, "CPF deve conter apenas números e ter 11 dígitos"),
    senha: z.string().min(6, "Senha deve ter no mínimo 6 caracteres")
})

export type loginUserSchema = z.infer<typeof loginUserSchema>

export const empresaSchema = z.object({
  nome: z.string().min(1, "O nome é obrigatório"),
  documento: z.string().min(11, "O documento é obrigatório"),
});

export type EmpresaFormData = z.infer<typeof empresaSchema>;

export const enviarEmailSchema = z.object({
  email: z.string().email({ message: "E-mail inválido" }),
});

export type EnviarEmailSchema = z.infer<typeof enviarEmailSchema>;
