import {NewLotPicture} from './NewLotPicture';

export class NewLot {
  constructor(description: string, price: number, picturesList: NewLotPicture[], userId: number) {
    this.description = description;
    this.price = price;
    this.picturesList = picturesList;
    this.userId = userId;
  }

  public description: string;
  public price: number;
  public picturesList: NewLotPicture[];
  public userId: number;
}
