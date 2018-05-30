import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MarketingDet } from './marketing-det.model';
import { createRequestOption } from '../../shared';
 
export type EntityResponseType = HttpResponse<MarketingDet>;

@Injectable()
export class MarketingDetService {

    private resourceUrl =  SERVER_API_URL + 'marketing/api/marketing-dets';

    constructor(private http: HttpClient) { }

    create(marketingDet: MarketingDet): Observable<EntityResponseType> {
        const copy = this.convert(marketingDet);
        return this.http.post<MarketingDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(marketingDet: MarketingDet): Observable<EntityResponseType> {
        const copy = this.convert(marketingDet);
        return this.http.put<MarketingDet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MarketingDet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MarketingDet[]>> {
        const options = createRequestOption(req);
        return this.http.get<MarketingDet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MarketingDet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MarketingDet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MarketingDet[]>): HttpResponse<MarketingDet[]> {
        const jsonResponse: MarketingDet[] = res.body;
        const body: MarketingDet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MarketingDet.
     */
    private convertItemFromServer(marketingDet: MarketingDet): MarketingDet {
        const copy: MarketingDet = Object.assign({}, marketingDet);
        return copy;
    }

    /**
     * Convert a MarketingDet to a JSON which can be sent to the server.
     */
    private convert(marketingDet: MarketingDet): MarketingDet {
        const copy: MarketingDet = Object.assign({}, marketingDet);
        return copy;
    }
}
