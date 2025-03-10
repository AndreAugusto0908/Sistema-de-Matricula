'use client';
import { getUserProfile, getToken } from "../AuthService";
import { useEffect } from "react";
import { useRouter } from 'next/navigation'

export default function Home() {
    const router = useRouter()
    
    useEffect(() => {
        if (!getToken()) {
            router.push("/");
        }
    }, []);

    const gerarCurriculo = async () => {
        try {
            const response = await fetch("http://localhost:8080/secretaria/gerarCurriculo", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${getToken()}`,
                    "Content-Type": "application/json"
                }
            });
    
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Erro ao gerar currículo: ${errorText}`);
            }
    
            const data = await response.json();
            router.push(`/Curriculo?data=${encodeURIComponent(JSON.stringify(data))}`);
        } catch (error) {
            console.error("Erro na requisição:", error);
            alert(`Falha ao gerar currículo: ${error.message}`);
        }
    };
    

    const verDisciplinas = () => {
        router.push("/Disciplinas");
    }

    if(getUserProfile() == "SECRETARIA") {
        return (
    
            <div className="menu_container">
                <button className="btn">Atualizar aluno</button>
                <button className="btn">Atualizar professor</button>
                <button className="btn" onClick={gerarCurriculo}>Gerar Currículo</button>
            </div>
        
      );
    } else if(getUserProfile() == "ALUNO") {
        return (
    
            <div className="menu_container">
                <button className="btn">Matricular em uma Disciplina</button>
                <button className="btn">Cancelar Matrícula</button>
                <button className="btn" onClick={verDisciplinas}>Ver disciplinas</button>
            </div>
        
      );
    } 

}
