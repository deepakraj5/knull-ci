'use client';

import {
    AppBar,
    Avatar,
    Box,
    Button,
    IconButton,
    Toolbar,
    Typography,
} from '@mui/material';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Image from 'next/image';

export default function TopBar() {
    return (
        <AppBar
            position="fixed"
            elevation={2}
            sx={{
                background: ' #1565c0',
                zIndex: (theme) => theme.zIndex.drawer + 1,
            }}
        >
            <Toolbar sx={{ justifyContent: 'space-between', px: 3 }}>
                {/* Left: Title */}
                <Image
                    src="/images/logo.svg"
                    width={320}
                    height={40}
                    alt="knull-ci"
                />

                {/* Right: Avatar and Button */}
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                    <IconButton sx={{ p: 0 }}>
                        <Avatar
                            sx={{
                                bgcolor: 'white',
                                color: 'primary.main',
                                width: 36,
                                height: 36,
                            }}
                        >
                            <AccountCircle />
                        </Avatar>
                    </IconButton>
                    <Button
                        variant="contained"
                        sx={{
                            textTransform: 'none',
                            fontWeight: 'bold',
                            borderRadius: 5,
                            backgroundColor: '#ffffff',
                            color: '#1565c0',
                            boxShadow: '0 2px 8px rgba(0, 0, 0, 0.15)',
                            '&:hover': {
                                backgroundColor: '#f0f0f0',
                            },
                        }}
                    >
                        Login
                    </Button>

                </Box>
            </Toolbar>
        </AppBar>
    );
}
