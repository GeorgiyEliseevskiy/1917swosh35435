import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, RadioControlValueAccessor, Validators} from "@angular/forms";
import {Order} from "../shared/model/order";
import {AppConstants} from "../../app.module";
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoginResponse} from "../shared/model/login.response";

@Component({
  selector: 'app-personal-account-client',
  templateUrl: './personal-account-client.component.html',
  styleUrls: ['./personal-account-client.component.css']
})
export class PersonalAccountClientComponent implements OnInit {

  formReview!: FormGroup;
  orders: Order[] = [];
  currentOrder!: Order;
  private baseUrl = AppConstants.baseURL;
  grade: number = 1;

  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
      }
    )
  }

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private http: HttpClient) {

  }

  loggedUser!: LoginResponse;

  checkLogin() {
    let json: string | null = sessionStorage.getItem("user");
    let obj: LoginResponse | null = json != null ? JSON.parse(json) : null;

    if (obj != null) {
      this.loggedUser = obj;
    } else {
      this.loggedUser = new LoginResponse();
      this.loggedUser.email = '';
    }
  }

  ngOnInit(): void {
    this.checkLogin();

    this.http.get<Order[]>(`${this.baseUrl}/api/customer/getUserOrders?email=${this.loggedUser.email}`, this.httpOptions).subscribe((data: Order[]) => {
      this.orders = data;
    });

    this.formReview = this.formBuilder.group({
      text: new FormControl('', [Validators.required]),
    });
  }

  getGrade(grade:number){
    this.grade = grade;
  }

  saveReview() {
    const text: string = this.formReview.controls['text'].value;

    this.orders.forEach((e) => {
      if (e.id == this.currentOrder.id) {
        e.grade = this.grade;
        e.text = text;
      }
    })
    this.http.put<boolean>(`${this.baseUrl}/api/customer/gradeOrder?id=${this.currentOrder.id}&grade=${this.grade}&text=${text}`, this.httpOptions).subscribe(() => {
    });
    this.grade = 1;
  }

  updateCurrentOrder(id: bigint) {
    let order: Order | undefined = this.orders.find((e) => e.id == id);
    if (order) {
      this.currentOrder = order;
    }
  }

  logout() {
    sessionStorage.clear();
  }

}
