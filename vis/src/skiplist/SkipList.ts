import {SkipListNode} from "./SkipListNode";
import {SkipListC} from "./SkipListC";

export interface SkipList{ // interface for skiplist
    get(key: number): GetMethodResult;
    delete(key: number): {}[];
    insert(key: number, val: number): void;
    toString():string;
    isEmpty():boolean;
    size():number;
}

export interface GetMethodResult{ // special type of response for get method
    val: number | null;
    animations:{}[];
}

export interface SearchMethodResult{ // special type of response for search method
    element: SkipListNode | null;
    animations:{}[];
}

export interface animationJson{ // special structure for animation
    c1: SkipListNode | null;
    c2: SkipListNode | null;
    slState: SkipListC;
}