import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FeeRule } from './fee-rule.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FeeRule>;

@Injectable()
export class FeeRuleService {

    private resourceUrl =  SERVER_API_URL + 'api/fee-rules';

    constructor(private http: HttpClient) { }

    create(feeRule: FeeRule): Observable<EntityResponseType> {
        const copy = this.convert(feeRule);
        return this.http.post<FeeRule>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(feeRule: FeeRule): Observable<EntityResponseType> {
        const copy = this.convert(feeRule);
        return this.http.put<FeeRule>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FeeRule>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FeeRule[]>> {
        const options = createRequestOption(req);
        return this.http.get<FeeRule[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FeeRule[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FeeRule = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FeeRule[]>): HttpResponse<FeeRule[]> {
        const jsonResponse: FeeRule[] = res.body;
        const body: FeeRule[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FeeRule.
     */
    private convertItemFromServer(feeRule: FeeRule): FeeRule {
        const copy: FeeRule = Object.assign({}, feeRule);
        return copy;
    }

    /**
     * Convert a FeeRule to a JSON which can be sent to the server.
     */
    private convert(feeRule: FeeRule): FeeRule {
        const copy: FeeRule = Object.assign({}, feeRule);
        return copy;
    }
}
