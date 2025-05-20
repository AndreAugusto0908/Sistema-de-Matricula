"use client";

import { loginRequest } from "@/service/auth/auth";
import { useRouter } from "next/navigation";
import { setCookie } from 'nookies'
import { useState, useContext, ReactNode } from "react";
import { createContext } from "react";
import { jwtDecode } from "jwt-decode";

type User = {
    nome: string;
    documento: string;
    role: string;
    id: string;
}

type DecodedToken = {
    nome: string;
    documento: string;
    role: string;
    id: string;
    exp: number;
    sub: string;
};

type LoginData = {
    documento: string;
    senha: string;
}

type AuthContextType = {
    isAuthenticated: boolean;
    user: User | null;
    signIn: (data: LoginData) => Promise<void>
}

export const AuthContext = createContext({} as AuthContextType)

export function AuthProvider({ children }: { children: ReactNode }) {
    const [user, setUser] = useState<User | null>(null)
    const isAuthenticated = !!user;
    const router = useRouter();

    async function signIn({ documento, senha }: LoginData) {
        try {
            const response = await loginRequest({ documento, senha });
            const token = response?.token;

            if (!token) {
                throw new Error('No token received');
            }

            setCookie(undefined, 'nextauth.token', token, {
                maxAge: 60 * 60 * 1,
                path: '/',
            });

            const decoded: DecodedToken = jwtDecode(token);

            setUser({
                nome: decoded.nome,
                documento: decoded.documento,
                role: decoded.role,
                id: decoded.id
            })

            switch (decoded.role) {
                case "ROLE_ALUNO":
                    router.push("/dashboard/aluno");
                    break;
                case "ROLE_EMPRESA":
                    router.push("/dashboard/empresa");
                    break;
                case "ROLE_PROFESSOR":
                    router.push("/dashboard/professor");
                    break;
            }
        } catch (err) {
            console.error("Erro no login:", err);
        }
    }

    return (
        <AuthContext.Provider value={{ user, isAuthenticated, signIn }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};