import {Injectable} from '@angular/core';
import {BehaviorSubject, map, Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {LoginData} from "../../models/LoginData";

import {ACCESS_TOKEN, API_BASE_URL} from '../../constants/app-constants.component';
import {MessageResponse} from "../../models/MessageResponse";
import {Account} from "../../models/Account";
import {UserDetails} from "../../models/UserDetails";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient) {
  }

  register(account: Account): Observable<MessageResponse> {
    const headers = new HttpHeaders();
    return this.http.post<MessageResponse>(API_BASE_URL + '/auth/register', account, {headers: headers});
  }

  login(loginData: LoginData): Observable<HttpResponse<any>> {
    const headers = new HttpHeaders();
    return this.http.post<any>(API_BASE_URL + '/auth/login', loginData, {headers: headers})
      .pipe(map((response: any) => {
        if (response.success) {
          localStorage.setItem(ACCESS_TOKEN, response.accessToken);
        }
        this.isLoggedIn.next(response.success);
        return response;
      }));
  }

  get getIsLoggedIn(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

  public setIsLoggedIn(isLoggedIn: boolean) {
    this.isLoggedIn.next(isLoggedIn);
  }

  setHeaders() {
    const headers = new HttpHeaders();
    return headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN));
  }

  getProfileDetails(): Observable<UserDetails> {
    return this.http.get<any>(API_BASE_URL + '/user/me', {headers: this.setHeaders()});
  }

  deactivateAccount(): Observable<HttpResponse<MessageResponse>> {
    return this.http.post<HttpResponse<MessageResponse>>(API_BASE_URL + '/deactivateAccount', {}, {headers: this.setHeaders()});
  }

}
