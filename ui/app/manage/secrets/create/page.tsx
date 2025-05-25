'use client';

import { useState } from 'react';
import {
  Box,
  Button,
  Container,
  Paper,
  TextField,
  Typography,
} from '@mui/material';
import { useRouter } from 'next/navigation';

export default function CreateSecretPage() {
  const router = useRouter();
  const [secret, setSecret] = useState({ name: '', value: '' });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSecret((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/v1/secrets', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(secret),
      });

      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

      router.push('/manage/secrets');
    } catch (err) {
      console.error('Failed to create secret:', err);
    }
  };

  return (
    <Container sx={{ mt: 2 }}>
      <Paper sx={{ p: 4, borderRadius: 3 }}>
        <Typography variant="h5" fontWeight={600} gutterBottom>
          Create Secret
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            fullWidth
            label="Name"
            name="name"
            value={secret.name}
            onChange={handleChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Value"
            name="value"
            type="password"
            value={secret.value}
            onChange={handleChange}
            margin="normal"
          />
          <Box mt={3} display="flex" justifyContent="space-between">
            <Button variant="outlined" onClick={() => router.push('/manage/secrets')}>
              Cancel
            </Button>
            <Button variant="contained" type="submit">
              Create
            </Button>
          </Box>
        </form>
      </Paper>
    </Container>
  );
}
