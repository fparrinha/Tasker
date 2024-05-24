import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TaskerComponent } from './tasker/components/tasker/tasker.component';
import { NavbarComponent } from './core/components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'todo-angular';
}
