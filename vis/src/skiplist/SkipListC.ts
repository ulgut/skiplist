import {SkipListNode} from "./SkipListNode";
import {type} from "./SkipListNode";
import {SkipList} from "./SkipList";

export class SkipListC implements SkipList{
    private p: number = 1 / Math.E;
    private n: number;
    private start: SkipListNode;
    private terminus: SkipListNode;
    private height:number;

    constructor() {// implement this insert array method later if time
        this.start = new SkipListNode();
        this.terminus = new SkipListNode();
        this.height = 1;
        this.n = 0;
    }

    private search(key: number): SkipListNode | never {
        let curr: SkipListNode = this.start;
        let i: number = this.start.height() - 1;

        while(curr.getType() !== type.cap){
            if (curr.nexts[i].isLessKey(key)){
                curr = curr.nexts[i];
                //comps?
            }
            else if (curr.nexts[i].equals(key)){
                return curr.nexts[i];
            }
            else{
                i--;
            }
        }
        throw new Error("No such val in the skiplist");
    }

    public get(key: number): number | null {//shouldn't ever be null
        return this.search(key).getValue();
    }

    public delete(key: number) {
        let node: SkipListNode = this.search(key);
        for (var i: number = 0; i < node.nexts.length; i++){
            var b: SkipListNode  = node.prevs[i];
            var a: SkipListNode  = node.nexts[i];
            b.nexts[i] = a;
            a.prevs[i] = b;
        }
        this.n--;
    }


    public insert(key: number, val: number) {
        let levels: number = this.levels();

        this.increase(levels);


        let newNode: SkipListNode  = new SkipListNode(undefined, undefined, key, val);
        for (let i: number = 0; i < levels; i++){
            newNode.nexts[i] = new SkipListNode(undefined,undefined,undefined,undefined)
            newNode.prevs[i] = new SkipListNode(undefined,undefined,undefined,undefined)
        }

        let i: number = levels - 1;

        while (i >= 0){
            let back: SkipListNode = this.start;

            while (i< back.height() && back.nexts[i] !== this.terminus && back.nexts[i].isLess(newNode)){
                back = back.nexts[i];
            }

            let front: SkipListNode = this.terminus;

            if (back.nexts[i] != null){
                front = front.nexts[i];
            }

            newNode.prevs[i] = back;
            back.nexts[i] = newNode;


            newNode.nexts[i] = front;
            front.prevs[i] = newNode;

            i--;
        }
        this.n++;
    }

    private levels():number{
        if (Math.random() < this.p){
            return 1 + this.levels();
        }
        return 1;
    }

    private increase(levels: number):void{
        while (levels > this.start.height()){
            this.start.nexts.push(new SkipListNode());
            this.terminus.prevs.push(new SkipListNode());

            this.start.nexts[this.start.nexts.length - 1] = this.terminus;
            this.terminus.prevs[this.terminus.prevs.length - 1] = this.start;
        }
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