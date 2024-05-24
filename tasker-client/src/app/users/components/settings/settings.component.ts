import { Component } from '@angular/core';
import { FetchingUserDataMessage } from '../constants';
import { CommonModule } from '@angular/common';
import { GetRequest } from '../../../core/Networking';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent {
  public username: string;
  public email: string;
  public firstName: string;
  public lastName: string;

  constructor() {
    this.username = FetchingUserDataMessage;
    this.email = FetchingUserDataMessage;
    this.firstName = FetchingUserDataMessage;
    this.lastName = FetchingUserDataMessage;
  }

  ngOnInit(): void {
    
  }

  public async requestUserData(): Promise<void> {
    const request = await GetRequest("/users/");
  }
}
