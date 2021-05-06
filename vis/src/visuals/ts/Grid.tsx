import React from "react";
import {SkipListC} from "../../skiplist/SkipListC";


class Grid extends React.Component<any, any>{
    private sl: SkipListC;

    constructor(props:any) {
        super(props);
        this.sl = new SkipListC();
        this.state = {size:10}
        this.onChangeVal = this.onChangeVal.bind(this);
    }

    onChangeVal(e: any){
        e.preventDefault();
        this.setState({[e.target.name] : e.target.value});
        console.log(this.state.size);
    }


    componentDidMount() {
        this.renderList();
    }

    renderList(){
        for (let i: number = 0; i < this.state.size; i++){
            let key: number = Math.random() * 100;
            let val: number = key;
            this.sl.insert(key, val);
        }
    }


    render(){
        return (
            <div>
                <div className={"skiplist-form"}>
                    <h4>Graph Params</h4>
                    <label>Number of Elements</label><br/>
                    5<input type="range" name="size" className="skiplist-form__range" id="range" onChange={this.onChangeVal} value={this.state.size}/>99
                </div>
                <div className={"skiplist"}>

                </div>
            </div>
        )
    }

}

export default Grid;