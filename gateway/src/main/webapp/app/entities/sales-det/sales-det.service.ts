import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { SalesDet } from './sales-det.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SalesDet>;

@Injectable()
export class SalesDetService {

    private resourceUrl =  SERVER_API_URL + 'sales/api/sales-dets';

    constructor(private http: HttpClient) { }

    create(salesDet: SalesDet): Observable<EntityResponseType> {
        const copy = this.convert(salesDet);
        return this.http.post<SalesDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(salesDet: SalesDet): Observable<EntityResponseType> {
        const copy = this.convert(salesDet);
        return this.http.put<SalesDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SalesDet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SalesDet[]>> {
        const options = createRequestOption(req);
        return this.http.get<SalesDet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SalesDet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SalesDet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SalesDet[]>): HttpResponse<SalesDet[]> {
        const jsonResponse: SalesDet[] = res.body;
        const body: SalesDet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SalesDet.
     */
    private convertItemFromServer(salesDet: SalesDet): SalesDet {
        const copy: SalesDet = Object.assign({}, salesDet);
        return copy;
    }

    /**
     * Convert a SalesDet to a JSON which can be sent to the server.
     */
    private convert(salesDet: SalesDet): SalesDet {
        const copy: SalesDet = Object.assign({}, salesDet);
        return copy;
    }
}
