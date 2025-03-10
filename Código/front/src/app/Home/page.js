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

    const verDisciplinas = () => {
        router.push("/Disciplinas");
    }

    if(getUserProfile() == "SECRETARIA") {
        return (
    
            <div className="menu_container">
                <button className="btn">Atualizar aluno</button>
                <button className="btn">Atualizar professor</button>
                <button className="btn">Gerar Currículo</button>
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
