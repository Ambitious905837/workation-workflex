import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Workation } from '../models/workation.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WorkationService {
  private apiUrl = `${environment.apiUrl}/workflex/workation`;

  constructor(private http: HttpClient) { }

  getWorkations(sortBy?: string, sortDirection?: string): Observable<Workation[]> {
    let url = this.apiUrl;
    
    if (sortBy && sortDirection) {
      url += `?sortBy=${sortBy}&sortDirection=${sortDirection}`;
    }
    
    return this.http.get<Workation[]>(url);
  }
} 