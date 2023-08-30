import { Injectable } from '@angular/core';
import { File } from '../model/File';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SecondhandService {

  constructor(private http: HttpClient) { }

  postForm(file: File): Observable<any> {
    const httpHeaders = {
      'Content-Type': 'multipart/form-data',
      'Accept': 'application/json'
    }
    return this.http.post<any>("/", file, {headers: httpHeaders})
  }

}
