'use client';

import { useEffect, useState } from 'react';
import {
    CircularProgress,
    Container,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Link,
    Chip,
    FormControl,
    MenuItem,
    Select,
    IconButton,
    Menu,
} from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';

type Build = {
    id: number;
    jobId: number;
    status: string;
    ref: string;
    headCommit: string;
    repositoryId: number;
    repositoryName: string;
    repositoryFullName: string;
    repositoryUrl: string;
    repositoryCloneUrl: string;
    repositoryOwner: string;
    createdAt: string;
    updatedAt: string;
};

const statusColor = (status: string) => {
    switch (status.toUpperCase()) {
        case 'QUEUE':
            return 'warning';
        case 'BUILDING':
            return 'info';
        case 'SUCCESS':
            return 'success';
        case 'FAILED':
            return 'error';
        default:
            return 'default';
    }
};

const statuses = ['ALL', 'QUEUE', 'BUILDING', 'SUCCESS', 'FAILED'];

import { styled } from '@mui/material/styles';
import { useRouter } from 'next/navigation';

const StyledFilterWrapper = styled('div')(({ theme }) => ({
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing(3),
}));

const StyledPaper = styled(Paper)(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(2),
    padding: `${theme.spacing(1)} ${theme.spacing(2)}`,
    borderRadius: 16,
    background: '#f8f9fa',
    boxShadow: '0 2px 8px rgba(0,0,0,0.08)',
}));

const StyledSelect = styled(Select)(({ theme }) => ({
    backgroundColor: '#fff',
    borderRadius: 12,
    minWidth: 150,
    fontWeight: 500,
    boxShadow: '0 1px 4px rgba(0,0,0,0.1)',
    '& .MuiOutlinedInput-notchedOutline': {
        borderColor: '#d0d7de',
    },
    '&:hover .MuiOutlinedInput-notchedOutline': {
        borderColor: theme.palette.primary.light,
    },
    '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
        borderColor: theme.palette.primary.main,
        borderWidth: 2,
    },
}));


export default function AllBuildsPage() {
    const [builds, setBuilds] = useState<Build[]>([]);
    const [loading, setLoading] = useState(true);
    const [selectedStatus, setSelectedStatus] = useState<string>('ALL');
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [selectedJobId, setSelectedJobId] = useState<number | null>(null);
    const router = useRouter();

    const handleStatusChange = (event: any) => {
        setSelectedStatus(event.target.value);
    };

    const handleClick = (event: React.MouseEvent<HTMLElement>, jobId: number) => {
        setAnchorEl(event.currentTarget);
        setSelectedJobId(jobId);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleGoToJob = () => {
        if (selectedJobId) {
            // Redirect to the related job (replace this URL with your actual job URL)
            window.open(`http://localhost:8080/jobs/${selectedJobId}`, '_blank');
        }
        handleClose();
    };

    useEffect(() => {
        setLoading(true);
        const statusParam = `?status=${selectedStatus}`;
        fetch(`http://localhost:8080/api/v1/builds${statusParam}`)
            .then((res) => res.json())
            .then((data) => {
                setBuilds(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error('Error fetching builds:', err);
                setLoading(false);
            });
    }, [selectedStatus]);

    return (
        <Container sx={{ mt: 4 }}>
            <StyledFilterWrapper>
                <Typography variant="h4" sx={{ fontWeight: 600 }}>
                    All Builds
                </Typography>

                <StyledPaper>
                    <Typography variant="body1" sx={{ fontWeight: 500 }}>
                        Filter by status:
                    </Typography>

                    <FormControl size="small" variant="outlined">
                        <StyledSelect
                            value={selectedStatus}
                            onChange={handleStatusChange}
                            displayEmpty
                        >
                            {statuses.map((status) => (
                                <MenuItem key={status} value={status}>
                                    {status}
                                </MenuItem>
                            ))}
                        </StyledSelect>
                    </FormControl>
                </StyledPaper>
            </StyledFilterWrapper>

            {loading ? (
                <CircularProgress />
            ) : builds.length === 0 ? (
                <Typography>No builds found for this status.</Typography>
            ) : (
                <TableContainer component={Paper} sx={{ borderRadius: 2, boxShadow: 3 }}>
                    <Table>
                        <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
                            <TableRow>
                                <TableCell sx={{ fontWeight: 'bold' }}>Build ID</TableCell>
                                <TableCell sx={{ fontWeight: 'bold' }}>Status</TableCell>
                                <TableCell sx={{ fontWeight: 'bold' }}>Repository</TableCell>
                                <TableCell sx={{ fontWeight: 'bold' }}>Branch</TableCell>
                                <TableCell sx={{ fontWeight: 'bold' }}>Commit</TableCell>
                                <TableCell sx={{ fontWeight: 'bold' }}>Created At</TableCell>
                                <TableCell sx={{ fontWeight: 'bold' }}></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {builds.map((build, index) => (
                                <TableRow
                                    key={build.id}
                                    sx={{
                                        backgroundColor: index % 2 === 0 ? 'white' : '#fafafa',
                                        '&:hover': {
                                            backgroundColor: '#f0f0f0',
                                        },
                                        cursor: "pointer"
                                    }}
                                    onClick={() => router.push("/builds/" + build.id + "/build")}
                                >
                                    <TableCell>{build.id}</TableCell>
                                    <TableCell>
                                        <Chip
                                            label={build.status}
                                            color={statusColor(build.status)}
                                            variant="outlined"
                                            size="small"
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Link
                                            href={build.repositoryCloneUrl}
                                            target="_blank"
                                            rel="noopener"
                                            underline="hover"
                                        >
                                            {build.repositoryName}
                                        </Link>
                                    </TableCell>
                                    <TableCell>{build.ref.split("/")[2]}</TableCell>
                                    <TableCell>
                                        <code style={{ fontFamily: 'monospace' }}>
                                            {build.headCommit.slice(0, 7)}
                                        </code>
                                    </TableCell>
                                    <TableCell>
                                        {new Date(build.createdAt).toLocaleString()}
                                    </TableCell>
                                    <TableCell>
                                        <IconButton
                                            size="small"
                                            onClick={(e) => handleClick(e, build.jobId)}
                                        >
                                            <MoreVertIcon />
                                        </IconButton>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}

            {/* Menu for options */}
            <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleClose}
                MenuListProps={{
                    'aria-labelledby': 'basic-button',
                }}
            >
                <MenuItem onClick={handleGoToJob}>Go to Related Job</MenuItem>
            </Menu>
        </Container>
    );
}
