import { WarehouseDetService, WarehouseDet } from "../entities/warehouse-det";
import { HttpResponse, HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { SERVER_API_URL } from "../app.constants";
import { createRequestOption } from "../shared";
import { EntityResponseType } from "../entities/marketing-det";
import { Injectable } from '@angular/core';

@Injectable()
export class ProductDetService{
    private resourceUrl =  SERVER_API_URL + 'warehouse/api/warehouse-dets';
    constructor(private warehouseService:WarehouseDetService, private http: HttpClient){
        
    }

    query(req?: any): Observable<HttpResponse<WarehouseDet[]>> {
        const options = createRequestOption(req);
        return this.http.get<WarehouseDet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WarehouseDet[]>) => this.convertArrayResponse(res));
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