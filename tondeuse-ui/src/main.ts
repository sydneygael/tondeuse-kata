import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { CommandInputComponent } from './app/command-input/command-input.component';

bootstrapApplication(CommandInputComponent, appConfig)
  .catch((err) => console.error(err));
