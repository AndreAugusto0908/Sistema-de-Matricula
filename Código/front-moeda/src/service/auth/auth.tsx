import { api } from "../api"

type loginData = {
    documento: string;
    senha: string;
}

type ResponseLogin = {
  token: string;
};

export async function loginRequest(data: loginData): Promise<ResponseLogin> {
    const response = await api.post<ResponseLogin>("/auth/login", data);
    return response.data;
}