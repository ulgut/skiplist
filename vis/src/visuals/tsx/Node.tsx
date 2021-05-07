import React from 'react';
import '../styls/main.scss';
import {SkipListNode} from "../../skiplist/SkipListNode";
import {type} from '../../skiplist/SkipListNode';


interface nodeProps{
    node: SkipListNode | undefined;
    active: false;
}

function getColor(node: SkipListNode){
    switch (node.getType()){
        case type.cap:
            return `red`;
        case type.node:
            return `transparent`
        case type.root:
            return `green`;
    }
}

function setText(node: SkipListNode){
    switch (node.getType()){
        case type.root:
            return `ROOT`;
        case type.node:
            return `K:${node.getKey()}\nV:${node.getValue()}`
        case type.cap:
            return `CAP`;
    }
}

function setNode(node: SkipListNode | undefined): (JSX.Element){
    if (node === undefined){
        return (
            <div className={"node-square"} style={{backgroundColor: `transparent`}}>
                <br className={"modified-b"}/>
                <p className={"node-square__text"}>===={'>'}</p>
            </div>
        )
    }
    return (
        <div className={"node-square"} style={{backgroundColor:getColor(node)}}>
            <p className={"node-square__text"}>{setText(node)}</p>
        </div>
    )
}

const Node = (props: nodeProps) =>{
    return (
        setNode(props.node)
    )
}

export default Node;