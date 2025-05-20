'use client';

import { useEffect, useRef, useState } from 'react';
import { useParams } from 'next/navigation';
import { Client } from '@stomp/stompjs';
import { Box, List, ListItem, ListItemText, Paper, Typography } from '@mui/material';

export default function BuildLogsPage() {
    const { id } = useParams() as { id: string };
    const [logs, setLogs] = useState<string[]>([]);
    const clientRef = useRef<Client | null>(null);

    useEffect(() => {
        if (!id) return;

        const client = new Client({
            brokerURL: 'ws://localhost:8080/knull-websocket',
            reconnectDelay: 5000,
            onConnect: () => {
                console.log('Connected to WebSocket');

                client.subscribe(`/topic/logs.${id}`, message => {
                    try {
                        const payload = JSON.parse(message.body);
                        console.log(payload)
                        if (payload.message) {
                            setLogs(prev => [...prev, payload.message]);
                        }
                    } catch (err) {
                        console.error('Failed to parse message', err);
                    }
                });

                client.publish({
                    destination: '/app/logs',
                    body: JSON.stringify({ buildId: id }),
                });
            },
            onStompError: frame => {
                console.error('Broker reported error:', frame.headers['message']);
                console.error('Additional details:', frame.body);
            },
        });

        client.activate();
        clientRef.current = client;

        return () => {
            if (clientRef.current && clientRef.current.active) {
                clientRef.current.deactivate();
            }
        };
    }, [id]);

    return (
        <Box p={4}>
            <Typography variant="h4" fontWeight="bold" mb={2}>
                Logs for Build ID: {id}
            </Typography>

            <Paper
                sx={{
                    bgcolor: 'black',
                    color: 'white', // or 'success.main' from theme
                    fontFamily: 'Monospace',
                    p: 2,
                    borderRadius: 5,
                    height: 500,
                    overflowY: 'auto',
                    border: '1px solid',
                    borderColor: 'grey.700',
                }}
                elevation={3}
            >
                <List dense>
                    {logs.map((line, index) => (
                        <ListItem key={index} disablePadding>
                            <ListItemText primary={line} />
                        </ListItem>
                    ))}
                </List>
            </Paper>
        </Box>
    );
}
