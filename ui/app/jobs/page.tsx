'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';
import {
    Box,
    Button,
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
    CircularProgress,
} from '@mui/material';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material/styles';

type Job = {
    id: number;
    name: string;
    description: string;
    scmUrl: string;
    scmSecretId: number;
    branch: string;
    knullFileLocation: string;
    createdAt: string;
};

const StyledTableCell = styled(TableCell)({
    fontWeight: 500,
});

export default function JobsPage() {
    const [jobs, setJobs] = useState<Job[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        setLoading(true);
        fetch('http://localhost:8080/api/v1/jobs')
            .then(res => {
                if (!res.ok) throw new Error('Failed to fetch jobs');
                return res.json();
            })
            .then(data => setJobs(data))
            .catch(err => {
                console.error(err);
                setError('Failed to load jobs');
            })
            .finally(() => setLoading(false));
    }, []);

    return (
        <Container sx={{ mt: 4 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
                <Typography variant="h4" fontWeight={600}>Jobs</Typography>
                <Link href="/jobs/create" passHref>
                    <Button variant="contained" color="primary" sx={{ borderRadius: 2, textTransform: 'none' }}>
                        Create Job
                    </Button>
                </Link>
            </Box>

            {loading ? (
                <CircularProgress />
            ) : error ? (
                <Typography color="error">{error}</Typography>
            ) : (
                <TableContainer component={Paper} sx={{ borderRadius: 2, boxShadow: 3 }}>
                    <Table>
                        <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
                            <TableRow>
                                <StyledTableCell>Name</StyledTableCell>
                                <StyledTableCell>Description</StyledTableCell>
                                <StyledTableCell>SCM URL</StyledTableCell>
                                <StyledTableCell>Branch</StyledTableCell>
                                <StyledTableCell>Created At</StyledTableCell>
                                <StyledTableCell align="center">Actions</StyledTableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {jobs.map((job) => (
                                <TableRow key={job.id}>
                                    <TableCell>{job.name}</TableCell>
                                    <TableCell>{job.description}</TableCell>
                                    <TableCell>{job.scmUrl}</TableCell>
                                    <TableCell>{job.branch}</TableCell>
                                    <TableCell>{new Date(job.createdAt).toLocaleString()}</TableCell>
                                    <TableCell align="center">
                                        <IconButton
                                            color="primary"
                                            onClick={() => console.log(`Run job ${job.id}`)}
                                        >
                                            <PlayArrowIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}
        </Container>
    );
}
