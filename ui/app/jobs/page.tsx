'use client';

import Link from 'next/link';
import { useState } from 'react';
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
} from '@mui/material';
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material/styles';

const mockJobs = [
    {
        id: 1,
        name: 'Build Web App',
        description: 'CI job for web app',
        scmUrl: 'https://github.com/org/project',
        scmSecretId: 101,
        branch: 'main',
        knullFileLocation: './knull.yml',
        createdAt: '2024-01-10T14:00:00Z',
    },
];

const StyledTableCell = styled(TableCell)({
    fontWeight: 500,
});

export default function JobsPage() {
    const [jobs] = useState(mockJobs);

    return (
        <Container sx={{ mt: 4 }}>
            <Box
                sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
                <Typography variant="h4" fontWeight={600}>Jobs</Typography>
                <Link href="/jobs/create" passHref>
                    <Button variant="contained" color="primary" sx={{ borderRadius: 2, textTransform: 'none' }}>
                        Create Job
                    </Button>
                </Link>
            </Box>

            <TableContainer component={Paper} sx={{ borderRadius: 2, boxShadow: 3 }}>
                <Table>
                    <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
                        <TableRow>
                            <StyledTableCell>Name</StyledTableCell>
                            <StyledTableCell>Description</StyledTableCell>
                            <StyledTableCell>SCM URL</StyledTableCell>
                            <StyledTableCell>Branch</StyledTableCell>
                            <StyledTableCell>Created At</StyledTableCell>
                            <StyledTableCell align="center">Actions</StyledTableCell> {/* New */}
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
        </Container>
    );
} 