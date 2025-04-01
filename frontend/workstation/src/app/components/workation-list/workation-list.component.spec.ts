import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { CommonModule } from '@angular/common';

import { WorkationListComponent } from './workation-list.component';
import { WorkationService } from '../../services/workation.service';
import { RiskLevel, Workation } from '../../models/workation.model';

describe('WorkationListComponent', () => {
  let component: WorkationListComponent;
  let fixture: ComponentFixture<WorkationListComponent>;
  let workationServiceSpy: jasmine.SpyObj<WorkationService>;

  // Mock data that matches the actual CSV file
  const mockWorkations = [
    {
      workationId: 'w1',
      employee: 'Steffen Jacobs',
      origin: 'Germany',
      destination: 'United States',
      start: '2024-01-02',
      end: '2024-12-31',
      workingDays: 65,
      risk: 'HIGH'
    },
    {
      workationId: 'w2',
      employee: 'Steffen Jacobs',
      origin: 'Germany',
      destination: 'Ukraine',
      start: '2023-04-23',
      end: '2023-04-30',
      workingDays: 1,
      risk: 'HIGH'
    },
    {
      workationId: 'w3',
      employee: 'Henry Duchamp',
      origin: 'Belgium',
      destination: 'Spain',
      start: '2022-09-01',
      end: '2023-03-01',
      workingDays: 131,
      risk: 'HIGH'
    },
    {
      workationId: 'w4',
      employee: 'Andre Fischer',
      origin: 'Germany',
      destination: 'Greece',
      start: '2023-05-22',
      end: '2023-06-30',
      workingDays: 50,
      risk: 'LOW'
    },
    {
      workationId: 'w5',
      employee: 'Ayushi Singh',
      origin: 'Germany',
      destination: 'India',
      start: '2023-03-13',
      end: '2023-04-30',
      workingDays: 35,
      risk: 'NO'
    }
  ];

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('WorkationService', ['getWorkations']);
    
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CommonModule,
        WorkationListComponent
      ],
      providers: [
        { provide: WorkationService, useValue: spy }
      ]
    }).compileComponents();

    workationServiceSpy = TestBed.inject(WorkationService) as jasmine.SpyObj<WorkationService>;
    workationServiceSpy.getWorkations.and.returnValue(of([...mockWorkations] as Workation[]));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkationListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch workations on init', () => {
    expect(workationServiceSpy.getWorkations).toHaveBeenCalled();
    expect(component.workations.length).toBe(5);
  });

}); 