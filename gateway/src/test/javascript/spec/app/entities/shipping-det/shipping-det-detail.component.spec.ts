/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { ShippingDetDetailComponent } from '../../../../../../main/webapp/app/entities/shipping-det/shipping-det-detail.component';
import { ShippingDetService } from '../../../../../../main/webapp/app/entities/shipping-det/shipping-det.service';
import { ShippingDet } from '../../../../../../main/webapp/app/entities/shipping-det/shipping-det.model';

describe('Component Tests', () => {

    describe('ShippingDet Management Detail Component', () => {
        let comp: ShippingDetDetailComponent;
        let fixture: ComponentFixture<ShippingDetDetailComponent>;
        let service: ShippingDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ShippingDetDetailComponent],
                providers: [
                    ShippingDetService
                ]
            })
            .overrideTemplate(ShippingDetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShippingDetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShippingDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ShippingDet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.shippingDet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
