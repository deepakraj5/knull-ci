type Stage = {
  id: number;
  buildId: number;
  name: string;
  status: 'PENDING' | 'RUNNING' | 'SUCCESS' | 'FAILED';
  command: string;
  createdAt: string;
  updatedAt: string;
};

const mockStages: Stage[] = [
  {
    id: 1,
    buildId: 1,
    name: 'Install Dependencies',
    status: 'SUCCESS',
    command: 'npm install',
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
  {
    id: 2,
    buildId: 1,
    name: 'Run Tests',
    status: 'FAILED',
    command: 'npm test',
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
  {
    id: 3,
    buildId: 1,
    name: 'Build Project',
    status: 'PENDING',
    command: 'npm run build',
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
  {
    id: 4,
    buildId: 1,
    name: 'Build Project',
    status: 'RUNNING',
    command: 'npm run build',
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
  {
    id: 4,
    buildId: 1,
    name: 'Build Project',
    status: 'RUNNING',
    command: 'npm run build',
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  },
  {
    id: 4,
    buildId: 1,
    name: 'Build Project',
    status: 'RUNNING',
    command: 'npm run build',
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  }
];

export function getMockStages(buildId: number): Stage[] {
  // Filter by build ID (just in case)
  return mockStages.filter((s) => s.buildId === buildId);
}
