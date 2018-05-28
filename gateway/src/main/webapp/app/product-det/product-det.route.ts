import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ProductDetComponent } from './';

export const productDetRoute: Route = {
    path: 'product-det',
    component: ProductDetComponent,
    data: {
        authorities: [],
        pageTitle: 'product-det.title'
    }
};
