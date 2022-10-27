import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Service} from "../shared/model/service";
import {AppConstants} from "../../app.module";
import {Carwash} from "../shared/model/carwash";
import {Order} from "../shared/model/order";

@Component({
  selector: 'app-about-carwash',
  templateUrl: './about-carwash.component.html',
  styleUrls: ['./about-carwash.component.css']
})
export class AboutCarwashComponent implements OnInit {

  carWashes: Carwash[] = [];
  orders: Order[] = [];
  services: Service[] = [];
  private baseUrl = AppConstants.baseURL;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get<Order[]>(`${this.baseUrl}/api/public/getReviews?location=ул. Белинского 32a`).subscribe((data: Order[]) => {
      this.orders = data;
    });
    this.http.get<Carwash[]>(`${this.baseUrl}/api/public/getAllCarWashes`).subscribe((data: Carwash[]) => {
      this.carWashes = data;
      this.services = this.carWashes[0].services;
    })
  }

}
