import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class ConfigService {
  public baseUrl: string = 'http://localhost:9000';
  constructor(private http: HttpClient) {
    // This service can now make HTTP requests via `this.http`.
  }
}
