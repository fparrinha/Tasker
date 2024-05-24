import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TaskerComponent } from './tasker/components/tasker/tasker.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TaskerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'todo-angular';
}
