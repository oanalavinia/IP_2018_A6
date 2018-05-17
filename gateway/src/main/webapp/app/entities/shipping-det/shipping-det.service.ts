import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ShippingDet } from './shipping-det.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ShippingDet>;

@Injectable()
export class ShippingDetService {

    private resourceUrl =  SERVER_API_URL + 'shipping/api/shipping-dets';

    constructor(private http: HttpClient) { }

    create(shippingDet: ShippingDet): Observable<EntityResponseType> {
        const copy = this.convert(shippingDet);
        return this.http.post<ShippingDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(shippingDet: ShippingDet): Observable<EntityResponseType> {
        const copy = this.convert(shippingDet);
        return this.http.put<ShippingDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ShippingDet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ShippingDet[]>> {
        const options = createRequestOption(req);
        return this.http.get<ShippingDet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ShippingDet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ShippingDet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ShippingDet[]>): HttpResponse<ShippingDet[]> {
        const jsonResponse: ShippingDet[] = res.body;
        const body: ShippingDet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ShippingDet.
     */
    private convertItemFromServer(shippingDet: ShippingDet): ShippingDet {
        const copy: ShippingDet = Object.assign({}, shippingDet);
        return copy;
    }

    /**
     * Convert a ShippingDet to a JSON which can be sent to the server.
     */
    private convert(shippingDet: ShippingDet): ShippingDet {
        const copy: ShippingDet = Object.assign({}, shippingDet);
        return copy;
    }
}
