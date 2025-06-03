import toast from "react-hot-toast";
import { api } from "../api"

type loginData = {
    documento: string;
    senha: string;
}

type ResponseLogin = {
  token: string;
};

export async function loginRequest(data: loginData): Promise<ResponseLogin | undefined> {
  try {
    const response = await api.post<ResponseLogin>("/auth/login", data);
    toast.success("Login realizado com sucesso!");
    return response.data;
  } catch (error: any) {
    const keys = Object.keys(error.response.data);
    for(const erro of keys) {
      toast.error(error.response.data[erro]);
    }
    console.error(error);
    return undefined;
  }
}
