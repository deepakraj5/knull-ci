'use client';

import { useRouter } from 'next/navigation';
import {
  Box,
  Card,
  CardActionArea,
  CardContent,
  Grid,
  Typography,
} from '@mui/material';
import KeyIcon from '@mui/icons-material/Key';

const manageOptions = [
  {
    title: 'Secret Management',
    description: 'Manage your secrets used in jobs',
    route: '/manage/secrets',
    icon: <KeyIcon fontSize="large" color="primary" />,
  },
];

export default function ManagePage() {
  const router = useRouter();

  return (
    <Box p={4}>
      <Typography variant="h4" fontWeight={600} gutterBottom>
        Manage Knull
      </Typography>

      <Grid
        container
        sx={{
          display: 'grid',
          gridTemplateColumns: {
            xs: 'repeat(1, 1fr)',
            sm: 'repeat(2, 1fr)',
            md: 'repeat(3, 1fr)',
          },
          gap: 3,
        }}
      >
        {manageOptions.map((option, index) => (
          <Grid key={index}>
            <Card sx={{ borderRadius: 3 }}>
              <CardActionArea onClick={() => router.push(option.route)}>
                <CardContent sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                  {/* Icon on the left */}
                  <Box>{option.icon}</Box>

                  <Box>
                    <Typography variant="h6" fontWeight={600}>
                      {option.title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      {option.description}
                    </Typography>
                  </Box>
                </CardContent>
              </CardActionArea>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
}
