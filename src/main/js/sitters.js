import React from 'react';
import axios from 'axios';

class SitterRow extends React.Component{
    render(){
        return (
            <tr>
                <ti>
                    {this.props.sitter.name}
                </ti>
                <ti>
                    {this.props.sitter.location}
                </ti>
                {this.props.sitter.map((available, index) =>
                    <ti><button onClick={
                        axios.post('/api/book', {
                            sitter:this.props.sitter.name,
                            day: index
                        })} disabled={!available}
                     /></ti>
                )}
            </tr>
        );
    }
}

export class SitterTable extends React.Component{
    state = {
        sitters: []
    };

    componentDidMount() {
        axios.get('/api/user/getavailablesitters')
            .then(res => {
                const sitters = res.users;
                this.setState({sitters: sitters});
            });
    }
    render(){
        return (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Location</th>
                    <th>Sunday</th>
                    <th>Monday</th>
                    <th>Tuesday</th>
                    <th>Wednesday</th>
                    <th>Thursday</th>
                    <th>Friday</th>
                    <th>Saturday</th>
                </tr>
                </thead>
                <tbody>
                    {this.state.sitters.map(sitter =>
                        <SitterRow sitter={sitter}/>
                    )}
                </tbody>
            </table>
        );
    }
}