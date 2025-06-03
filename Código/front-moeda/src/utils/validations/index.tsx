import { z } from "zod";

export const createAlunoSchema = z.object({
  nome: z.string().min(3, "Nome é obrigatório"),
  documento: z.string()
    .min(3, 'Documento é obrigatório')
    .transform((val) => val.replace(/[^\d]/g, '')) // remove pontuação
    .refine((cpf) => {
      if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) return false;

      let sum = 0;
      for (let i = 0; i < 9; i++) sum += +cpf[i] * (10 - i);
      let firstDigit = (sum * 10) % 11;
      if (firstDigit === 10) firstDigit = 0;
      if (firstDigit !== +cpf[9]) return false;

      sum = 0;
      for (let i = 0; i < 10; i++) sum += +cpf[i] * (11 - i);
      let secondDigit = (sum * 10) % 11;
      if (secondDigit === 10) secondDigit = 0;

      return secondDigit === +cpf[10];
    }, {
      message: 'CPF inválido',
    }),
  curso: z.string().min(2, "Curso é obrigatório"),
  email: z.string().email("Email inválido"),
  endereco: z.string().min(5, "Endereço é obrigatório"),
  rg: z  .string()
  .transform((val) => val.replace(/[^a-zA-Z0-9]/g, '').toUpperCase())
  .refine((val) => /^[A-Z]{2}\d{8}$/.test(val), {
    message: 'RG deve conter UF seguida de 8 números (ex: MG18417318)',
  }),
  senha: z
    .string()
    .min(6, "A senha deve ter pelo menos 6 caracteres")
    .regex(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).+$/,
      "A senha deve conter letra maiúscula, minúscula, número e caractere especial"
    ),
});

export type createAlunoSchema = z.infer<typeof createAlunoSchema>;

export const loginUserSchema = z.object({
  documento: z
    .string()
    .refine(
      (val) => /^\d{11}$/.test(val) || /^\d{14}$/.test(val),
      {
        message: "Informe um CPF (11 dígitos) ou CNPJ (14 dígitos) válido",
      }
    ),
  senha: z
    .string()
    .min(6, "A senha deve ter pelo menos 6 caracteres")
    .regex(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).+$/,
      "A senha deve conter letra maiúscula, minúscula, número e caractere especial"
    ),
});

export type loginUserSchema = z.infer<typeof loginUserSchema>;

export const empresaSchema = z.object({
  nome: z.string().min(1, "O nome é obrigatório"),
  documento: z.string().min(11, "O documento é obrigatório"),
});

export type EmpresaFormData = z.infer<typeof empresaSchema>;

export const enviarEmailSchema = z.object({
  email: z.string().email({ message: "E-mail inválido" }),
});

export type EnviarEmailSchema = z.infer<typeof enviarEmailSchema>;

export const resetarSenhaSchema = z
  .object({
    senha: z
      .string()
      .min(6, "A senha deve ter pelo menos 6 caracteres")
      .regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).+$/,
        "A senha deve conter letra maiúscula, minúscula, número e caractere especial"
      ),
    confirmarSenha: z.string(),
  })
  .refine((data) => data.senha === data.confirmarSenha, {
    message: "As senhas não coincidem",
    path: ["confirmarSenha"],
  });

export type ResetarSenhaSchema = z.infer<typeof resetarSenhaSchema>