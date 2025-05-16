'use client';

import {
    AppBar,
    Box,
    CssBaseline,
    Divider,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Toolbar,
} from '@mui/material';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';
import DashboardIcon from '@mui/icons-material/Dashboard';
import SettingsIcon from '@mui/icons-material/Settings';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';
import TopBar from './TopBar';

const drawerWidth = 240;

const navItems = [
    { label: 'Jobs', path: '/jobs', icon: <DashboardIcon /> },
    { label: 'Builds', path: '/builds', icon: <PlayCircleIcon /> },
    { label: 'Manage', path: '/manage', icon: <SettingsIcon /> },
];

export default function SidebarLayout({ children }: { children: React.ReactNode }) {
    const pathname = usePathname();

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />

            {/* AppBar */}
            <AppBar
                position="fixed"
                sx={{
                    zIndex: (theme) => theme.zIndex.drawer + 1,
                }}
            >
                <TopBar />
            </AppBar>

            {/* Sidebar Drawer */}
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                        backgroundColor: '#f5f5f5',
                        borderRight: 0,
                    },
                }}
            >
                <Toolbar />
                <Divider />
                <List sx={{ mt: 1 }}>
                    {navItems.map(({ label, path, icon }) => {
                        const selected = pathname === path;
                        return (
                            <ListItem key={label} disablePadding sx={{ px: 1 }}>
                                <ListItemButton
                                    component={Link}
                                    href={path}
                                    selected={selected}
                                    sx={{
                                        borderRadius: 2,
                                        mx: 1,
                                        my: 0.5,
                                        color: selected ? 'primary.main' : 'text.primary',
                                        backgroundColor: selected ? 'primary.lighter' : 'transparent',
                                        '&:hover': {
                                            backgroundColor: selected ? 'primary.light' : 'action.hover',
                                        },
                                    }}
                                >
                                    <ListItemIcon sx={{ color: 'inherit' }}>{icon}</ListItemIcon>
                                    <ListItemText
                                        primary={label}
                                        primaryTypographyProps={{
                                            fontWeight: selected ? 'bold' : 'normal',
                                        }}
                                    />
                                </ListItemButton>
                            </ListItem>
                        );
                    })}
                </List>
            </Drawer>

            {/* Main Content */}
            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    p: 3,
                }}
            >
                <Toolbar />
                {children}
            </Box>
        </Box>
    );
}
