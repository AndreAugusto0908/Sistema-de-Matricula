'use client';

import { getUserProfile, getToken } from "../AuthService";
import { useEffect, useState } from "react";
import { useRouter } from 'next/navigation'

const Disciplinas = () => {

    const router = useRouter()

    const [turmas, setTurmas] = useState([]);

    useEffect(() => {
        if (!getToken()) {
            router.push("/");
        }
        const token = getToken();
        fetch("http://localhost:8080/aluno/getTurmasMatriculadas?matricula=1",
            {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                  }
            }
        ).then(response => {
            response.json().then(data => {
                setTurmas(data);
            })
        });
    }, []);

    return (
        <div className="turmas_container">
        {turmas.map(turma => {
            return (
                <div className="turma_container">
                    <h2>{turma.disciplina.nome}</h2>
                    <p>{turma.disciplina.descricao}</p>
                    <p>{turma.professor.nome}</p>
                    <p>{turma.professor.email}</p>
                </div>
            )
        })}
        </div>
    )


}

export default Disciplinas;