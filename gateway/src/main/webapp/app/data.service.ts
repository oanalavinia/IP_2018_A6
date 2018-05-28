import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { ProductDetModule } from './product-det/product-det.module'

@Injectable()
export class DataService{

    constructor() {}

    getSome():string{
        return "some";
    }
}