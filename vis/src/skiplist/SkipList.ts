export interface SkipList{
    get(key: number): number | null;
    delete(key: number): void;
    insert(key: number, val: number): void;
    toString():string;
    isEmpty():boolean;
    size():number;
}