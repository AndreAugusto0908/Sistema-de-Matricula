// src/app/login/page.tsx
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import ImagemMoeda from "@/assets/Moeda Estudantil.jpg"
import Image from "next/image";
export default function Login() {
  return (
    <div className="flex min-h-screen">

      <div className="hidden lg:flex w-1/2">
        <Image
          src={ImagemMoeda}
          alt="Moeda Estudantil Logo"
          className="mx-auto"
        />
      </div>

      <div className="flex flex-col justify-center w-full lg:w-1/2 px-[10%] py-[5%] bg-gray-50">
        <div className="w-full max-w-md mx-auto">
          <h2 className="text-3xl font-bold text-black mb-6">Bem-vindo de volta!</h2>
          <form className="space-y-5">
            <div className="space-y-2">
              <label htmlFor="email" className="text-sm text-black">
                Email
              </label>
              <Input
                id="email"
                type="email"
                placeholder="Digite seu email"
                className="bg-white text-black"
              />
            </div>
            <div className="space-y-2">
              <label htmlFor="password" className="text-sm text-black">
                Senha
              </label>
              <Input
                id="password"
                type="password"
                placeholder="Digite sua senha"
                className="bg-white text-black"
              />
            </div>

            <div className="flex items-center justify-between text-sm text-black">
              <label className="flex items-center gap-2">
                <input type="checkbox" className="accent-yellow-400" />
                Lembrar-me
              </label>
              <a href="#" className="hover:underline">Esqueceu a senha?</a>
            </div>

            <Button type="submit" className="w-full bg-yellow-400 text-black hover:bg-yellow-500">
              Entrar
            </Button>
          </form>

          <p className="mt-6 text-center text-sm text-black">
            Não tem uma conta?{" "}
            <a href="#" className="text-blue-700 hover:underline">Cadastre-se</a>
          </p>
        </div>
      </div>
    </div>
  );
}
