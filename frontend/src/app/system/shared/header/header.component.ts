import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {LoginResponse} from "../model/login.response";
import {AppConstants} from "../../../app.module";
import {User} from "../model/user";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loginForm!: FormGroup;
  formRegistration!: FormGroup;
  currentUser!: User;

  public showCode: boolean = false;

  private baseUrl = AppConstants.baseURL;

  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        // @ts-ignore
        // 'Authorization': sessionStorage.getItem('user') != null ? JSON.parse(sessionStorage.getItem('user')).token : ''
      }
    )
  }

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              private http: HttpClient
  ) {

  }

  ngOnInit() {
    this.formRegistration = this.formBuilder.group({
      name: new FormControl('', [Validators.required, Validators.minLength(2)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      phone: new FormControl('', [Validators.required, Validators.minLength(10)]),
      confirmedPassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
      code: new FormControl('')
    });
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    })
  }

  pressBtn(id: string): void {
    document.getElementById(id)?.click();
  }

  login(): void {
    let email: string = this.loginForm.value.email;
    let request = {"email": email, "password": this.loginForm.value.password};
    this.http.post<LoginResponse>(`${this.baseUrl}/api/auth/login`, JSON.stringify(request), this.httpOptions).subscribe((data: LoginResponse) => {
      if (data.role == "CUSTOMER" || data.role == "EMPLOYEE") {
        this.router.navigate(["/personal-account-client"]);
      } else if (data.role == "ADMIN") {
        this.router.navigate(["/personal-account-admin"]);
      }
      sessionStorage.setItem("user", JSON.stringify(data));
    });
    this.loginForm.reset();
  }


  registration(): void {
    let request = {
      "email": this.formRegistration.value.email,
      "phone": '7' + this.formRegistration.value.phone,
      "password": this.formRegistration.value.password,
      "name": this.formRegistration.value.name
    };
    this.http.post<boolean>(`${this.baseUrl}/api/reg/createUser`, JSON.stringify(request), this.httpOptions).subscribe((data: boolean) => {
      this.showCode = data;
    })
  }

  confirmCode(): void {
    const {code, email} = this.formRegistration.value;

    this.http.put<boolean>(`${this.baseUrl}/api/reg/checkConfirmation?code=${code}&contact=${email}`, code).subscribe((data: boolean) => {
      if (!data) {
        alert("Введён неправильный код!");
      }
    })
    this.formRegistration.reset();
  }

  loggedUser!: LoginResponse | null;
  loggedStatus: boolean = false;

  checkLogin() {
    let json: string | null = sessionStorage.getItem("user");
    let obj: LoginResponse | null = json != null ? JSON.parse(json) : null;

    if (obj != null) {
      if (!this.loggedStatus) {
        this.http.get<User>(`${this.baseUrl}/api/customer/getUser?email=${obj.email}`, this.httpOptions).subscribe((data: User) => {
          this.currentUser = data;
          sessionStorage.setItem("user_info", JSON.stringify(data));
          this.loggedStatus = true;
        })
      }
      this.loggedUser = obj;
      return true;
    } else {
      this.loggedStatus = false;
      this.loggedUser = null;
      return false;
    }
  }

  personal() {
    if (this.loggedUser != null) {
      if (this.loggedUser.role == "CUSTOMER" || this.loggedUser.role == "EMPLOYEE") {
        this.router.navigate(["/personal-account-client"]);
      } else if (this.loggedUser.role == "ADMIN") {
        this.router.navigate(["/personal-account-admin"]);
      }
    }
  }
}
