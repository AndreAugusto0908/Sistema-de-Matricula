import { useContext, useState } from "react";
import { Search, Plus } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { VantagemForm } from "../../forms/VantagemForm/index"; 
import ExtratoModal from "../../modal/extratoModal";
import { AuthContext } from "@/contexts/AuthContext";

export function SearchGerenciarVantagemAluno({ setFiltro }: { setFiltro: (value: string) => void }) {
  const [search, setSearch] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModalOpenExtrato, setIsModalOpenExtrato] = useState(false);
  const { user } = useContext(AuthContext)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearch(value);
    setFiltro(value);
  };

  return (
    <>
      <div className="flex flex-wrap justify-between items-center w-full gap-2 pr-5">
        <div className="relative w-full md:w-auto">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
          <Input
            type="text"
            placeholder="Pesquisar Vantagem"
            value={search}
            onChange={handleChange}
            className="pl-10 bg-[#F5F5F5] text-black placeholder:text-gray-400"
          />
        </div>
        <Button
          className="px-4 py-2 rounded-md bg-white text-gray-800 border border-gray-300 shadow-sm hover:bg-gray-100 hover:shadow-md transition-all font-medium"
          onClick={() => setIsModalOpenExtrato(true)}
        >
          Extrato
        </Button>
          <div className="mt-4 sm:mt-0">
            <p className="text-sm text-gray-500">Saldo disponível</p>
            <p className="text-2xl font-bold text-green-600">R$ {Number(user?.saldo).toFixed(2)}</p>
          </div>
      </div>

      {isModalOpenExtrato && (
        <div className="fixed inset-0 w-full h-full flex items-center justify-center bg-black/50 z-50">
          <div>
            <ExtratoModal closeModal={() => setIsModalOpenExtrato(false)} />
          </div>
        </div>
      )}
    </>
  );
}
