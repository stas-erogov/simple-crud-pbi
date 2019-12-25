import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleCrudPbiSharedModule } from 'app/shared/shared.module';
import { PointsComponent } from './points.component';
import { PointsDetailComponent } from './points-detail.component';
import { PointsUpdateComponent } from './points-update.component';
import { PointsDeleteDialogComponent } from './points-delete-dialog.component';
import { pointsRoute } from './points.route';

@NgModule({
  imports: [SimpleCrudPbiSharedModule, RouterModule.forChild(pointsRoute)],
  declarations: [PointsComponent, PointsDetailComponent, PointsUpdateComponent, PointsDeleteDialogComponent],
  entryComponents: [PointsDeleteDialogComponent]
})
export class SimpleCrudPbiPointsModule {}
