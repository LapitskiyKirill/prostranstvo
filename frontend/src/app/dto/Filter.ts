export class Filter {
  public highestPrice: number;
  public lowestPrice: number;
  public orderByPrice: boolean;

  constructor(highestPrice: number, lowestPrice: number, orderByPrice: boolean) {
    this.highestPrice = highestPrice;
    this.lowestPrice = lowestPrice;
    this.orderByPrice = orderByPrice;
  }
}
