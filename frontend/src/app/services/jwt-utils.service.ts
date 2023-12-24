import { Injectable } from '@angular/core';
import {jwtDecode, JwtPayload} from "jwt-decode";
import {DecodedToken} from "../models/DecodedToken";

@Injectable({
  providedIn: 'root'
})
export class JwtUtilsService {

  getUserEmail():string{
    let token = localStorage.getItem('token')
    if(token){
      let email = jwtDecode(token).sub
      return  email ? email : 'tokenDecodingError'
    } else return 'tokenDecodingError'
  }
  GetUserRoles(): string[]{
    let token = localStorage.getItem('token')
    if(token){
      return jwtDecode<DecodedToken>(token).roles
    } else return ['tokenDecodingError']
  }

  isTokenExpired(token:string): boolean{
    let expires = jwtDecode(token).exp;
    if(expires){
      return expires < Date.now() / 1000;
    }
    return true;
  }
}
