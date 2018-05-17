import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WarehouseDet } from './warehouse-det.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WarehouseDet>;

@Injectable()
export class WarehouseDetService {

    private resourceUrl =  SERVER_API_URL + 'warehouse/api/warehouse-dets';

    constructor(private http: HttpClient) { }

    create(warehouseDet: WarehouseDet): Observable<EntityResponseType> {
        const copy = this.convert(warehouseDet);
        return this.http.post<WarehouseDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(warehouseDet: WarehouseDet): Observable<EntityResponseType> {
        const copy = this.convert(warehouseDet);
        return this.http.put<WarehouseDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WarehouseDet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WarehouseDet[]>> {
        const options = createRequestOption(req);
        return this.http.get<WarehouseDet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WarehouseDet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WarehouseDet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WarehouseDet[]>): HttpResponse<WarehouseDet[]> {
        const jsonResponse: WarehouseDet[] = res.body;
        const body: WarehouseDet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WarehouseDet.
     */
    private convertItemFromServer(warehouseDet: WarehouseDet): WarehouseDet {
        const copy: WarehouseDet = Object.assign({}, warehouseDet);
        return copy;
    }

    /**
     * Convert a WarehouseDet to a JSON which can be sent to the server.
     */
    private convert(warehouseDet: WarehouseDet): WarehouseDet {
        const copy: WarehouseDet = Object.assign({}, warehouseDet);
        return copy;
    }
}
