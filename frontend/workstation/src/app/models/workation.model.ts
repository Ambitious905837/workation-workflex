export interface Workation {
  workationId: string;
  employee: string;
  origin: string;
  destination: string;
  start: string;
  end: string;
  workingDays: number;
  risk: RiskLevel;
}

export enum RiskLevel {
  HIGH = 'HIGH',
  LOW = 'LOW',
  NO = 'NO'
} 