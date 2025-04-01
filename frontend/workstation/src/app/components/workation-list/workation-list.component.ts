import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Workation, RiskLevel } from '../../models/workation.model';
import { WorkationService } from '../../services/workation.service';

@Component({
  selector: 'app-workation-list',
  standalone: true,
  imports: [CommonModule, DatePipe, HttpClientModule],
  templateUrl: './workation-list.component.html',
  styleUrls: ['./workation-list.component.css']
})
export class WorkationListComponent implements OnInit {
  workations: Workation[] = [];
  sortColumn: string = 'employee';
  sortDirection: string = 'asc';
  
  constructor(private workationService: WorkationService) {}

  ngOnInit(): void {
    this.loadWorkations();
  }

  loadWorkations(): void {
    this.workationService.getWorkations(this.sortColumn, this.sortDirection)
      .subscribe(data => {
        console.log('API Response:', data);
        this.workations = data;
      });
  }

  sort(column: string): void {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
    this.loadWorkations();
  }

  getRiskClass(risk: RiskLevel): string {
    switch (risk) {
      case RiskLevel.HIGH:
        return 'high';
      case RiskLevel.LOW:
        return 'low';
      case RiskLevel.NO:
        return 'no';
      default:
        return '';
    }
  }

  getRiskLabel(risk: RiskLevel): string {
    switch (risk) {
      case RiskLevel.HIGH:
        return 'High';
      case RiskLevel.LOW:
        return 'No';
      case RiskLevel.NO:
        return 'No';
      default:
        return '';
    }
  }

  getCountryFlag(countryCode: string): string {
    // Map country names to ISO country codes
    const countryCodeMap: {[key: string]: string} = {
      'Germany': 'de',
      'Belgium': 'be',
      'Ukraine': 'ua',
      'Spain': 'es',
      'Greece': 'gr',
      'India': 'in',
      'United States': 'us',
      'France': 'fr',
      'UK': 'gb',
      'Italy': 'it',
      'Portugal': 'pt',
      'Netherlands': 'nl',
      'Sweden': 'se',
      'Norway': 'no',
      'Denmark': 'dk',
      'Finland': 'fi'
    };
    
    const code = countryCodeMap[countryCode] || 'unknown';
    return `https://flagcdn.com/w20/${code.toLowerCase()}.png`;
  }

  formatDate(dateString: string): string {
    // If date is already in DD/MM/YYYY format, return it as is
    if (dateString?.includes('/')) {
      return dateString;
    }
    
    try {
      // First try standard date parsing
      let date = new Date(dateString);
      
      // If that didn't work and we have a date in YYYY-MM-DD format
      if (isNaN(date.getTime()) && dateString?.includes('-')) {
        const parts = dateString.split('-');
        if (parts.length === 3) {
          // Parse YYYY-MM-DD format manually
          date = new Date(+parts[0], +parts[1] - 1, +parts[2]);
        }
      }
      
      if (isNaN(date.getTime())) {
        console.warn('Could not parse date:', dateString);
        return 'Invalid Date';
      }
      
      // Format as DD/MM/YYYY
      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const year = date.getFullYear();
      
      return `${day}/${month}/${year}`;
    } catch (error) {
      console.error('Error formatting date:', dateString, error);
      return 'Invalid Date';
    }
  }
} 