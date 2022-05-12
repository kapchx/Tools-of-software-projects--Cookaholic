export interface CookaholiUser{
    id : number;
    name : string;
    username : string;
    password : string;
    email : string;
    userTitle : UserTitle;
    phone : string;
    imageUrl : string;
    userCode : string; 
}

export enum UserTitle {
    ROLE_GUEST, ROLE_USER, ROLE_ADMIN
}