import {SkipListNode, type} from "./SkipListNode";
import {GetMethodResult, SearchMethodResult, SkipList} from "./SkipList";

export class SkipListC implements SkipList{
    private p: number = 1 / Math.E;
    private n: number;
    private start: SkipListNode;
    private terminus: SkipListNode;
    private height:number;

    public animations:{}[]=[];

    constructor() {// implement this insert array method later if time
        this.start = new SkipListNode(undefined, undefined, undefined, undefined, type.root);
        console.log(this.start.getType());
        this.terminus = new SkipListNode(undefined, undefined, undefined, undefined, type.cap);
        this.height = 1;
        this.n = 0;
    }

    private search(key: number): SearchMethodResult {
        console.log("KEY IS: ", key);
        this.animations = [];
        let curr: SkipListNode = this.start;
        let i: number = this.start.height() - 1;
        let comp1Row:number = i;
        let comp1Col:number = 0;
        this.animations.push([{"c1R": comp1Row, "c1C": comp1Col}])

        while(curr.getType() !== type.cap){
            console.log("In search, level: ", i);
            if (curr.nexts[i].isLessKey(key)){
                curr = curr.nexts[i];
                comp1Row = i;
                comp1Col += 1;
                this.animations.push([{"c1R": comp1Row, "c1C": comp1Col}]);
            }
            else if (curr.nexts[i].equals(key)){
                return {element: curr.nexts[i], animations: this.animations};
            }
            else{
                i -= 1;
            }
        }
        return {element: null, animations: this.animations}
    }

    public get(key: number): GetMethodResult {
        let res = this.search(key);
        return {val: res.element?.getValue() || null, animations:res.animations};
    }

    public delete(key: number): ({}[]) {
        let res: SearchMethodResult = this.search(key);
        let node: SkipListNode | null = res.element;
        if (node == null){
            return res.animations;
        }
        this.animations = [];
        for (let i: number = 0; i < node.nexts.length; i++){
            let b: SkipListNode  = node.prevs[i];
            let a: SkipListNode  = node.nexts[i];
            b.nexts[i] = a;
            a.prevs[i] = b;
        }
        this.n -= 1;
        return [];
    }


    public insert(key: number, val: number): void {
        this.animations = [];
        let levels: number = this.levels();

        this.increase(levels);


        let newNode: SkipListNode  = new SkipListNode(undefined, undefined, key, val, type.node);
        for (let i: number = 0; i < levels; i++){
            newNode.nexts[i] = new SkipListNode(undefined, undefined, undefined, undefined, type.node)
            newNode.prevs[i] = new SkipListNode(undefined, undefined, undefined, undefined, type.node)
        }

        let i: number = levels - 1;

        while (i >= 0){
            let back: SkipListNode = this.start;

            while (i< back.height() && back.nexts[i] !== this.terminus && back.nexts[i].isLess(newNode)){
                back = back.nexts[i];
            }

            let front: SkipListNode = this.terminus;

            if (back.nexts[i] != null){
                front = back.nexts[i];
            }

            newNode.prevs[i] = back;
            back.nexts[i] = newNode;


            newNode.nexts[i] = front;
            front.prevs[i] = newNode;

            i -= 1;
        }
        this.n += 1;
    }

    private levels():number{
        if (Math.random() < this.p){
            return 1 + this.levels();
        }
        return 1;
    }

    private increase(levels: number):void{
        while (levels > this.start.height()){
            this.start.nexts.push(new SkipListNode(undefined, undefined, undefined, undefined, type.root));
            this.terminus.prevs.push(new SkipListNode(undefined, undefined, undefined, undefined, type.cap));

            this.start.nexts[this.start.nexts.length - 1] = this.terminus;
            this.terminus.prevs[this.terminus.prevs.length - 1] = this.start;
        }
    }


    public to2DArray(): SkipListNode[][]{ // we arent updating node's next fields to include
        let i: number = 0;
        let tmp: SkipListNode = this.start;
        let cols: SkipListNode[][] = [];
        while (i <= this.n){
            let col: SkipListNode[] = [];
            for (var j : number = 1; j<=tmp.height(); j++){
                col.push(tmp);
            }
            tmp = tmp.nexts[0];
            cols.push(col);
            i++;
        }
        console.log(tmp.getType(), tmp.height());
        let col: SkipListNode[] = [];
        for (var j:number = 1; j<=this.start.nexts.length; j++){
            col.push(tmp);
        }
        cols.push(col)
        return cols;
    }

    public toString(): string {
        return this.start.toString();
    }

    public size(): number {
        return this.n;
    }

    public isEmpty(): boolean {
        return this.n === 0;
    }
}