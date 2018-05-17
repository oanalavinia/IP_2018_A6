/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { SalesDetComponent } from '../../../../../../main/webapp/app/entities/sales-det/sales-det.component';
import { SalesDetService } from '../../../../../../main/webapp/app/entities/sales-det/sales-det.service';
import { SalesDet } from '../../../../../../main/webapp/app/entities/sales-det/sales-det.model';

describe('Component Tests', () => {

    describe('SalesDet Management Component', () => {
        let comp: SalesDetComponent;
        let fixture: ComponentFixture<SalesDetComponent>;
        let service: SalesDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SalesDetComponent],
                providers: [
                    SalesDetService
                ]
            })
            .overrideTemplate(SalesDetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SalesDetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SalesDet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.salesDets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
