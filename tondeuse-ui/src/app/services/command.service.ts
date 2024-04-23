import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigService } from './config-service';

@Injectable({
  providedIn: 'root'
})
export class CommandService {

  constructor(private http: HttpClient, private configService: ConfigService) { }

  submitForm(body: any) {
    const url = `${this.configService.baseUrl}/api/tondeuse/command`; // url complète
    return this.http.post(url, body);
  }
}
