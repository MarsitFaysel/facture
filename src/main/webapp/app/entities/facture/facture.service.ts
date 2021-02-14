import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacture } from 'app/shared/model/facture.model';

type EntityResponseType = HttpResponse<IFacture>;
type EntityArrayResponseType = HttpResponse<IFacture[]>;

@Injectable({ providedIn: 'root' })
export class FactureService {
  public resourceUrl = SERVER_API_URL + 'api/factures';

  constructor(protected http: HttpClient) {}

  create(facture: IFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facture);
    return this.http
      .post<IFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facture: IFacture): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facture);
    return this.http
      .put<IFacture>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacture[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facture: IFacture): IFacture {
    const copy: IFacture = Object.assign({}, facture, {
      datePiece: facture.datePiece && facture.datePiece.isValid() ? facture.datePiece.format(DATE_FORMAT) : undefined,
      factureRecuMail:
        facture.factureRecuMail && facture.factureRecuMail.isValid() ? facture.factureRecuMail.format(DATE_FORMAT) : undefined,
      factureRecuPhysique:
        facture.factureRecuPhysique && facture.factureRecuPhysique.isValid() ? facture.factureRecuPhysique.format(DATE_FORMAT) : undefined,
      blRecuMail: facture.blRecuMail && facture.blRecuMail.isValid() ? facture.blRecuMail.format(DATE_FORMAT) : undefined,
      blRecuphysique: facture.blRecuphysique && facture.blRecuphysique.isValid() ? facture.blRecuphysique.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datePiece = res.body.datePiece ? moment(res.body.datePiece) : undefined;
      res.body.factureRecuMail = res.body.factureRecuMail ? moment(res.body.factureRecuMail) : undefined;
      res.body.factureRecuPhysique = res.body.factureRecuPhysique ? moment(res.body.factureRecuPhysique) : undefined;
      res.body.blRecuMail = res.body.blRecuMail ? moment(res.body.blRecuMail) : undefined;
      res.body.blRecuphysique = res.body.blRecuphysique ? moment(res.body.blRecuphysique) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((facture: IFacture) => {
        facture.datePiece = facture.datePiece ? moment(facture.datePiece) : undefined;
        facture.factureRecuMail = facture.factureRecuMail ? moment(facture.factureRecuMail) : undefined;
        facture.factureRecuPhysique = facture.factureRecuPhysique ? moment(facture.factureRecuPhysique) : undefined;
        facture.blRecuMail = facture.blRecuMail ? moment(facture.blRecuMail) : undefined;
        facture.blRecuphysique = facture.blRecuphysique ? moment(facture.blRecuphysique) : undefined;
      });
    }
    return res;
  }
}
