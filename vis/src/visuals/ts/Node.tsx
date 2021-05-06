import React from 'react';
import {SkipListNode} from "../../skiplist/SkipListNode";


interface nodeProps{
    node: SkipListNode;
    active: boolean;
}

const Node = (props: nodeProps) =>{
    return (
        <div className={"node-square"}>

        </div>
    )
}
export default Node;