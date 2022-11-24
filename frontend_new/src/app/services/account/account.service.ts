import {Injectable} from '@angular/core';
import {BehaviorSubject, map, Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {LoginData} from "../../models/LoginData";

import {API_BASE_URL, ACCESS_TOKEN} from '../../constants/app-constants.component';
import {MessageResponse} from "../../models/MessageResponse";
import {Account} from "../../models/Account";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private isLoggedIn = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private cookieService: CookieService) {
  }

  register(account: Account): Observable<MessageResponse> {
    const headers = new HttpHeaders();
    return this.http.post<MessageResponse>(API_BASE_URL + '/auth/register', account, {headers: headers });
  }

  login(loginData: LoginData): Observable<HttpResponse<any>> {
    const headers = new HttpHeaders();
    return this.http.post<any>(API_BASE_URL + '/auth/login', loginData, {headers: headers })
      .pipe(map((response: any) => {
        if (response.success) {
          this.isLoggedIn.next(true);
          this.cookieService.set(ACCESS_TOKEN, response.accessToken);
        } else {
          this.isLoggedIn.next(false);
        }
        return response;
      }));
  }

  get getIsLoggedIn(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

}
