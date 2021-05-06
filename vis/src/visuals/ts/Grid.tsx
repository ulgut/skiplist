import React from "react";
import {SkipListC} from "../../skiplist/SkipListC";

class Grid extends React.Component<any, any>{
    private sl: SkipListC;
    constructor(props:any) {
        super(props);
        this.sl = new SkipListC();
    }


    render(){
        return (
            <div>

            </div>
        )
    }

}

export default Grid;