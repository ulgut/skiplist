import React from 'react';
import '../styles/main.scss';

import Grid from "./Grid";

class App extends React.Component<any, any>{
    constructor(props: any) {
        super(props);
    }

    render(){
        return (
            <div>
                <nav className={"nav"}>
                    Skip Lists
                </nav>
                <Grid/>
            </div>
        )
    }
}

export default App;