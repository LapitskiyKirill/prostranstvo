export class EditLot {
  public name: string;
  public description: string;
  public price: number;
  public deletedPictures: number[];

  constructor(name: string, description: string, price: number, deletedPictures: number[]) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.deletedPictures = deletedPictures;
  }
}
