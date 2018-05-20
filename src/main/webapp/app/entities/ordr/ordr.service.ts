import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ordr } from './ordr.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Ordr>;

@Injectable()
export class OrdrService {

    private resourceUrl =  SERVER_API_URL + 'api/ordrs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(ordr: Ordr): Observable<EntityResponseType> {
        const copy = this.convert(ordr);
        return this.http.post<Ordr>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ordr: Ordr): Observable<EntityResponseType> {
        const copy = this.convert(ordr);
        return this.http.put<Ordr>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Ordr>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Ordr[]>> {
        const options = createRequestOption(req);
        return this.http.get<Ordr[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Ordr[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ordr = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Ordr[]>): HttpResponse<Ordr[]> {
        const jsonResponse: Ordr[] = res.body;
        const body: Ordr[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Ordr.
     */
    private convertItemFromServer(ordr: Ordr): Ordr {
        const copy: Ordr = Object.assign({}, ordr);
        copy.createDate = this.dateUtils
            .convertDateTimeFromServer(ordr.createDate);
        copy.receiveDate = this.dateUtils
            .convertDateTimeFromServer(ordr.receiveDate);
        copy.backDate = this.dateUtils
            .convertDateTimeFromServer(ordr.backDate);
        return copy;
    }

    /**
     * Convert a Ordr to a JSON which can be sent to the server.
     */
    private convert(ordr: Ordr): Ordr {
        const copy: Ordr = Object.assign({}, ordr);

        copy.createDate = this.dateUtils.toDate(ordr.createDate);

        copy.receiveDate = this.dateUtils.toDate(ordr.receiveDate);

        copy.backDate = this.dateUtils.toDate(ordr.backDate);
        return copy;
    }
}
