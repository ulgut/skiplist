import React from 'react';
import '../styles/main.scss';
import {SkipListNode} from "../../skiplist/SkipListNode";
import {type} from '../../skiplist/SkipListNode';


interface nodeProps{
    node: SkipListNode | undefined;
    r: number;
    c: number;
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

function setNode(node: SkipListNode | undefined, r:number, c: number): (JSX.Element){
    if (node === undefined){
        return (
            <div id={`node-${r}`} className={"node-square"} style={{backgroundColor: `transparent`}}>
                <br className={"modified-b"}/>
                <p className={"node-square__text"}>===={'>'}</p>
            </div>
        )
    }
    return (
        <div id={`node-${node.getKey()}`} className={"node-square"} style={{backgroundColor:getColor(node)}}>
            <p className={"node-square__text"}>{setText(node)}</p>
        </div>
    )
}

const Node = (props: nodeProps) =>{
    return (
        setNode(props.node, props.r,props.c)
    )
}

export default Node;