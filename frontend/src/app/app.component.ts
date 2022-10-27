import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Service} from "./system/shared/model/service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AppConstants} from "./app.module";
import {Carwash} from "./system/shared/model/carwash";
import {User} from "./system/shared/model/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  formRecord!: FormGroup;
  date1!: Date;
  price: number = 0;
  clazz!: string;
  serviceRecord!: Service | undefined;
  carWashRecord!: Carwash | undefined;

  carWashes: Carwash[] = [];
  currentServices: Service[] = [];
  clazzes: string[] = ['Класс A', 'Класс B', 'Класс C', 'Класс D', 'Класс E', 'Класс F', 'Класс G', 'Класс H'];
  clazzMap: Map<string, number> = new Map<string, number>();

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

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {

  }

  ngOnInit() {
    this.clazzMap.set('Класс А', 0);
    this.clazzMap.set('Класс B', 0);
    this.clazzMap.set('Класс C', 5);
    this.clazzMap.set('Класс D', 5);
    this.clazzMap.set('Класс E', 5);
    this.clazzMap.set('Класс F', 10);
    this.clazzMap.set('Класс G', 10);
    this.clazzMap.set('Класс H', 15);

    this.http.get<Carwash[]>(`${this.baseUrl}/api/public/getAllCarWashes`).subscribe((data: Carwash[]) => {
      this.carWashes = data;
    })
    this.formRecord = this.formBuilder.group({
      phone: new FormControl('', [Validators.required, Validators.minLength(10)]),
      clazz: new FormControl('', [Validators.required]),
      service: new FormControl('', [Validators.required]),
      name: new FormControl('', [Validators.required]),
      agree: new FormControl('', [Validators.requiredTrue])
    });
    this.formRecord.controls['clazz'].setValue(undefined);
    this.formRecord.controls['service'].setValue(undefined);

    this.date1 = new Date();
  }

  updateCarWash(event: Event) {
    let location: string = (event.target as HTMLOptionElement).value;
    let carWash: Carwash | undefined = this.carWashes.find((e) => e.location === location);
    this.http.get<Service[]>(`${this.baseUrl}/api/public/getAllServicesByCarWash?location=${location}`).subscribe((data: Service[]) => {
      this.currentServices = data;
    })
    this.carWashRecord = carWash;

    this.formRecord.controls['clazz'].setValue(undefined);
    this.formRecord.controls['service'].setValue(undefined);
    this.price = 0;
  }

  updatePrice(event: Event) {
    let name: string = (event.target as HTMLOptionElement).value;
    let multiplier: number | undefined = this.clazzMap.get(this.clazz);
    let serv: Service | undefined = this.currentServices.find((e) => e.name === name);
    this.price = serv === undefined ? 0 : serv.price + Math.round(serv.price * (multiplier === undefined ? 0 : multiplier) / 100);
    this.serviceRecord = serv;
  }

  updateClazz(event: Event) {
    this.clazz = (event.target as HTMLOptionElement).value;
    let multiplier: number | undefined = this.clazzMap.get(this.clazz);
    let serv: Service | undefined = this.serviceRecord;
    this.price = serv === undefined ? 0 : serv.price + Math.round(serv.price * (multiplier === undefined ? 0 : multiplier) / 100);
  }

  saveRecord() {
    this.checkLogin();
    const {name, phone} = this.formRecord.value;

    let request = {
      "price": this.price,
      "carWash": this.carWashRecord,
      "service": this.serviceRecord,
      "user": this.loggedUserInfo != null ? this.loggedUserInfo : {'name': name, 'phone': '7' + phone}
    }
    this.http.post<boolean>(`${this.baseUrl}/api/customer/createOrder`, JSON.stringify(request), this.httpOptions).subscribe(() => {
    });
    this.formRecord.reset();
    this.formRecord.controls['clazz'].setValue(undefined);
    this.formRecord.controls['service'].setValue(undefined);
    this.price = 0;
    this.serviceRecord = undefined;
    this.valueControlsChanged = false;
  }

  loggedUserInfo!: User | null;
  valueControlsChanged: boolean = false;

  checkLogin() {
    let json: string | null = sessionStorage.getItem("user_info");
    let obj: User | null = json != null ? JSON.parse(json) : null;

    if (obj != null) {
      this.formRecord.controls['name'].setValue(obj.name);
      this.formRecord.controls['phone'].setValue(obj.phone);
      this.loggedUserInfo = obj;
      return true;
    } else {
      if (!this.valueControlsChanged) {
        this.formRecord.controls['name'].setValue(undefined);
        this.formRecord.controls['phone'].setValue(undefined);
        this.valueControlsChanged = true;
      }
      this.loggedUserInfo = null;
      return false;
    }
  }
}
