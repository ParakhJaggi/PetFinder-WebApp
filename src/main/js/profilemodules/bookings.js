import React from 'react';
import axios from 'axios';

class BookingRow extends React.Component{
    renderDay(index, val){
        switch(val){
            case 1:
                return(
                    'Accept'
                );
            case 2:
                return(
                    'Cancel'
                );
            default:
                return;
        }
    }
    render(){
        return (
            <tr>
                <td>
                    {this.props.user}
                </td>
                {this.props.data.map((val, index) =>
                    <td>
                        {this.renderDay(index, val)}
                    </td>
                )}
            </tr>
        );
    }
}
export class BookingTable extends React.Component{
    state = {
        rowdata: new Map()
    };
    updateDays(principal, data, val){
        for(let i = 0; i < 7; i++){
            if(data[i])
                this.state.rowdata.get(principal)[i] = val;
        }
    }
    render(){
        let self = this;
        this.props.user.requestedBookings.forEach(function(request) {
            if(!(self.state.rowdata.has(request.principal))){
                self.state.rowdata.set(request.principal, [0,0,0,0,0,0,0]);
            }
            self.updateDays(request.principal, request.days, 1);
        });
        return (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Monday</th>
                    <th>Tuesday</th>
                    <th>Wednesday</th>
                    <th>Thursday</th>
                    <th>Friday</th>
                    <th>Saturday</th>
                    <th>Sunday</th>
                </tr>
                </thead>
                <tbody>
                    {Array.from(this.state.rowdata).map((pair) =>
                        <BookingRow user={pair[0]} data={pair[1]}/>
                    )}
                </tbody>
            </table>
        );
    }
}