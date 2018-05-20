import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Coin } from './coin.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Coin>;

@Injectable()
export class CoinService {

    private resourceUrl =  SERVER_API_URL + 'api/coins';

    constructor(private http: HttpClient) { }

    create(coin: Coin): Observable<EntityResponseType> {
        const copy = this.convert(coin);
        return this.http.post<Coin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(coin: Coin): Observable<EntityResponseType> {
        const copy = this.convert(coin);
        return this.http.put<Coin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Coin>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Coin[]>> {
        const options = createRequestOption(req);
        return this.http.get<Coin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Coin[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Coin = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Coin[]>): HttpResponse<Coin[]> {
        const jsonResponse: Coin[] = res.body;
        const body: Coin[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Coin.
     */
    private convertItemFromServer(coin: Coin): Coin {
        const copy: Coin = Object.assign({}, coin);
        return copy;
    }

    /**
     * Convert a Coin to a JSON which can be sent to the server.
     */
    private convert(coin: Coin): Coin {
        const copy: Coin = Object.assign({}, coin);
        return copy;
    }
}
