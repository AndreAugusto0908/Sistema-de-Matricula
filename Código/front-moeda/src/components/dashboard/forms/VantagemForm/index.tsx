import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod"; // ajuste se necessário
import { api } from "@/service/api"; // ajuste o caminho
import { parseCookies } from "nookies";
import { toast } from "react-hot-toast";
import { useAuth } from "@/contexts/AuthContext";

interface VantagemFormProps {
    closeModal: () => void;
}

const vantagemSchema = z.object({
    descricao: z.string().min(1, "Descrição é obrigatória"),
    valorMoedas: z.coerce.number().positive("Preço deve ser maior que zero"),
    foto: z.string().url("Deve ser uma URL válida"),
});

type VantagemFormData = z.infer<typeof vantagemSchema>;

export const VantagemForm = ({ closeModal }: VantagemFormProps) => {
    const { register, handleSubmit, formState: { errors } } = useForm<VantagemFormData>({
        resolver: zodResolver(vantagemSchema),
    });

const { user } = useAuth();

const onSubmit = async (data: VantagemFormData) => {
    try {
        const { 'nextauth.token': token } = parseCookies();

        console.log(token)

        if (!user?.id) {
            toast.error("ID da empresa não encontrado.");
            return;
        }

        const payload = {
            valorMoedas: data.valorMoedas,
            descricao: data.descricao,
            foto: data.foto,
            idEmpresa: user.id
        };

        await api.post("/vantagem/criar", payload, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        toast.success("Vantagem criada com sucesso!");
        closeModal();
    } catch (error: any) {
        toast.error(error?.response?.data?.message || "Erro ao criar vantagem.");
    }
};

    return (
        <div className="w-full flex flex-col justify-center gap-4 vantagem_form">
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="form_modal">
                    <h1 className="text-center text-2xl font-bold mb-4">Adicionar Vantagem</h1>

                    <div className="mb-4">
                        <label className="block text-gray-700 font-medium mb-2">Descrição</label>
                        <input
                            type="text"
                            {...register("descricao")}
                            className="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                            placeholder="Digite a descrição"
                        />
                        {errors.descricao && <p className="text-red-500 text-sm mt-1">{errors.descricao.message}</p>}
                    </div>

                    <div className="mb-4">
                        <label className="block text-gray-700 font-medium mb-2">Preço</label>
                        <input
                            type="number"
                            step="0.01"
                            {...register("valorMoedas")}
                            className="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                            placeholder="Digite o preço"
                        />
                        {errors.valorMoedas && <p className="text-red-500 text-sm mt-1">{errors.valorMoedas.message}</p>}
                    </div>

                    <div className="mb-4">
                        <label className="block text-gray-700 font-medium mb-2">Link da foto</label>
                        <input
                            type="text"
                            {...register("foto")}
                            className="w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-yellow-500"
                            placeholder="Cole a URL da imagem"
                        />
                        {errors.foto && <p className="text-red-500 text-sm mt-1">{errors.foto.message}</p>}
                    </div>

                    <div className="flex gap-4 justify-end mt-6">
                        <button
                            type="submit"
                            className="bg-yellow-500 hover:bg-yellow-600 text-white font-bold py-2 px-6 rounded"
                        >
                            Salvar
                        </button>
                        <button
                            type="button"
                            onClick={() => { console.log("cancelar"); closeModal(); }}
                            className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-6 rounded"
                        >
                            Cancelar
                        </button>
                    </div>
                </div>
            </form>
        </div>
    );
};
