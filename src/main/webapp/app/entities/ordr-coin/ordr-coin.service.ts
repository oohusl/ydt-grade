import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { OrdrCoin } from './ordr-coin.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<OrdrCoin>;

@Injectable()
export class OrdrCoinService {

    private resourceUrl =  SERVER_API_URL + 'api/ordr-coins';

    constructor(private http: HttpClient) { }

    create(ordrCoin: OrdrCoin): Observable<EntityResponseType> {
        const copy = this.convert(ordrCoin);
        return this.http.post<OrdrCoin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ordrCoin: OrdrCoin): Observable<EntityResponseType> {
        const copy = this.convert(ordrCoin);
        return this.http.put<OrdrCoin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<OrdrCoin>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<OrdrCoin[]>> {
        const options = createRequestOption(req);
        return this.http.get<OrdrCoin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<OrdrCoin[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: OrdrCoin = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<OrdrCoin[]>): HttpResponse<OrdrCoin[]> {
        const jsonResponse: OrdrCoin[] = res.body;
        const body: OrdrCoin[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to OrdrCoin.
     */
    private convertItemFromServer(ordrCoin: OrdrCoin): OrdrCoin {
        const copy: OrdrCoin = Object.assign({}, ordrCoin);
        return copy;
    }

    /**
     * Convert a OrdrCoin to a JSON which can be sent to the server.
     */
    private convert(ordrCoin: OrdrCoin): OrdrCoin {
        const copy: OrdrCoin = Object.assign({}, ordrCoin);
        return copy;
    }
}
