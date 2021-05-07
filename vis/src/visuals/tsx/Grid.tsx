import React from "react";
import {SkipListC} from "../../skiplist/SkipListC";
import Node from './Node';
import {SkipListNode} from "../../skiplist/SkipListNode";
import {GetMethodResult} from "../../skiplist/SkipList";


class Grid extends React.Component<any, any>{
    private sl: SkipListC;
    private max = 30; // max/min # of insertions
    private min = 5;

    constructor(props:any) {
        super(props);
        this.sl = new SkipListC();
        this.state = {size:5, slArray: [], search_key:0, search_result:null}
        this.onChangeVal = this.onChangeVal.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
    }

    onChangeVal(e: any){
        e.preventDefault();
        this.setState({[e.target.name] : e.target.value});
    }


    componentDidMount() {
        this.renderList();
    }

    setAnimation(animation:{}){

    }

    handleSearch(){
        let res:GetMethodResult = this.sl.get(this.state.search_key);
        for (let i: number = 0; i < res.animations.length;i++){
            let animation:{} = res.animations[i];
            this.setAnimation(animation);
        }
        this.setState({search_result: res.val});
    }

    renderList(){
        this.sl = new SkipListC();
        for (let i: number = 0; i < this.state.size; i++){
            let key: number = Math.floor(Math.random() * 100);
            this.sl.insert(key, key);
        }
        let res:SkipListNode[][] = this.sl.to2DArray();
        this.setState({slArray:res[0].map((_, colIndex) => res.map(row => row[colIndex]))}); // transpose rows to cols LA!
    }

    search(){

    }



    render(){
        const setSize = () =>{
            var w = window.innerWidth;
            var MAX_SIZE = 60;
            var MARGIN = 10
            var resize_factor = ( w / (this.state.slArray[0].length * 60 + this.state.slArray[0].length * MARGIN));
            return resize_factor < 1 ? MAX_SIZE * (1-resize_factor) : MAX_SIZE;
        }
        const skipGrid = this.state.slArray.slice(0).reverse().map((row: SkipListNode[], rindex: number)=>{

            return(
                <div className={"row m-0 p-0 justify-content-center"}>
                    {
                        row.map((col: SkipListNode, cindex: number)=>{
                            return(
                                <div className={"col-auto p-0 m-0"}><Node node={col} active={false}/></div>
                            )
                        })
                    }
                </div>
            )
        })
        return(
            <div>
                <div className={"skiplist-form"}>
                    <h4>Graph Params</h4>
                    <label>Number of Elements {this.state.size}</label><br/>
                    {this.min}<input type="range" name="size" className="skiplist-form__range" id="range" onChange={this.onChangeVal} value={this.state.size} max={this.max} min={this.min}/>{this.max}<br/>
                    <button className={"btn btn-dark"} onClick={() => {this.sl = new SkipListC(); this.renderList()}}>Build</button><br/>
                    <label>Search For An Element</label><br/>
                    <input type={"number"} name={"search_key"} value={this.state.search_key} onChange={this.onChangeVal} placeholder={"Enter Key Here."}/>
                    <button className={"btn btn-dark"} onClick={this.handleSearch}>Search</button><br/>
                    <label>{this.state.search_key === "" && this.state.search_result === null ? "": "Search Result: " + this.state.search_result}</label><br/>
                </div>
                <div className={"container  mx-auto skiplist"}>
                    {skipGrid}
                </div>
            </div>
        )
    }

}

export default Grid;