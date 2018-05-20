import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { UserAddress } from './user-address.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<UserAddress>;

@Injectable()
export class UserAddressService {

    private resourceUrl =  SERVER_API_URL + 'api/user-addresses';

    constructor(private http: HttpClient) { }

    create(userAddress: UserAddress): Observable<EntityResponseType> {
        const copy = this.convert(userAddress);
        return this.http.post<UserAddress>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(userAddress: UserAddress): Observable<EntityResponseType> {
        const copy = this.convert(userAddress);
        return this.http.put<UserAddress>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<UserAddress>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<UserAddress[]>> {
        const options = createRequestOption(req);
        return this.http.get<UserAddress[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<UserAddress[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: UserAddress = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<UserAddress[]>): HttpResponse<UserAddress[]> {
        const jsonResponse: UserAddress[] = res.body;
        const body: UserAddress[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to UserAddress.
     */
    private convertItemFromServer(userAddress: UserAddress): UserAddress {
        const copy: UserAddress = Object.assign({}, userAddress);
        return copy;
    }

    /**
     * Convert a UserAddress to a JSON which can be sent to the server.
     */
    private convert(userAddress: UserAddress): UserAddress {
        const copy: UserAddress = Object.assign({}, userAddress);
        return copy;
    }
}
