import { useState } from "react";
import { Search, Plus, History} from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { VantagemForm } from "../../forms/VantagemForm/index"; 
import ExtratoModal from "../../modal/extratoModal";
import HistoricoModal from "../../modal/hitoricoModal";

export function SearchAddVantagem({ setFiltro }: { setFiltro: (value: string) => void }) {
  const [search, setSearch] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModalOpenHistorico, setIsModalOpenHistorico] = useState(false);
  const [isModalOpenExtrato, setIsModalOpenExtrato] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setSearch(value);
    setFiltro(value);
  };

  return (
    <>
      <div className="flex  items-center w-full gap-2">
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
          className="flex items-center gap-2 bg-[#FFD700] text-black hover:bg-[#e6c200] transition-all font-semibold"
          onClick={() => setIsModalOpen(true)}
        >
          Adicionar Vantagem
          <Plus size={18} />
        </Button>

        <Button
          className="flex items-center gap-2 bg-[#FFD700] text-black hover:bg-[#e6c200] transition-all font-semibold"
          onClick={() => (setIsModalOpenHistorico(true))}
        >
          <History size={18} />
        </Button>

        <Button
          className="flex items-center gap-2 bg-[#FFFFF] text-black transition-all font-semibold"
          onClick={() => setIsModalOpenExtrato(true)}
        >
          Extrato
        </Button>
      </div>

      {/* Modal */}
      {isModalOpen && (
        <div className="fixed inset-0 w-full h-full flex items-center justify-center bg-black/50 z-50">
          <div className="max-w-md w-full bg-white rounded-md p-6">
            <VantagemForm closeModal={() => setIsModalOpen(false)} />
          </div>
        </div>
      )}
      {isModalOpenExtrato && (
        <div className="fixed inset-0 w-full h-full flex items-center justify-center bg-black/50 z-50">
          <div className="max-w-md w-full bg-white rounded-md p-6">
            <ExtratoModal closeModal={() => setIsModalOpenExtrato(false)} />
          </div>
        </div>
      )}
       {isModalOpenHistorico && (
        <div className="fixed inset-0 w-full h-full flex items-center justify-center bg-black/50 z-50">
          <div className="max-w-md w-full bg-white rounded-md p-6">
            <HistoricoModal closeModal={() => setIsModalOpenHistorico(false)} />
          </div>
        </div>
      )}
    </>
  );
}
