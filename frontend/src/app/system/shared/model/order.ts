import {User} from "./user";
import {Registration} from "./registration";
import {Carwash} from "./carwash";
import {Service} from "./service";

export class Order {
  private _id!: bigint;
  private _user!: User;
  private _employee!: Registration;
  private _carWash!: Carwash;
  private _service!: Service;
  private _date!: Date;
  private _text!: string;
  private _price!: number;
  private _grade!: number;
  private _status!: boolean;

  constructor() {
  }

  get id(): bigint {
    return this._id;
  }

  set id(value: bigint) {
    this._id = value;
  }

  get user(): User {
    return this._user;
  }

  set user(value: User) {
    this._user = value;
  }

  get employee(): Registration {
    return this._employee;
  }

  set employee(value: Registration) {
    this._employee = value;
  }

  get carWash(): Carwash {
    return this._carWash;
  }

  set carWash(value: Carwash) {
    this._carWash = value;
  }

  get service(): Service {
    return this._service;
  }

  set service(value: Service) {
    this._service = value;
  }

  get date(): Date {
    return this._date;
  }

  set date(value: Date) {
    this._date = value;
  }

  get text(): string {
    return this._text;
  }

  set text(value: string) {
    this._text = value;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }

  get grade(): number {
    return this._grade;
  }

  set grade(value: number) {
    this._grade = value;
  }

  get status(): boolean {
    return this._status;
  }

  set status(value: boolean) {
    this._status = value;
  }
}
