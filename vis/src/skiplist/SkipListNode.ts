export enum type{
    root,
    node,
    cap,
    null
}

export class SkipListNode{
    public prevs: SkipListNode[];
    public nexts: SkipListNode[];
    private key: number | null;
    private val: number | null;
    private type: type;


    constructor(prevs?: undefined, nexts?: undefined, key?: number | null, val?: number | null, nodeType?: type) {
        this.prevs = prevs || [];
        this.nexts = nexts || [];
        this.key = key || null;
        this.val = val || null;
        this.type = nodeType || type.root;
    }

    private compareTo(n2: number | null): number{
        // @tsx-ignore
        // @ts-ignore
        if (this.key > n2){
            return 1;
        }
        // @tsx-ignore
        else { // @ts-ignore
            if(this.key < n2){
                        return -1;
                    }
        }
        return 0;
    }


    public isLess(n2: SkipListNode): boolean{
        if (this.type === type.root){
            return true;
        }
        else if (this.type === type.cap){
            return false;
        }
        return this.compareTo(n2.key) < 0;
    }

    public isLessKey(key: number): boolean{
        if (this.type === type.root)
            return true;
        else if (this.type === type.cap)
            return false;
        else
            { // @tsx-ignore
                return this.compareTo(key) < 0;
            }
    }

    public equals(key2: number): boolean{
        if (this.key == null){
            return false
        }
        return this.compareTo(key2) === 0;
    }

    public height(): number{
        return this.nexts.length;
    }

    public toString(): string{
        let str: string = "";

        for (let i:number = this.nexts.length - 1; i >= 0; i--) {
            let currentNode: SkipListNode = this;

            while (currentNode.type !== type.cap) {
                str += "{" + currentNode.key + ", " + currentNode.val + "} --> ";
                currentNode = currentNode.nexts[i];
            }
            str += "\n";
        }

        return str;
    }

    public getType():type{
        return this.type;
    }

    public getValue():(number | null){
        return this.val;
    }

    public getKey():(number | null){
        return this.key;
    }


    public to2DArray(): (SkipListNode[][]) {
        let rows: SkipListNode[][] = [];

        for (let i:number = this.nexts.length - 1; i >= 0; i--) {
            let currentNode: SkipListNode = this;
            let col: SkipListNode[] = [];
            while (currentNode.type !== type.cap) {
                col.push(currentNode);
                currentNode = currentNode.nexts[i];
            }
            col.push(currentNode);
            rows.push(col);
        }

        return rows;
    }


}
