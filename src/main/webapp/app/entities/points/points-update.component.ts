import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IPoints, Points } from 'app/shared/model/points.model';
import { PointsService } from './points.service';

@Component({
  selector: 'jhi-points-update',
  templateUrl: './points-update.component.html'
})
export class PointsUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    region: [],
    city: [],
    type: [],
    date: [],
    fact: [],
    bandwidth: []
  });

  constructor(protected pointsService: PointsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ points }) => {
      this.updateForm(points);
    });
  }

  updateForm(points: IPoints): void {
    this.editForm.patchValue({
      id: points.id,
      region: points.region,
      city: points.city,
      type: points.type,
      date: points.date,
      fact: points.fact,
      bandwidth: points.bandwidth
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const points = this.createFromForm();
    if (points.id !== undefined) {
      this.subscribeToSaveResponse(this.pointsService.update(points));
    } else {
      this.subscribeToSaveResponse(this.pointsService.create(points));
    }
  }

  private createFromForm(): IPoints {
    return {
      ...new Points(),
      id: this.editForm.get(['id'])!.value,
      region: this.editForm.get(['region'])!.value,
      city: this.editForm.get(['city'])!.value,
      type: this.editForm.get(['type'])!.value,
      date: this.editForm.get(['date'])!.value,
      fact: this.editForm.get(['fact'])!.value,
      bandwidth: this.editForm.get(['bandwidth'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoints>>): void {
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
