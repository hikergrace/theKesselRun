import { CartItem } from './models/cart-item';
import { Cart } from './models/cart';
import { Injectable } from '@angular/core';
import { User } from './models/user';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  // private url = 'http://localhost:8080/api/carts';
  private baseUrl = '/swREST/';
  private url = this.baseUrl + 'api/carts';
  private cartItemsUrl = this.baseUrl + 'api/cartitems';
  private cart = new Cart();

  index() {
    return this.http.get<Cart[]>(this.url)
        .pipe(
            catchError((err: any) => {
              console.log(err);
              return throwError('KABOOM');
            })
        );
  }

  show(id) {
    return this.http.get<Cart>(this.url + '/' + id)
        .pipe(
            catchError((err: any) => {
              console.log(err);
              return throwError('KABOOM');
            })
        );
  }

  showByUsername(username) {
    return this.http.get<Cart>(this.url + '/' + username)
        .pipe(
            catchError((err: any) => {
              console.log(err);
              return throwError('KABOOM');
            })
        );
  }

  getCartItems(userId) {
    return this.http.get<CartItem[]>(this.cartItemsUrl + '/' + userId)
        .pipe(
            catchError((err: any) => {
              console.log(err);
              return throwError('KABOOM');
            })
        );
  }
  removeCartItem(cartItem) {
    return this.http.delete<void>(this.cartItemsUrl + '/' + cartItem.id)
    .pipe(
        catchError((err: any) => {
          console.log(err);
          return throwError('KABOOM');
        })
    );
  }

  checkoutCart(userId: number, cartItem: CartItem) {
    console.log('in cart service checkout cart');

      return this.http.put<CartItem>( this.cartItemsUrl + '/checkout/' + userId, cartItem)
      .pipe(
          catchError((err: any) => {
            console.log(err);
            return throwError('KABOOM');
          })
      );


  }

  update(cart) {
    // set the local auth header token
    const localUn = localStorage.getItem('username');
    const token = this.authServ.getToken();
    const headers = new HttpHeaders()
        .set('Authorization', `Basic ${token}`);
    if (!this.authServ.checkLogin()) {
      this.router.navigateByUrl('/login');
    }
    console.log('in update method in service');
    return this.http.put<User>(this.url + '/' + cart.id, cart, {headers})
        .pipe(
          catchError((err: any) => {
            console.log(err);
            return throwError('KABOOM');
          })
        );
  }

  destroy(id) {
    return this.http.delete(this.url + '/' + id)
        .pipe(
          catchError((err: any) => {
            console.log(err);
            return throwError('KABOOM');
          })
        );
  }
  constructor(private authServ: AuthService, private http: HttpClient, private router: Router) { }
}
