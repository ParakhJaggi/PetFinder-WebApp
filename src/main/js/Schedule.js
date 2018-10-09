import React from 'react';
import Switch from 'react-switch';
import {MySwitch} from "js/switches";

export class Schedule extends React.Component{
    boolean checked;
    render(){
        return(
            <div className="table-responsive">
                <tr>
                    <td id="switch1">
                        <MySwitch/>
                    </td>
                </tr>
            </div>
        );
    }

}
