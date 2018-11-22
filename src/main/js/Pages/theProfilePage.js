import axios from 'axios';
import React from 'react';
import {Footer, NavBar, SideBar} from 'js/navigationModules/navigation';
import _ from 'lodash';
import {EditPetForm} from 'js/petModules/pet';
import {BookingTable} from 'js/profileModules/bookings';
import {SitterTable} from 'js/profileModules/sitters';
import connect from 'react-redux/es/connect/connect';
import * as Users from 'js/profileModules/users';


function ClearNotification() {
    axios.get('/api/user/clearnotifications');
    location.reload();
}

class ProfilePage extends React.Component {
    constructor(props) {
        super(props);
        /* set the initial checkboxState to true */
        this.state = {
            available: [false, false, false, false, false, false, false]
        };
    }

    componentDidMount() {
        axios.get('/api/user/getDays')
            .then(res => {
                const bools = res.bools;
                this.setState({
                    available: bools
                });
            });
        axios.get('/api/user')
            .then(res => {
                console.log(res);
                this.setState({user: res});
            });
        axios.get('/api/userPets/ugly')
            .then(res => {
                console.log(res);
                this.setState({pets: res.pets});
            });
    }


    onSubmit(event) {
        console.log('Submitting');
        event.preventDefault();
        let toPost = {
            'bools': this.state.available
        };
        axios.post('/api/user/setdays', toPost);
    }

    /* callback to change the checkboxState to false when the checkbox is checked */
    toggleAvailable(day, event) {
        let newAvailable = this.state.available;
        newAvailable[day] = !newAvailable[day];
        this.setState({
            available: newAvailable
        });
    }

    render() {
        let availableCheck = [];
        const week = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
        for (let i = 0; i < 7; i++) {
            availableCheck.push(
                <h3><label className="container-checkbox">{week[i]}
                    <input type="checkbox" checked={this.state.available[i]}
                           onClick={this.toggleAvailable.bind(this, i)}/>
                    <span className="checkmark"></span>
                </label></h3>
            );
        }
        return (

            <div className="container padded">
                <NavBar/>
                <SideBar/>
                <div className="top-buffer shiftRight">
                    {_.isDefined(this.props.authentication)
                        //<div>{this.props.authentication['access_token']}</div>
                    }
                    {_.isDefined(this.props.user) &&
                    <div className="card mb-3"><div className="card-header">
                        <i className="fas fa-table"></i>Profile</div>
                            <table className="table" id="dataTable" width="100%" cellSpacing="0">
                                <thead>
                                <tr>
                                    <td>Email</td><td>Type</td>
                                </tr>
                                </thead>
                                <tbody>
                            <tr><td>{this.props.user.principal}</td>
                                <td>{this.props.user.type}</td></tr></tbody>
                            </table>
                    </div>
                    }
                    {
                        this.state.user && this.state.user.notification &&
                        <div>
                            <div className="card mb-3">
                            <div className="card-header">
                                <i className="fas fa-table"></i>
                                Your Current Notifications <br/>
                                <button className="btn btn-primary" onClick={() => ClearNotification()}>Clear Notifications</button>
                            </div>
                            <div className='pl-3 pr-3 pt-3'> {this.state.user.notification.map(test =>
                            <tr>
                                <td>{test.toString()}</td>
                            </tr>

                        )}</div>

                            </div>

                        </div>
                    }

                    <div className="card mb-3">
                        <div className="card-header">
                            <i className="fas fa-table"></i>
                            My Pets
                        </div>
                        <div className='pl-3 pr-3 pt-3'>
                    {
                        this.state.pets &&
                        this.state.pets.map(pet =>
                            <EditPetForm pet={pet}/>
                        )
                    }
                        </div>
                    </div>
                    <div className="card mb-3">
                        <div className="card-header">
                            <i className="fas fa-table"></i>
                            My Availability
                        </div>
                        <div className="card-body justify-content-center">
                            <form onSubmit={this.onSubmit.bind(this)}>
                                {availableCheck.map(checkbox => {
                                    return checkbox;
                                })}
                                <button type="submit">Save</button>
                            </form>
                        </div>
                    </div>
                    {
                        this.state.user &&
                        <React.Fragment>
                            {
                                this.state.user.type === 'SITTER' &&
                                'You are a sitter.'
                            }
                            <BookingTable user={this.state.user}/>
                            {
                                this.state.user.type === 'OWNER' &&
                                <SitterTable/>
                            }
                        </React.Fragment>
                    }
                    <Footer/>
                </div>
            </div>
        );
    }
}

ProfilePage = connect(
    state => ({
        authentication: Users.State.getAuthentication(state),
        user: Users.State.getUser(state)
    })
)(ProfilePage);

export {ProfilePage};