export class UserStats {
  private _id!: bigint;
  private _name!: string;
  private _email!: string;
  private _phone!: string;
  private _numberOfVisits!: number;
  private _averageCheck!: number;

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

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get numberOfVisits(): number {
    return this._numberOfVisits;
  }

  set numberOfVisits(value: number) {
    this._numberOfVisits = value;
  }

  get averageCheck(): number {
    return this._averageCheck;
  }

  set averageCheck(value: number) {
    this._averageCheck = value;
  }
}
