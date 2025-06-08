type VantagensCardProps = {
  descricao: string;
  valor: string;
  empresa: string;
  onResgatar?: () => void;
};

export default function VantagensCard({ descricao, valor, empresa, onResgatar }: VantagensCardProps) {
  return (
    <div className="bg-white rounded-lg shadow-md p-6 w-full max-w-md mx-auto mb-6 transition-transform hover:scale-[1.02]">
      <div className="mb-4">
        <p className="text-sm text-gray-500">Descrição</p>
        <p className="text-lg font-semibold text-gray-800">{descricao}</p>
      </div>
      <div className="mb-4">
        <p className="text-sm text-gray-500">Valor</p>
        <p className="text-lg font-semibold text-green-600">R$ {valor}</p>
      </div>
      <div className="mb-6">
        <p className="text-sm text-gray-500">Empresa</p>
        <p className="text-lg font-medium text-gray-700">{empresa}</p>
      </div>

      {onResgatar && (
        <button
          onClick={onResgatar}
          className="w-full bg-[#FFD700] text-black font-semibold py-2 px-4 rounded hover:bg-yellow-400 focus:outline-none focus:ring-2 focus:ring-yellow-300 transition"
        >
          Resgatar
        </button>
      )}
    </div>
  );
}