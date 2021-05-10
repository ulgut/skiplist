import React from 'react';
import '../styles/main.scss';

import Grid from "./Grid";

class App extends React.Component<any, any>{
    render(){
        return (
            <div>
                <nav className={"header"}>
                    <h2>Skip Lists by Cole Dumas and Jesse Tuglu</h2>
                </nav>
                <Grid/>
            </div>
        )
    }
}

export default App;