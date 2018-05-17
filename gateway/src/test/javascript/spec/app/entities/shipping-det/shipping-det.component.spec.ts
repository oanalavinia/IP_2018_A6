/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { ShippingDetComponent } from '../../../../../../main/webapp/app/entities/shipping-det/shipping-det.component';
import { ShippingDetService } from '../../../../../../main/webapp/app/entities/shipping-det/shipping-det.service';
import { ShippingDet } from '../../../../../../main/webapp/app/entities/shipping-det/shipping-det.model';

describe('Component Tests', () => {

    describe('ShippingDet Management Component', () => {
        let comp: ShippingDetComponent;
        let fixture: ComponentFixture<ShippingDetComponent>;
        let service: ShippingDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ShippingDetComponent],
                providers: [
                    ShippingDetService
                ]
            })
            .overrideTemplate(ShippingDetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShippingDetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShippingDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ShippingDet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.shippingDets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
