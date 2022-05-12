import { HttpClient } from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { CookaholiUser, UserTitle } from 'src/app/cookaholicUser';


export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse extends CookaholiUser {
  token: string;
}

const UserStorageKey = "user";
const TokenStorageKey = "token";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser: CookaholiUser | null = null;
  private user1!: CookaholiUser;

  get user(): CookaholiUser {
    if(!this.currentUser){
      this.currentUser = JSON.parse(
        sessionStorage.getItem(UserStorageKey) as string
      );
    }
    return this.currentUser as CookaholiUser;
  }


  private currentToken: string | null = null;

  get token(): string {
    if(!this.currentToken){
      this.currentToken = sessionStorage.getItem(TokenStorageKey)
    }
    return this.currentToken as string;
  }

  get isLoggedIn(): boolean {
    return !!this.token;
  }

  get isAdmin(): boolean {
    return this.user.userTitle === UserTitle.ROLE_ADMIN;
  }

  constructor(private httpClient: HttpClient){}

  async login(loginRequest: LoginRequest): Promise<void>{
    const user = await this.httpClient
    .post<LoginResponse>('http://localhost:8080/api/auth', loginRequest)
    .toPromise();
    
    
    this.setUser(user);
    console.log(this.isAdmin);
    this.user1 = {id: 1, name: this.user.name, username: this.user.username, password: this.user.password,  
      email: this.user.email, userTitle : this.user.userTitle,phone : this.user.phone, imageUrl : this.user.imageUrl, userCode : this.user.userCode };

  }

  logout(): void {
    this.setUser(null);
  }
  
  private setUser(user: LoginResponse | null): void {
      if (user) {
        sessionStorage.setItem(TokenStorageKey, user.token);
        sessionStorage.setItem(UserStorageKey, JSON.stringify(user));
        document.cookie = `${TokenStorageKey}=${user.token}`;
        document.cookie = `${UserStorageKey}=${JSON.stringify(user)}`;
      } else {
        sessionStorage.removeItem(TokenStorageKey);
        sessionStorage.removeItem(UserStorageKey);
      }
      this.currentUser = user;
      this.currentToken = user?.token || null;
  }

}
