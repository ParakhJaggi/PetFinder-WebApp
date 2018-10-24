import React from 'react';
import axios from 'axios';

class BookingRow extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
            user: this.props.user,
            days: [0,0,0,0,0,0,0]
        }
    }
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
                    {this.state.user}
                </td>
                {this.state.days.map((index, val) =>
                    <td>
                        {this.renderDay(index, val)}
                    </td>
                )}
            </tr>
        );
    }
}
class BookingTable extends React.Component{
    render(){
        let rows = {};
        this.props.user.requestedBookings.forEach(function(request) {
            if(!rows.hasOwnProperty(request.principal)){
                rows[request.principal] = <BookingRow user=request.principal/>;
            }
            rows[request.principal].update(request);
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
                    {rows.map((user, row) =>
                        row
                    )}
                </tbody>
            </table>
        );
    }
}