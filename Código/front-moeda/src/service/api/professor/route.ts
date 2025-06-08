import { NextResponse } from 'next/server';
import axios from 'axios';

export async function GET(
    request: Request,
    { params }: { params: { documento: string } }
) {
    try {
        const response = await axios.get(`http://localhost:8080/professor/${params.documento}/saldo`);
        return NextResponse.json(response.data);
    } catch (error) {
        console.error('Erro ao buscar saldo do professor:', error);
        return NextResponse.json(
            { error: 'Erro ao buscar saldo do professor' },
            { status: 500 }
        );
    }
} 