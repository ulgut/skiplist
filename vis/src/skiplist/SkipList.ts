import {SkipListNode} from "./SkipListNode";

export interface SkipList{
    get(key: number): GetMethodResult;
    delete(key: number): {}[];
    insert(key: number, val: number): void;
    toString():string;
    isEmpty():boolean;
    size():number;
}

export interface GetMethodResult{
    val: number | null;
    animations:{}[];
}

export interface SearchMethodResult{
    element: SkipListNode | null;
    animations:{}[];
}