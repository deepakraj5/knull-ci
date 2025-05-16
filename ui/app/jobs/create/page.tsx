'use client';

import { useState } from 'react';
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

const mockSecrets = [
    { id: 101, name: 'GitHub Secret' },
    { id: 102, name: 'GitLab Token' },
];

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
    });

    const handleChange = (e: any) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name as string]: value }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        console.log('Form submitted:', formData);
        router.push('/jobs');
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
                    />

                    <StyledFormControl fullWidth margin="normal">
                        <InputLabel id="scm-secret-label">SCM Secret</InputLabel>
                        <Select
                            labelId="scm-secret-label"
                            name="scmSecretId"
                            value={formData.scmSecretId}
                            onChange={handleChange}
                            label="SCM Secret"
                        >
                            {mockSecrets.map((secret) => (
                                <MenuItem key={secret.id} value={secret.id}>
                                    {secret.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </StyledFormControl>

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
