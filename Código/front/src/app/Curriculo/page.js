'use client';
import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import "./curriculo.css"

export default function Curriculo() {
    const searchParams = useSearchParams();
    const [curriculo, setCurriculo] = useState(null);
    const [expanded, setExpanded] = useState({});

    useEffect(() => {
        const data = searchParams.get("data");
        if (data) {
            setCurriculo(JSON.parse(decodeURIComponent(data)));
        }
    }, [searchParams]);

    const toggleExpand = (id) => {
        setExpanded(prev => ({ ...prev, [id]: !prev[id] }));
    };

    const semestres = [
        "semestre1", "semestre2", "semestre3", "semestre4", 
        "semestre5", "semestre6", "semestre7", "semestre8"
    ];

    if (!curriculo) {
        return <div className="curriculo-container">Carregando currículo...</div>;
    }

    return (
        <div className="curriculo-container">
            <h1>Currículo</h1>
            <div className="disciplinas">
                {semestres.map((semestre) => (
                    <div key={semestre} className="semestre">
                        <h2>{semestre.replace("semestre", "Semestre ")}</h2>
                        {curriculo[semestre]?.length > 0 ? (
                            <ul>
                                {curriculo[semestre].map(disciplina => (
                                    <li key={disciplina.id} className="disciplina">
                                        <h3 onClick={() => toggleExpand(disciplina.id)}>
                                            {disciplina.nome} {expanded[disciplina.id] ? "▲" : "▼"}
                                        </h3>
                                        {expanded[disciplina.id] && (
                                            <div className="detalhes">
                                                <p>{disciplina.descricao}</p>
                                                <p><strong>Curso:</strong> {disciplina.curso.nome}</p>
                                                <p><strong>Instituição:</strong> {disciplina.curso.instituicao}</p>
                                                <p>
                                                    <strong>Período de matrícula:</strong> {disciplina.curso.inicioPeriodoMatricula} até {disciplina.curso.fimPeriodoMatricula}
                                                </p>
                                                <span className={disciplina.opcional ? "opcional" : "obrigatoria"}>
                                                    {disciplina.opcional ? "Opcional" : "Obrigatória"}
                                                </span>
                                            </div>
                                        )}
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p className="semestre-vazio">Este semestre não possui matérias disponíveis.</p>
                        )}
                    </div>
                ))}
            </div>
        </div>
    );
}
