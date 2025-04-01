import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { WorkationListComponent } from './components/workation-list/workation-list.component';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, WorkationListComponent, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Workations';
}
