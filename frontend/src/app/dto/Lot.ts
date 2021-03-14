import {LotPicture} from './LotPicture';
import {User} from './User';

export class Lot {
  public id: number;
  public name: string;
  public description: string;
  public price: number;
  public creationDate: Date;
  public active: boolean;
  public picturesList: LotPicture[];
  public userDto: User;
}
