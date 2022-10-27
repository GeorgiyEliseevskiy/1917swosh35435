import {Registration} from "./registration";
import {Service} from "./service";

export class Carwash {
  private _id!: bigint;
  private _location!: string;
  private _name!: string;
  private _phone!: string;
  private _employees!: Registration[];
  private _services!: Service[];

  constructor() {
  }

  get id(): bigint {
    return this._id;
  }

  set id(value: bigint) {
    this._id = value;
  }

  get employees(): Registration[] {
    return this._employees;
  }

  set employees(value: Registration[]) {
    this._employees = value;
  }

  get services(): Service[] {
    return this._services;
  }

  set services(value: Service[]) {
    this._services = value;
  }

  get location(): string {
    return this._location;
  }

  set location(value: string) {
    this._location = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get phone(): string {
    return this._phone;
  }

  set phone(value: string) {
    this._phone = value;
  }
}
