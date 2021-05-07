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
        this.state = {size:5, slArray: [], search_key:null, search_result:null, animations: []}
        this.onChangeVal = this.onChangeVal.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.animate = this.animate.bind(this);
    }

    onChangeVal(e: any){
        e.preventDefault();
        this.setState({[e.target.name] : e.target.value, animations:[], search_result: null});
    }


    componentDidMount() {
        this.renderList();
    }

    animate(res: GetMethodResult){
        for (let i: number = 0; i < res.animations.length; i++){
            setTimeout(()=> {
                let animation: {} = res.animations[i];
                // @ts-ignore
                if (animation["c1"] !== null){
                    // @ts-ignore
                    var element = document.getElementById(`node-${animation["c1"].getKey()}`);
                    if (element !== null) element.style.backgroundColor = `purple`;
                }
                // @ts-ignore
                if (animation["c2"] !== null){
                    // @ts-ignore
                    var element = document.getElementById(`node-${animation["c2"].getKey()}`);
                    if (element !== null) element.style.backgroundColor = `blue`;
                }
            }, 200 * i);
        }
        this.setState({search_result: res.val === null ? "No Value Found": res.val});
    }

    skipGrid(){
        return this.state.slArray.slice(0).reverse().map((row: SkipListNode[], rindex: number) => {
            return (
                <div className={"row m-0 p-0 justify-content-center"}>
                    {
                        row.map((col: SkipListNode, cindex: number) => {
                            return (
                                <div className={"col-auto p-0 m-0"}><Node node={col} r={rindex} c={cindex}/></div>
                            )
                        })
                    }
                </div>
            )
        });
    }

    handleSearch(){
        let res:GetMethodResult = this.sl.get(this.state.search_key);
        this.animate(res);
        this.setState({search_key: null});
    }

    renderList(){
        this.sl = new SkipListC();
        for (let i: number = 0; i < this.state.size; i++){
            let key: number = Math.floor(Math.random() * 100);
            while (key === 0 || this.sl.get(key).val !== null){
                key = Math.floor(Math.random() * 100); //handle weird TS? bug
            }
            this.sl.insert(key, key);
        }
        let res:SkipListNode[][] = this.sl.to2DArray();
        this.setState({slArray:res[0].map((_, colIndex) => res.map(row => row[colIndex]))}); // transpose rows to cols LA!
    }

    render(){
        return(
            <div>
                <div className={"skiplist-form"}>
                    <h4>Graph Params</h4>

                    <label>Number of Elements {this.state.size}</label><br/>

                    {this.min}<input type="range" name="size" className="skiplist-form__range" id="range" onChange={this.onChangeVal} value={this.state.size} max={this.max} min={this.min}/>{this.max}<br/>

                    <button className={"btn btn-dark"} onClick={() => {this.sl = new SkipListC(); this.renderList()}}>Build</button><br/>

                    <label>Search For An Element</label><br/>

                    <small className={"color-box-purple"}>Purple: Path</small><br/>


                    <small className={"color-box-blue"}>Blue: Element(if any)</small><br/><br/>

                    <input type={"number"} name={"search_key"} value={this.state.search_key} onChange={this.onChangeVal} placeholder={"Enter Key Here."}/>

                    <button className={"btn btn-dark"} onClick={this.handleSearch}>Search</button><br/>

                    <label>{this.state.search_key === "" && this.state.search_result === null ? "": "Search Result: " + this.state.search_result}</label>
                    <br/>
                    <br/>
                    <br/>

                </div>
                <div className={"container mx-auto skiplist"}>
                    {this.skipGrid()}
                </div>
            </div>
        )
    }

}

export default Grid;