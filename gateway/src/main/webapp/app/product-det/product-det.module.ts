import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
    import { RouterModule } from '@angular/router';

    import { ProductDetComponent, productDetRoute } from './';
import { ProductDetService } from './product-det.service';

    @NgModule({
        imports: [
            RouterModule.forRoot([ productDetRoute ], { useHash: true })
        ],
        declarations: [
            ProductDetComponent,
        ],
        entryComponents: [
        ],
        providers: [
            ProductDetService
        ]
    })
    export class ProductDetModule {}
    