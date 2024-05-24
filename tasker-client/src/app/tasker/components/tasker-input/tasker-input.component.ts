import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { TaskerButtonComponent } from '../tasker-button/tasker-button.component';
import { PrioritySelectorComponent } from '../priority-selector/priority-selector.component';
import { CommonModule } from '@angular/common';
import { IconDefinition, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ButtonColors, Priority, TaskInputColor } from '../constants';


@Component({
  selector: 'app-tasker-input',
  standalone: true,
  imports: [TaskerButtonComponent, PrioritySelectorComponent, CommonModule, FontAwesomeModule],
  templateUrl: './tasker-input.component.html',
  styleUrl: './tasker-input.component.css'
})
export class TaskerInputComponent {
  @Input() description: string;
  @Input() priority: number;
  @Output() onSubmit: EventEmitter<{description: string, priority: number}>;
  @ViewChild('input') inputComponent!: ElementRef<HTMLInputElement>;
  @ViewChild('prioritySelector') priorityComponent!: PrioritySelectorComponent;

  public buttonColor = ButtonColors.SUCCESS;
  public style =  {
    color: TaskInputColor.FOCUSED
  };
  public icons : {faPlus: IconDefinition} = {
    faPlus: faPlus
  };

  
  constructor() {
    this.description = "";
    this.priority = Priority.LOW;
    this.onSubmit = new EventEmitter<{description: string, priority: number}>(); 
  }

  onInput(event: any) {
    this.description = event.target.value;
  }

  setPriority(priority: number) {
    this.priority = priority;
  }

  resetState() {
    this.inputComponent.nativeElement.value = ""
    this.priorityComponent.reset();
  }

  // Events

  onSubmitAddTask(event: any): void {
    this.onSubmit.emit({description: this.description, priority: this.priority});
    this.resetState();
  }
}
