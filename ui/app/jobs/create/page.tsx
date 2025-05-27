'use client';

import { useState, useEffect } from 'react';
import {
    Box,
    Button,
    Container,
    MenuItem,
    Paper,
    TextField,
    Typography,
    Select,
    FormControl,
    InputLabel,
    styled,
} from '@mui/material';
import { useRouter } from 'next/navigation';

const StyledTextField = styled(TextField)(({ theme }) => ({
    '& .MuiInputBase-root': {
        backgroundColor: '#fff',
        borderRadius: 12,
        paddingLeft: theme.spacing(1.5),
        paddingRight: theme.spacing(1.5),
        boxShadow: '0 1px 3px rgba(0,0,0,0.08)',
        fontWeight: 500,
    },
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

const StyledFormControl = styled(FormControl)(({ theme }) => ({
    backgroundColor: '#fff',
    borderRadius: 12,
    boxShadow: '0 1px 3px rgba(0,0,0,0.08)',
    '& .MuiInputLabel-root': {
        fontWeight: 500,
    },
    '& .MuiOutlinedInput-root': {
        borderRadius: 12,
        fontWeight: 500,
        '&:hover .MuiOutlinedInput-notchedOutline': {
            borderColor: theme.palette.primary.light,
        },
        '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
            borderColor: theme.palette.primary.main,
            borderWidth: 2,
        },
    },
}));

export default function CreateJobPage() {
    const router = useRouter();

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        scmUrl: '',
        scmSecretId: '',
        branch: '',
        knullFileLocation: '',
        isPrivateRepo: 'false', // string for Select
        isActive: 'true'
    });

    const [secrets, setSecrets] = useState<{ id: number; name: string }[]>([]);
    const [loadingSecrets, setLoadingSecrets] = useState(true);
    const [errorLoadingSecrets, setErrorLoadingSecrets] = useState<string | null>(null);

    useEffect(() => {
        async function fetchSecrets() {
            try {
                setLoadingSecrets(true);
                setErrorLoadingSecrets(null);

                const res = await fetch('/api/v1/secrets');
                if (!res.ok) {
                    throw new Error(`Failed to fetch secrets: ${res.status}`);
                }

                const data = await res.json();
                // Assuming data is an array of secrets with { id, name }
                setSecrets(data);
            } catch (error: any) {
                setErrorLoadingSecrets(error.message || 'Unknown error');
            } finally {
                setLoadingSecrets(false);
            }
        }

        fetchSecrets();
    }, []);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement> | any) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch('/api/v1/jobs', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    ...formData,
                    isPrivateRepo: formData.isPrivateRepo === 'true',
                    isActive: formData.isActive === 'true'
                }),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            console.log('Job created:', result);
            router.push('/jobs');
        } catch (error) {
            console.error('Failed to create job:', error);
        }
    };

    return (
        <Container sx={{ mt: 1 }}>
            <Paper sx={{ p: 4, borderRadius: 4, boxShadow: 4 }}>
                <Typography variant="h5" fontWeight={600} gutterBottom>
                    Create Job
                </Typography>

                <form onSubmit={handleSubmit}>
                    <StyledTextField
                        fullWidth
                        label="Name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        margin="normal"
                        required
                    />
                    <StyledTextField
                        fullWidth
                        label="Description"
                        name="description"
                        value={formData.description}
                        onChange={handleChange}
                        margin="normal"
                    />
                    <StyledTextField
                        fullWidth
                        label="SCM URL"
                        name="scmUrl"
                        value={formData.scmUrl}
                        onChange={handleChange}
                        margin="normal"
                        required
                    />

                    <StyledFormControl fullWidth margin="normal" required>
                        <InputLabel id="is-private-repo-label">Is Private Repo?</InputLabel>
                        <Select
                            labelId="is-private-repo-label"
                            name="isPrivateRepo"
                            value={formData.isPrivateRepo}
                            onChange={handleChange}
                            label="Is Private Repo?"
                        >
                            <MenuItem value="true">Yes</MenuItem>
                            <MenuItem value="false">No</MenuItem>
                        </Select>
                    </StyledFormControl>

                    {formData.isPrivateRepo === 'true' && (
                        <StyledFormControl fullWidth margin="normal" required>
                            <InputLabel id="scm-secret-label">SCM Secret</InputLabel>
                            <Select
                                labelId="scm-secret-label"
                                name="scmSecretId"
                                value={formData.scmSecretId}
                                onChange={handleChange}
                                label="SCM Secret"
                                disabled={loadingSecrets || !!errorLoadingSecrets}
                            >
                                {loadingSecrets && <MenuItem>Loading secrets...</MenuItem>}
                                {errorLoadingSecrets && (
                                    <MenuItem disabled>{`Error: ${errorLoadingSecrets}`}</MenuItem>
                                )}
                                {!loadingSecrets &&
                                    !errorLoadingSecrets &&
                                    secrets.map((secret) => (
                                        <MenuItem key={secret.id} value={secret.id}>
                                            {secret.name}
                                        </MenuItem>
                                    ))}
                            </Select>
                        </StyledFormControl>
                    )}

                    <StyledTextField
                        fullWidth
                        label="Branch"
                        name="branch"
                        value={formData.branch}
                        onChange={handleChange}
                        margin="normal"
                    />
                    <StyledTextField
                        fullWidth
                        label="Knull File Location"
                        name="knullFileLocation"
                        value={formData.knullFileLocation}
                        onChange={handleChange}
                        margin="normal"
                    />

                    <StyledFormControl fullWidth margin="normal" required>
                        <InputLabel id="is-active-label">Is Active?</InputLabel>
                        <Select
                            labelId="is-active-label"
                            name="isActive"
                            value={formData.isActive}
                            onChange={handleChange}
                            label="Is Active?"
                        >
                            <MenuItem value="true">Yes</MenuItem>
                            <MenuItem value="false">No</MenuItem>
                        </Select>
                    </StyledFormControl>

                    <Box mt={3} display="flex" justifyContent="space-between">
                        <Button variant="outlined" onClick={() => router.push('/jobs')}>
                            Cancel
                        </Button>
                        <Button variant="contained" type="submit">
                            Create Job
                        </Button>
                    </Box>
                </form>
            </Paper>
        </Container>
    );
}
