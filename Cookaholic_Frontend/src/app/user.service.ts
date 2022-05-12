import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CookaholiUser } from "./cookaholicUser";


@Injectable({
    providedIn : 'root'
})
export class UserService {
    private apiServerUrl = '';

    constructor(private http : HttpClient){}

    public getUser(id : number): Observable<CookaholiUser[]>{
        return this.http.get<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/find/${id}`)
    } 

    public getUsers(): Observable<CookaholiUser[]>{
        return this.http.get<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/find/all`)
    } 

    public addUser(cookaholicUser: CookaholiUser): Observable<CookaholiUser[]>{
        return this.http.post<CookaholiUser>(`${this.apiServerUrl}/CookaholicUser/register/admin`, cookaholicUser)
    } 

    public addAdmin(cookaholicUser: CookaholiUser): Observable<CookaholiUser[]>{
        return this.http.post<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/register/user`, cookaholicUser)
    } 

    public addGuest(cookaholicUser: CookaholiUser): Observable<CookaholiUser[]>{
        return this.http.post<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/register/guest`, cookaholicUser)
    } 

    public update(): Observable<CookaholiUser[]>{
        return this.http.put<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/update`)
    } 

    public delete(): Observable<CookaholiUser[]>{
        return this.http.delete<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/delete`)
    } 

    public deleteById(id : number): Observable<CookaholiUser[]>{
        return this.http.delete<CookaholiUser[]>(`${this.apiServerUrl}/CookaholicUser/delete/${id}`)
    } 
}