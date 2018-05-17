/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { SalesDetDetailComponent } from '../../../../../../main/webapp/app/entities/sales-det/sales-det-detail.component';
import { SalesDetService } from '../../../../../../main/webapp/app/entities/sales-det/sales-det.service';
import { SalesDet } from '../../../../../../main/webapp/app/entities/sales-det/sales-det.model';

describe('Component Tests', () => {

    describe('SalesDet Management Detail Component', () => {
        let comp: SalesDetDetailComponent;
        let fixture: ComponentFixture<SalesDetDetailComponent>;
        let service: SalesDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SalesDetDetailComponent],
                providers: [
                    SalesDetService
                ]
            })
            .overrideTemplate(SalesDetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SalesDetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SalesDet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.salesDet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
