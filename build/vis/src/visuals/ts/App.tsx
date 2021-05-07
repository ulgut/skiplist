import React from 'react';
import '../styls/main.scss';

import Grid from "./Grid";

class App extends React.Component<any, any>{
    constructor(props: any) {
        super(props);
    }

    render(){
        return (
            <div>
                <nav className={"nav"}>
                    Skip Lists by Cole Dumas and Jesse Tuglu
                </nav>
                <Grid/>
            </div>
        )
    }
}

export default App;