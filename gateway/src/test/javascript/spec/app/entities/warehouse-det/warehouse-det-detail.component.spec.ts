/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { WarehouseDetDetailComponent } from '../../../../../../main/webapp/app/entities/warehouse-det/warehouse-det-detail.component';
import { WarehouseDetService } from '../../../../../../main/webapp/app/entities/warehouse-det/warehouse-det.service';
import { WarehouseDet } from '../../../../../../main/webapp/app/entities/warehouse-det/warehouse-det.model';

describe('Component Tests', () => {

    describe('WarehouseDet Management Detail Component', () => {
        let comp: WarehouseDetDetailComponent;
        let fixture: ComponentFixture<WarehouseDetDetailComponent>;
        let service: WarehouseDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [WarehouseDetDetailComponent],
                providers: [
                    WarehouseDetService
                ]
            })
            .overrideTemplate(WarehouseDetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WarehouseDetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WarehouseDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WarehouseDet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.warehouseDet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
