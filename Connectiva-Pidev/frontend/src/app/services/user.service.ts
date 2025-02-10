// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:9090/api/users';

  constructor(private http: HttpClient) { }

  createUser(user: any): Observable<any> {
    return this.http.post(this.apiUrl, user);
  }


  getMatches(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/matches/${userId}`);
  }
}