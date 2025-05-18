import Image from "next/image";
import ImagemMoeda from "@/assets/Moeda Estudantil.jpg"
import { ReactNode } from "react";

type DefaultLoginProps = {
  children: ReactNode;
};


export default function DefaultLogin({ children }: DefaultLoginProps) {
  return (
    <main className="flex min-h-screen">
      <div className="w-1/2 h-screen">
        <Image
          src={ImagemMoeda}
          alt="Moeda Estudantil Logo"
          className="object-cover w-full h-full"
          priority
        />
      </div>

      <div className="w-1/2  h-screen">
        {children}
      </div>
    </main>
  );
}