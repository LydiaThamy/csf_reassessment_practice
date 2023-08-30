import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, catchError, tap, throwError } from 'rxjs';
import { Posting } from '../model/Posting';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SecondhandService {

  constructor(private http: HttpClient) { }


  postingSaved: boolean = false

  savePosting(posting: Posting): Observable<Posting> {
    const formData: FormData = new FormData()
    formData.append('name', posting.name)
    formData.append('email', posting.email)
    formData.append('phone', posting.phone)
    formData.append('title', posting.title)
    formData.append('description', posting.description)
    formData.append('imageFile', posting.image)

    return this.http.post<Posting>("/", formData)
  }

  getPosting(postingId: string): Observable<Posting> {
    return this.http.get<Posting>("/" + postingId)
  }


  confirmPost(posting: Posting): Observable<any> {
    return this.http.put<any>("/" + posting.postingId, posting)
  }

}
