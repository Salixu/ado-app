import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('File', file);
     // ReportProgress set to true to exposes progress events, its very expensive
    const req = new HttpRequest('POST', `${this.baseUrl}/extractLabels`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }


}
