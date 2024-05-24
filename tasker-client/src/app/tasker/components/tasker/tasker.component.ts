import { Component, Input, OnInit } from '@angular/core';
import { TaskerInputComponent } from '../tasker-input/tasker-input.component';
import { TaskComponent } from '../task/task.component';
import { CommonModule } from '@angular/common';
import { DeleteRequest, GetRequest, NO_CONNECTION_TO_SERVER_MSG, PostRequest, PutRequest } from '../../../core/Networking';
import { isStringEmpty } from '../../../core/Utils';
import { EmptyTaskDescription } from '../constants';

@Component({
  selector: 'app-tasker',
  standalone: true,
  imports: [TaskComponent, TaskerInputComponent, CommonModule],
  templateUrl: './tasker.component.html',
  styleUrl: './tasker.component.css'
})
export class TaskerComponent implements OnInit {
  @Input() errorMessage: string;
  public tasks : Array<{id: string, description: string, priority: number}>;

  constructor() {
    this.tasks = [];
    this.errorMessage = "";
  }

  ngOnInit(): void {
    this.onGetTasks();
  }

  /** Events */

  async onGetTasks(): Promise<void>  {
    try {
      const request = await GetRequest("tasker/tasks");
      const data = await request.json();

      if (!request.ok) {
        this.errorMessage = data.message;
        return;
      }

      this.tasks = data.tasks;
    } catch (error) {
      this.errorMessage = NO_CONNECTION_TO_SERVER_MSG;
    }
  }
  
  async onAddTask (description: string, priority: number): Promise<void>  {
    try {
      const request = await PostRequest("tasker/add", {
          description: isStringEmpty(description) ? EmptyTaskDescription : description,
          priority: priority
      });
      const data = await request.json();

      if (!request.ok) {
        this.errorMessage = data.message;
        return;
      }

      this.tasks = data.tasks;
    } catch (error) {
      this.errorMessage = NO_CONNECTION_TO_SERVER_MSG;
    }
  }

  async onDeleteTask(id: string): Promise<void>  {
    try {
      const request = await DeleteRequest(`tasker/delete?id=${id}`);
      const data = await request.json();

      if (!request.ok) {
        this.errorMessage = data.message;
        return;
      }
      
      this.tasks = data.tasks;
    } catch (error) {
      this.errorMessage = NO_CONNECTION_TO_SERVER_MSG;
    }
  }

  async onUpdateTask(id: string, description: string, priority: number): Promise<void> {
    try {
      const request = await PutRequest(`tasker/update`, {
          id: id,
          description: description,
          priority: priority
      });
      const data = await request.json();

      if (!request.ok) {
        this.errorMessage = data.message;
        return;
      }
      
      this.tasks = data.tasks;
    } catch (error) {
      this.errorMessage = NO_CONNECTION_TO_SERVER_MSG;
    }
  }
}
