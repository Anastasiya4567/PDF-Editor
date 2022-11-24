import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ACCESS_TOKEN, API_BASE_URL} from "../../constants/app-constants.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {User} from "../../models/User";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient,
              private cookieService: CookieService) {
  }

  // TODO
  getProfileDetails(): Observable<User> {
    const headers = new HttpHeaders();
    const authHeaders = headers.append('Authorization', 'Bearer ' + this.cookieService.get(ACCESS_TOKEN));
    return this.http.get<User>(API_BASE_URL + '/user/me', {headers: authHeaders});
  }
}
