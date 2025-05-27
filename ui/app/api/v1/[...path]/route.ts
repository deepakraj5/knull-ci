import { NextRequest, NextResponse } from 'next/server';

const BACKEND_BASE_URL = 'http://localhost:8080/api/v1';

export async function GET(req: NextRequest, { params }: { params: { path?: string[] } }) {
    return proxyRequest(req, params.path || []);
}

export async function POST(req: NextRequest, { params }: { params: { path?: string[] } }) {
    return proxyRequest(req, params.path || []);
}

export async function PUT(req: NextRequest, { params }: { params: { path?: string[] } }) {
    return proxyRequest(req, params.path || []);
}

export async function DELETE(req: NextRequest, { params }: { params: { path?: string[] } }) {
    return proxyRequest(req, params.path || []);
}

async function proxyRequest(req: NextRequest, path: string[]) {
    const url = `${BACKEND_BASE_URL}/${path.join('/')}`;
    const method = req.method;
    const headers = Object.fromEntries(req.headers.entries());

    const fetchOptions: RequestInit = {
        method,
        headers,
        body: ['GET', 'HEAD'].includes(method) ? undefined : await req.text(),
    };

    try {
        const backendRes = await fetch(url, fetchOptions);
        const contentType = backendRes.headers.get('content-type');

        const body = contentType?.includes('application/json')
            ? await backendRes.json()
            : await backendRes.text();

        return new NextResponse(JSON.stringify(body), {
            status: backendRes.status,
            headers: {
                'Content-Type': contentType || 'application/json',
            },
        });
    } catch (error) {
        console.error('Proxy error:', error);
        return NextResponse.json({ error: 'Proxy error' }, { status: 500 });
    }
}
