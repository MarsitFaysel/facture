import { Moment } from 'moment';

export interface IFacture {
  id?: number;
  numero?: number;
  datePiece?: Moment;
  client?: string;
  blVisa?: boolean;
  commentaire?: string;
  factureRecuMail?: Moment;
  factureRecuPhysique?: Moment;
  blRecuMail?: Moment;
  blRecuphysique?: Moment;
}

export class Facture implements IFacture {
  constructor(
    public id?: number,
    public numero?: number,
    public datePiece?: Moment,
    public client?: string,
    public blVisa?: boolean,
    public commentaire?: string,
    public factureRecuMail?: Moment,
    public factureRecuPhysique?: Moment,
    public blRecuMail?: Moment,
    public blRecuphysique?: Moment
  ) {
    this.blVisa = this.blVisa || false;
  }
}
