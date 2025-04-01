import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { WorkationService } from './workation.service';
import { environment } from '../../environments/environment';
import { Workation } from '../models/workation.model';

describe('WorkationService', () => {
  let service: WorkationService;
  let httpMock: HttpTestingController;

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

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [WorkationService]
    });
    
    service = TestBed.inject(WorkationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch workations', () => {
    service.getWorkations().subscribe(workations => {
      expect(workations.length).toBe(5);
      expect(workations).toEqual(mockWorkations as Workation[]);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/workflex/workation`);
    expect(req.request.method).toBe('GET');
    req.flush(mockWorkations);
  });


}); 