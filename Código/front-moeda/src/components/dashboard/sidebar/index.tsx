"use client";

import { usePathname } from "next/navigation";
import Link from "next/link";
import Image from "next/image";
import Logo from "@/assets/Moeda Estudantil.jpg";

interface SidebarItem {
  nome: string;
  url: string;
  icon: React.ElementType;
}

interface AppSidebarProps {
  menuPrincipalItems: SidebarItem[];
  configuracaoItems: SidebarItem[];
}

export function AppSidebar({ menuPrincipalItems, configuracaoItems }: AppSidebarProps) {
  const pathname = usePathname();

  return (
    <aside className="w-full px-4 mt-4 text-sm min-h-screen text-black">
      {/* Logo */}
      <Link
        href="/"
        className="flex items-center justify-center gap-2 mb-8"
      >
        <div className="w-20 h-auto lg:w-32 justify-center items-center">
          <Image
            src={Logo}
            alt="Logo Moeda Estudantil"
            width={128}
            height={128}
            className="object-contain w-full h-auto"
            priority
          />
        </div>
      </Link>

      {/* Menu Principal */}
      <div className="flex flex-col gap-2">
        <span className="hidden lg:block text-[#252107] font-light my-4 uppercase tracking-wider">
          Menu Principal
        </span>
        {menuPrincipalItems.map((item) => {
          const isActive = pathname === item.url;
          return (
            <Link
              href={item.url}
              key={item.nome}
              className={`flex items-center justify-center lg:justify-start gap-4 py-2 md:px-2 rounded-md transition-colors duration-200 ${
                isActive
                  ? "bg-[#FFD700] text-black"
                  : "hover:bg-[#FFD700] hover:text-black text-black"
              }`}
            >
              <item.icon
                size={20}
                className={`${isActive ? "text-black" : "text-black group-hover:text-black"}`}
              />
              <span className="hidden lg:block">{item.nome}</span>
            </Link>
          );
        })}
      </div>

      {/* Configurações */}
      <div className="flex flex-col gap-2 mt-6">
        <span className="hidden lg:block text-[#1f1b06] font-light my-4 uppercase tracking-wider">
          Configurações
        </span>
        {configuracaoItems.map((item) => {
          const isActive = pathname === item.url;
          return (
            <Link
              href={item.url}
              key={item.nome}
              className={`flex items-center justify-center lg:justify-start gap-4 py-2 md:px-2 rounded-md transition-colors duration-200 ${
                isActive
                  ? "bg-[#FFD700] text-black"
                  : "hover:bg-[#FFD700] hover:text-black text-black"
              }`}
            >
              <item.icon
                size={20}
                className={`${isActive ? "text-black" : "text-black group-hover:text-black"}`}
              />
              <span className="hidden lg:block">{item.nome}</span>
            </Link>
          );
        })}
      </div>
    </aside>
  );
}
