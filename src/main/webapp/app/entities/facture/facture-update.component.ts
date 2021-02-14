import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacture, Facture } from 'app/shared/model/facture.model';
import { FactureService } from './facture.service';

@Component({
  selector: 'jhi-facture-update',
  templateUrl: './facture-update.component.html',
})
export class FactureUpdateComponent implements OnInit {
  isSaving = false;
  datePieceDp: any;
  factureRecuMailDp: any;
  factureRecuPhysiqueDp: any;
  blRecuMailDp: any;
  blRecuphysiqueDp: any;

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required]],
    datePiece: [null, [Validators.required]],
    client: [null, [Validators.required]],
    blVisa: [],
    commentaire: [null, [Validators.maxLength(2000)]],
    factureRecuMail: [],
    factureRecuPhysique: [],
    blRecuMail: [],
    blRecuphysique: [],
  });

  constructor(protected factureService: FactureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facture }) => {
      this.updateForm(facture);
    });
  }

  updateForm(facture: IFacture): void {
    this.editForm.patchValue({
      id: facture.id,
      numero: facture.numero,
      datePiece: facture.datePiece,
      client: facture.client,
      blVisa: facture.blVisa,
      commentaire: facture.commentaire,
      factureRecuMail: facture.factureRecuMail,
      factureRecuPhysique: facture.factureRecuPhysique,
      blRecuMail: facture.blRecuMail,
      blRecuphysique: facture.blRecuphysique,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facture = this.createFromForm();
    if (facture.id !== undefined) {
      this.subscribeToSaveResponse(this.factureService.update(facture));
    } else {
      this.subscribeToSaveResponse(this.factureService.create(facture));
    }
  }

  private createFromForm(): IFacture {
    return {
      ...new Facture(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      datePiece: this.editForm.get(['datePiece'])!.value,
      client: this.editForm.get(['client'])!.value,
      blVisa: this.editForm.get(['blVisa'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      factureRecuMail: this.editForm.get(['factureRecuMail'])!.value,
      factureRecuPhysique: this.editForm.get(['factureRecuPhysique'])!.value,
      blRecuMail: this.editForm.get(['blRecuMail'])!.value,
      blRecuphysique: this.editForm.get(['blRecuphysique'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacture>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
