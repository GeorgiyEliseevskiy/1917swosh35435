export class Service {
  private _id!: bigint;
  private _name!: string;
  private _price!: number;
  private _carWashLocation!: string;

  constructor() {
  }

  get id(): bigint {
    return this._id;
  }

  set id(value: bigint) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }

  get carWashLocation(): string {
    return this._carWashLocation;
  }

  set carWashLocation(value: string) {
    this._carWashLocation = value;
  }
}

