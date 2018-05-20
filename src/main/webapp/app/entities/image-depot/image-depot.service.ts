import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ImageDepot } from './image-depot.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ImageDepot>;

@Injectable()
export class ImageDepotService {

    private resourceUrl =  SERVER_API_URL + 'api/image-depots';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(imageDepot: ImageDepot): Observable<EntityResponseType> {
        const copy = this.convert(imageDepot);
        return this.http.post<ImageDepot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(imageDepot: ImageDepot): Observable<EntityResponseType> {
        const copy = this.convert(imageDepot);
        return this.http.put<ImageDepot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ImageDepot>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ImageDepot[]>> {
        const options = createRequestOption(req);
        return this.http.get<ImageDepot[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ImageDepot[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ImageDepot = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ImageDepot[]>): HttpResponse<ImageDepot[]> {
        const jsonResponse: ImageDepot[] = res.body;
        const body: ImageDepot[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ImageDepot.
     */
    private convertItemFromServer(imageDepot: ImageDepot): ImageDepot {
        const copy: ImageDepot = Object.assign({}, imageDepot);
        copy.createDate = this.dateUtils
            .convertDateTimeFromServer(imageDepot.createDate);
        return copy;
    }

    /**
     * Convert a ImageDepot to a JSON which can be sent to the server.
     */
    private convert(imageDepot: ImageDepot): ImageDepot {
        const copy: ImageDepot = Object.assign({}, imageDepot);

        copy.createDate = this.dateUtils.toDate(imageDepot.createDate);
        return copy;
    }
}
