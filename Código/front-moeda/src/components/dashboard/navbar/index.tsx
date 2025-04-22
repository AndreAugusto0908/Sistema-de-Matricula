"use client";

import { ChevronDown } from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { userDropdownItems } from "@/utils/constants";

interface DashNavbarProps {
  Icon: React.ElementType;
  title: string;
}

export function DashNavbar({ Icon, title }: DashNavbarProps) {
  const fullName = "John Doe"; // Você pode pegar o nome real do usuário depois
  const initials = fullName
    .split(" ")
    .slice(0, 2)
    .map((word) => word[0])
    .join("")
    .toUpperCase();

  return (
    <div className="flex items-center justify-between p-4 border-b border-gray-300 bg-[#F5F5F5]">
      {/* Título e ícone */}
      <div className="hidden md:flex items-center gap-3 text-xl px-2 text-[#161616] whitespace-nowrap">
        <div className="text-2xl text-[#1A2A6C]">
          <Icon />
        </div>
        <span className="font-semibold truncate max-w-xs">{title}</span>
      </div>

      {/* Perfil Dropdown */}
      <DropdownMenu>
        <DropdownMenuTrigger asChild>
          <button className="flex items-center gap-3 focus:outline-none hover:cursor-pointer">
            <div
              className="w-10 h-10 flex items-center justify-center rounded-md border-2 font-semibold text-sm text-[#1A2A6C]"
              style={{
                backgroundColor: "#FFD70033", // dourado translúcido
                borderColor: "#FFD700",
              }}
            >
              {initials}
            </div>
            <div className="hidden md:flex flex-col items-start text-[#161616]">
              <span className="text-xs leading-3 font-medium">{fullName}</span>
              <span className="text-[10px] text-gray-500">Admin</span>
            </div>
            <ChevronDown className="w-4 h-4 text-gray-500" />
          </button>
        </DropdownMenuTrigger>

        <DropdownMenuContent align="end" className="w-56 mt-2 bg-white shadow-md border border-gray-200 rounded-md">
          {userDropdownItems.map((item) => (
            <DropdownMenuItem
              key={item.label}
              onClick={() => (window.location.href = item.url)}
              className="flex items-center gap-2 cursor-pointer hover:bg-[#FFD70022]"
            >
              <item.icon className="w-4 h-4 text-[#1A2A6C]" />
              <span className="text-[#161616]">{item.label}</span>
            </DropdownMenuItem>
          ))}
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
  );
}
