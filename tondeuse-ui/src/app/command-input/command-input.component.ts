import { Component, Input } from '@angular/core';
import { CommandService } from '../services/command.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { OrientationEnum } from './orientation-enum';
import { Observable, Observer, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-command-input',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './command-input.component.html',
  styleUrl: './command-input.component.css'
})
export class CommandInputComponent {

  positionX: number = 5;
  positionY: number = 5;
  @Input() selectedCommands: Array<string> = [];
  orientation: OrientationEnum = OrientationEnum.NORTH;
  orientations: Array<OrientationEnum> = [OrientationEnum.NORTH,OrientationEnum.EAST,OrientationEnum.WEST,OrientationEnum.SOUTH];
  textResult: string = 'No result';
  textResultPipe = new Observable<void>(() => {
    this.resetCommands();
  });

  constructor(private commandSerice: CommandService) {
  }

  addCommand(command: string) {
    if (this.selectedCommands.length < 5) {
      this.selectedCommands.push(command);
    }
  }

  submitForm() {
    console.log('Form submitted!');
    const body = {
      commands: this.selectedCommands,
      tondeuse: {
        position: {
          positionX: this.positionX,
          positionY: this.positionY
        },
        orientation: this.orientation
      },
      surface: {
        positionInitial: {
          positionX: 0,
          positionY: 0,
        },
        width: 10, // Valeur par défaut pour la largeur de la surface
        height: 10
      }
    };
    console.log('body :',body);

    this.commandSerice.submitForm(body)
      .pipe(switchMap((response) => {
        this.textResult = response;
        return this.textResultPipe;
      })).subscribe();
  }

  resetCommands(): void {
    this.selectedCommands = [];
  }

}
