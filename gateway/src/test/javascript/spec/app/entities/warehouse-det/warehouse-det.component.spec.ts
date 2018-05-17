/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { WarehouseDetComponent } from '../../../../../../main/webapp/app/entities/warehouse-det/warehouse-det.component';
import { WarehouseDetService } from '../../../../../../main/webapp/app/entities/warehouse-det/warehouse-det.service';
import { WarehouseDet } from '../../../../../../main/webapp/app/entities/warehouse-det/warehouse-det.model';

describe('Component Tests', () => {

    describe('WarehouseDet Management Component', () => {
        let comp: WarehouseDetComponent;
        let fixture: ComponentFixture<WarehouseDetComponent>;
        let service: WarehouseDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [WarehouseDetComponent],
                providers: [
                    WarehouseDetService
                ]
            })
            .overrideTemplate(WarehouseDetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WarehouseDetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WarehouseDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WarehouseDet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.warehouseDets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
