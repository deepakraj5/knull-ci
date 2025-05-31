'use client';

import {
    Box,
    CircularProgress,
    Container,
    Typography,
    Tooltip,
    Paper,
    List,
    ListItemText,
    ListItem,
} from '@mui/material';
import { keyframes, styled } from '@mui/system';
import { useEffect, useRef, useState } from 'react';
import { useParams } from 'next/navigation';
import CheckIcon from '@mui/icons-material/Check';
import ClearIcon from '@mui/icons-material/Clear';

type StageStatus = 'PENDING' | 'BUILDING' | 'SUCCESS' | 'FAILED';

type Stage = {
    id: number;
    buildId: number;
    name: string;
    status: StageStatus;
    command: string;
    createdAt: string;
    updatedAt: string;
};

const statusColors: Record<StageStatus, string> = {
    PENDING: '#bdbdbd',
    BUILDING: '#2196f3',
    SUCCESS: '#4caf50',
    FAILED: '#f44336',
};

const blink = keyframes`
  0%, 100% { opacity: 1 }
  50% { opacity: 0.4 }
`;

const PipelineContainer = styled(Box)(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: theme.spacing(2),
    marginTop: theme.spacing(6),
    overflowX: 'auto',
    paddingBottom: theme.spacing(2),
    whiteSpace: 'nowrap',
}));

const StageContainer = styled(Box)(() => ({
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    width: 100,
    flexShrink: 0,
    position: 'relative',
}));

const StageCircle = styled('div')<{ status: StageStatus }>(({ status }) => ({
    width: 40,
    height: 40,
    borderRadius: '50%',
    backgroundColor: statusColors[status],
    animation: status === 'BUILDING' ? `${blink} 1.2s infinite` : 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    color: '#fff',
    position: 'relative',
}));

const Connector = styled('div')(() => ({
    position: 'absolute',
    top: '35%',
    right: -46,
    transform: 'translateY(-50%)',
    width: 76,
    height: 2,
    backgroundColor: '#9e9e9e',
    zIndex: 0,
}));

const StageLabel = styled(Typography)(() => ({
    fontSize: '0.75rem',
    marginTop: 6,
    textAlign: 'center',
    maxWidth: '100%',
    whiteSpace: 'normal',
    wordBreak: 'break-word',
    display: '-webkit-box',
    WebkitLineClamp: 1,
    WebkitBoxOrient: 'vertical',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
}));

export default function Build() {
    const { id } = useParams();
    const [loading, setLoading] = useState(true);
    const [stages, setStages] = useState<Stage[]>([]);
    const [logs, setLogs] = useState<string[]>([]);

    const intervalRef = useRef<NodeJS.Timeout | null>(null);
    const logsIntervalRef = useRef<NodeJS.Timeout | null>(null);
    const logsEndRef = useRef<HTMLDivElement | null>(null);

    const stopPolling = () => {
        if (intervalRef.current) {
            clearInterval(intervalRef.current);
            intervalRef.current = null;
        }
        if (logsIntervalRef.current) {
            clearInterval(logsIntervalRef.current);
            logsIntervalRef.current = null;
        }
    };

    const fetchStages = (isInitial = false) => {
        if (isInitial) setLoading(true);

        fetch(`/api/v1/builds/${id}/stages`)
            .then(res => res.json())
            .then(data => {
                setStages(data);
                if (isInitial) setLoading(false);

                if (data.length > 0) {
                    const lastStage = data[data.length - 1];
                    let failedStage = data?.find((stage: { status: string; }) => stage.status === "FAILED");
                    if (['SUCCESS', 'FAILED'].includes(lastStage.status) || failedStage) {
                        stopPolling();
                    }
                }
            })
            .catch(err => {
                console.error('Error fetching stages:', err);
                if (isInitial) setLoading(false);
            });
    };

    const fetchLogs = () => {
        fetch(`/api/v1/logs/${id}`)
            .then(res => res.json())
            .then(data => {
                setLogs(data.map((log: { message: string }) => log.message));
                setTimeout(() => {
                    logsEndRef.current?.scrollIntoView({ behavior: 'smooth' });
                }, 100);
            })
            .catch(err => {
                console.error('Error fetching logs:', err);
            });
    };


    useEffect(() => {
        if (!id) return;

        fetchStages(true);
        fetchLogs();

        intervalRef.current = setInterval(() => fetchStages(false), 5000);
        logsIntervalRef.current = setInterval(() => fetchLogs(), 5000);

        return () => {
            stopPolling();
        };
    }, [id]);

    return (
        <Container sx={{ mt: 4 }}>
            <Typography variant="h4" fontWeight={600} gutterBottom>
                Build #{id} Pipeline
            </Typography>

            {loading ? (
                <CircularProgress />
            ) : stages.length === 0 ? (
                <Typography>No stages found for this build.</Typography>
            ) : (
                <PipelineContainer>
                    {stages.map((stage, index) => {
                        const isLast = index === stages.length - 1;
                        return (
                            <StageContainer key={stage.id}>
                                <Tooltip title={`Status: ${stage.status}`} arrow>
                                    <StageCircle status={stage.status}>
                                        {stage.status === 'SUCCESS' && <CheckIcon fontSize="small" />}
                                        {stage.status === 'FAILED' && <ClearIcon fontSize="small" />}
                                    </StageCircle>
                                </Tooltip>

                                {!isLast && <Connector />}

                                <Tooltip title={stage.name} arrow>
                                    <StageLabel>{stage.name}</StageLabel>
                                </Tooltip>
                            </StageContainer>
                        );
                    })}
                </PipelineContainer>
            )}

            {stages.length > 0 && (
                <Box p={4}>
                    <Paper
                        sx={{
                            bgcolor: '#1d1e1f',
                            color: 'white',
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
                            <div ref={logsEndRef} />
                        </List>
                    </Paper>
                </Box>
            )}
        </Container>
    );
}
