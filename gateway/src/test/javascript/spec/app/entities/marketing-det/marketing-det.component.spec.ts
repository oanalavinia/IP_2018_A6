/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { MarketingDetComponent } from '../../../../../../main/webapp/app/entities/marketing-det/marketing-det.component';
import { MarketingDetService } from '../../../../../../main/webapp/app/entities/marketing-det/marketing-det.service';
import { MarketingDet } from '../../../../../../main/webapp/app/entities/marketing-det/marketing-det.model';

describe('Component Tests', () => {

    describe('MarketingDet Management Component', () => {
        let comp: MarketingDetComponent;
        let fixture: ComponentFixture<MarketingDetComponent>;
        let service: MarketingDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [MarketingDetComponent],
                providers: [
                    MarketingDetService
                ]
            })
            .overrideTemplate(MarketingDetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketingDetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketingDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MarketingDet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.marketingDets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
