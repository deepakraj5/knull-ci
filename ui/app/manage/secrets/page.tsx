'use client';

import { useEffect, useState } from 'react';
import {
  Box,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material';
import { useRouter } from 'next/navigation';

interface Secret {
  id: number;
  name: string;
  value: string;
}

export default function SecretListPage() {
  const router = useRouter();
  const [secrets, setSecrets] = useState<Secret[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch('/api/v1/secrets')
      .then((res) => {
        if (!res.ok) throw new Error('Failed to fetch secrets');
        return res.json();
      })
      .then((data) => {
        setSecrets(data);
      })
      .catch((err) => {
        console.error(err);
        setSecrets([]);
      })
      .finally(() => setLoading(false));
  }, []);

  return (
    <Box p={4}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h5" fontWeight={600}>
          Secrets
        </Typography>
        <Button variant="contained" onClick={() => router.push('/manage/secrets/create')}>
          Create Secret
        </Button>
      </Box>

      {loading ? (
        <Typography>Loading secrets...</Typography>
      ) : secrets.length === 0 ? (
        <Typography color="text.secondary" textAlign="center" mt={2}>
          No secrets available.
        </Typography>
      ) : (
        <TableContainer component={Paper} sx={{ borderRadius: 3 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><strong>Name</strong></TableCell>
                <TableCell><strong>Value</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {secrets.map((secret) => (
                <TableRow key={secret.id} hover>
                  <TableCell>{secret.name}</TableCell>
                  {/* Mask or show value cautiously */}
                  <TableCell>{secret.value}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Box>
  );
}
