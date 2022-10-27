import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserStats} from "../shared/model/user.stats";
import {Registration} from "../shared/model/registration";
import {Service} from "../shared/model/service";
import {Carwash} from "../shared/model/carwash";
import {LoginResponse} from "../shared/model/login.response";
import {Router} from "@angular/router";
import {AppConstants} from "../../app.module";
import {Order} from "../shared/model/order";

@Component({
  selector: 'app-personal-account-admin',
  templateUrl: './personal-account-admin.component.html',
  styleUrls: ['./personal-account-admin.component.css']
})
export class PersonalAccountAdminComponent implements OnInit {

  currentCarWash: string = 'carwash_common';
  currentEmployees: Registration[] = [];
  currentUsers: UserStats[] = [];
  currentOrders: Order[] = [];
  employeeOrderMap: Map<Order, Registration> = new Map<Order, Registration>();
  carWashes: Carwash[] = [];
  orders: Order[] = [];
  employeeAdd: Registration[] = [];
  serviceAdd: Service[] = [];
  employeeLocation!: string;
  serviceLocation!: string;

  changeServId!: bigint;
  changeEmplId!: bigint;

  formEmpl!: FormGroup;
  formServ!: FormGroup;
  formCarWash!: FormGroup;
  formChangeEmpl!: FormGroup;
  formChangeServ!: FormGroup;

  private baseUrl = AppConstants.baseURL;

  numberOrders: number = 0;
  avgGrade: string = '0';
  returnAbility: number = 0;

  httpOptions = {
    headers: new HttpHeaders(
      {
        'Content-Type': 'application/json',
        // @ts-ignore
        'Authorization': sessionStorage.getItem('user') != null ? JSON.parse(sessionStorage.getItem('user')).token : ''
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
    if (obj) {
      this.loggedUser = obj;
    }
    return obj != null;
  }

  ngOnInit() {
    if (!this.checkLogin()) {
      this.router.navigate(['']);
    }
    this.http.get<Carwash[]>(`${this.baseUrl}/api/admin/getAllCarWashes`, this.httpOptions).subscribe((data: Carwash[]) => {
      this.carWashes = data;
    });
    this.http.get<Service[]>(`${this.baseUrl}/api/admin/getAllServices`, this.httpOptions).subscribe((data: Service[]) => {
      this.serviceAdd = data;
    });
    this.http.get<Registration[]>(`${this.baseUrl}/api/admin/getAllEmployees`, this.httpOptions).subscribe((data: Registration[]) => {
      this.employeeAdd = data;
    });
    this.http.get<Order[]>(`${this.baseUrl}/api/admin/getAllOrders`, this.httpOptions).subscribe((data: Order[]) => {
      this.orders = data;
    });
    this.formEmpl = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      name: new FormControl('', [Validators.required]),
      carwash: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required, Validators.minLength(10)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
    this.formCarWash = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required, Validators.minLength(10)]),
      address: new FormControl('', [Validators.required])
    });
    this.formServ = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
      carwash: new FormControl('', [Validators.required])
    });
    this.formChangeEmpl = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      name: new FormControl('', [Validators.required]),
      carwash: new FormControl('', [Validators.required]),
      phone: new FormControl('', [Validators.required, Validators.minLength(10)]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
    this.formChangeServ = this.formBuilder.group({
      name: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
      carwash: new FormControl('', [Validators.required])
    });
  }

  clickCarWash(name: string, type: string) {
    this.currentCarWash = name + type;
    if (name != 'carwash') {
      this.getCurrentEmployees(name);
      this.getCurrentUsers(name);
      this.getCurrentOrders(name);
    }
  }

  getCurrentOrders(location: string) {
    this.currentOrders = this.orders.filter((e) => {
      return e.carWash.location === location;
    });
  }

  getCurrentEmployees(location: string) {
    this.http.get<Registration[]>(`${this.baseUrl}/api/admin/getAllEmployeesByCarWash?location=${location}`, this.httpOptions).subscribe((data: Registration[]) => {
      this.currentEmployees = data;
    });
  }

  getCurrentUsers(location: string) {
    this.http.get<UserStats[]>(`${this.baseUrl}/api/admin/getCustomerStats?carWashLocation=${location}`, this.httpOptions).subscribe((data: UserStats[]) => {
      this.currentUsers = data;
    });
  }

  getStatsEmployee(location: string, event: Event) {
    let carwash: string = location.substring(0, location.indexOf('_'));
    let name: string = (event.target as HTMLOptionElement).value;
    if (name == 'Выберите') {
      return;
    }
    this.http.get<number>(`${this.baseUrl}/api/admin/getNumberEmployeeOrders?employeeName=${name}&carWashLocation=${carwash}&start=01-01-2022&end=31-12-2022`, this.httpOptions).subscribe((data: number) => {
      this.numberOrders = data;
    });

    this.http.get<number>(`${this.baseUrl}/api/admin/getAvgGrade?employeeName=${name}&carWashLocation=${carwash}&start=01-01-2022&end=31-12-2022`, this.httpOptions).subscribe((data: number) => {
      let lengthGrade: number = data.toString().length;
      this.avgGrade = data.toPrecision(lengthGrade>3?3:1)
    });

    this.http.get<number>(`${this.baseUrl}/api/admin/getReturnAbility?employeeName=${name}&carWashLocation=${carwash}&start=01-01-2022&end=31-12-2022`, this.httpOptions).subscribe((data: number) => {
      this.returnAbility = data;
    });
  }

  saveCarWash() {
    const {name, phone, address} = this.formCarWash.value;
    const carwash: Carwash = new Carwash()

    carwash.phone = '7' + phone;
    carwash.name = name;
    carwash.location = address;

    let request = {"name": name, "phone": '7' + phone, "location": address, "employees": [], "services": []};
    this.formCarWash.reset();
    this.http.post<boolean>(`${this.baseUrl}/api/admin/createCarWash`, JSON.stringify(request), this.httpOptions).subscribe((data: boolean) => {
      if (data) {
        this.carWashes.push(carwash);
      }
    });
  }

  saveService() {
    const {name, price} = this.formServ.value;
    const carwash = this.serviceLocation;
    const service: Service = new Service();

    service.name = name;
    service.price = price;
    service.carWashLocation = carwash;

    let request = {"name": name, "price": Number.parseInt(price), "carWashLocation": carwash};
    this.formServ.reset();
    this.formServ.controls['carwash'].setValue(undefined)
    this.http.post<boolean>(`${this.baseUrl}/api/admin/addServices`, JSON.stringify(request), this.httpOptions).subscribe((data: boolean) => {
      if (data) {
        this.serviceAdd.push(service);
      }
    });
  }

  saveEmployee() {
    const {name, email, password, phone} = this.formEmpl.value;
    const carwash = this.employeeLocation;
    const employee: Registration = new Registration()

    employee.name = name;
    employee.email = email;
    employee.phone = '7' + phone;
    employee.password = password;
    employee.carWashLocation = carwash;

    let request = {"name": name, "phone": '7' + phone, "email": email, "password": password, "carWashLocation": carwash};
    this.formEmpl.reset();
    this.formEmpl.controls['carwash'].setValue(undefined)
    this.http.post<boolean>(`${this.baseUrl}/api/admin/createEmployee`, JSON.stringify(request), this.httpOptions).subscribe((data: boolean) => {
      if (data) {
        this.employeeAdd.push(employee);
      }
    });
  }

  updateLocationService(event: Event) {
    this.serviceLocation = (event.target as HTMLOptionElement).value;
  }

  updateLocationEmployee(event: Event) {
    this.employeeLocation = (event.target as HTMLOptionElement).value;
  }

  updateEmployeeOrderMap(order: Order, event: Event) {
    let emplName: string = (event.target as HTMLOptionElement).value;
    let employee: Registration | undefined = this.currentEmployees.find((e) => e.name == emplName);
    if (employee) {
      this.employeeOrderMap.set(order, employee);
    }
  }

  updateOrderEmployee(order: Order) {
    let employee: Registration | undefined = this.employeeOrderMap.get(order);
    this.currentOrders.forEach((e) => {
      if (e.id == order.id) {
        if (employee) {
          order.employee = employee;
        }
      }
    });

    this.http.put<boolean>(`${this.baseUrl}/api/admin/acceptOrder?id=${order.employee.id}`, JSON.stringify(order), this.httpOptions).subscribe(() => {
    });
  }

  updateOrderStatus(order: Order) {
    this.currentOrders.forEach((e) => {
      if (e.id == order.id) {
        order.status = true;
      }
    });

    this.http.put<boolean>(`${this.baseUrl}/api/admin/finishOrder?id=${order.id}`, this.httpOptions).subscribe(() => {
    });
  }

  logout() {
    sessionStorage.clear();
  }

  changeEmployeeId(id: bigint) {
    this.changeEmplId = id;
  }

  changeServiceId(id: bigint) {
    this.changeServId = id;
  }

  deleteEmployee() {
    this.http.delete(`${this.baseUrl}/api/admin/deleteEmployee?id=${this.changeEmplId}`, this.httpOptions).subscribe(()=>{});
    this.employeeAdd = this.employeeAdd.filter(e => e.id != this.changeEmplId);
  }

  deleteService() {
    this.http.delete(`${this.baseUrl}/api/admin/deleteService?id=${this.changeServId}`, this.httpOptions).subscribe(()=>{});
    this.serviceAdd = this.serviceAdd.filter(e => e.id != this.changeServId);
  }

  updateEmployee() {
    const {name, email, password, phone} = this.formChangeEmpl.value;
    const carwash = this.employeeLocation;
    const employee: Registration = new Registration()

    employee.name = name;
    employee.email = email;
    employee.phone = '7' + phone;
    employee.password = password;
    employee.carWashLocation = carwash;

    let request = {"id": this.changeEmplId, "name": name, "phone": '7' + phone, "email": email, "password": password, "carWashLocation": carwash};
    this.formChangeEmpl.reset();
    this.formChangeEmpl.controls['carwash'].setValue(undefined)
    this.http.put<boolean>(`${this.baseUrl}/api/admin/updateEmployee`, JSON.stringify(request), this.httpOptions).subscribe((data: boolean) => {
      if (data) {
        this.employeeAdd.forEach(e => {
          if (e.id == this.changeEmplId) {
            e.name = name;
            e.password = password;
            e.phone = '7' + phone;
            e.email = email;
            e.carWashLocation = carwash;
          }
        })
      }
    });
  }

  updateService() {
    const {name, price} = this.formChangeServ.value;
    const carwash = this.serviceLocation;
    const service: Service = new Service();

    service.name = name;
    service.price = price;
    service.carWashLocation = carwash;

    let request = {"id": this.changeServId, "name": name, "price": Number.parseInt(price), "carWashLocation": carwash};
    this.formChangeServ.reset();
    this.formChangeServ.controls['carwash'].setValue(undefined)
    this.http.put<boolean>(`${this.baseUrl}/api/admin/updateService`, JSON.stringify(request), this.httpOptions).subscribe((data: boolean) => {
      if (data) {
        this.serviceAdd.forEach(e => {
          if (e.id == this.changeServId) {
            e.name = name;
            e.price = price;
            e.carWashLocation = carwash;
          }
        })
      }
    });
  }
}
