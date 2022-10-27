export class Registration {
  private _id!: bigint;
  private _name!: string;
  private _phone!: string;
  private _password!: string;
  private _carWashLocation!: string;
  private _email!: string;

  constructor() {
  }

  get id(): bigint {
    return this._id;
  }

  set id(value: bigint) {
    this._id = value;
  }

  get phone(): string {
    return this._phone;
  }

  set phone(value: string) {
    this._phone = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get carWashLocation(): string {
    return this._carWashLocation;
  }

  set carWashLocation(value: string) {
    this._carWashLocation = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }
}
