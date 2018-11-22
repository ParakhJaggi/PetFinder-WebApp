import React from 'react';
import {NavBar, SideBar, Footer} from 'js/navigationModules/navigation';
import dogAboutUs from '../../resources/images/dogau.jpg';
import dogAboutUs2 from '../../resources/images/dog2au.jpg';
import dogAboutUs3 from '../../resources/images/dog3au.jpg';
import {Logout} from 'js/profileModules/logoutHelpers';

export class AboutUs extends React.Component {
    render() {
        return (
            <div className="container shiftRight top-buffer">
                <NavBar/>
                <SideBar/>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-12 p-3">
                            <div className="row ">
                                <div className="col-md-8">
                                    <div className="card-block">
                                        <h6 className="card-title text-right"></h6>
                                        <p className="card-text text-justify">Tempeturs was created for Pet Owners and
                                            Sitters
                                            to match. Simply set your availability, and create a booking to accommodate
                                            your furry
                                            friends.</p>
                                        <a href="#" className="btn btn-primary">My Profile</a>
                                    </div>
                                </div>
                                <div className="col-md-4">
                                    <img className="w-100" src={dogAboutUs}/>
                                </div>
                            </div>
                        </div>
                        <div className="card col-md-12 p-3">
                            <div className="row ">
                                <div className="col-md-4">
                                    <img className="w-100" src={dogAboutUs2}/>
                                </div>
                                <div className="col-md-8">
                                    <div className="card-block">
                                        <h6 className="card-title">Rate a Sitter</h6>
                                        <p className="card-text text-justify"> Rate and review sitters to share your
                                            Tempeturs experience! </p>
                                        <a href="/#/review-page" className="btn btn-primary">Rate a Sitter</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="card col-md-12 p-3">
                            <div className="row ">
                                <div className="col-md-4">
                                    <img className="w-100" src={dogAboutUs3}/>
                                </div>
                                <div className="col-md-8">
                                    <div className="card-block">
                                        <h6 className="card-title">Built by the Best</h6>
                                        <p className="card-text text-justify"> Tempeturs was designed and developed by
                                            Ian Laird,
                                            Parakh Jaggi, Garth Terlizzi III, Aidan Edwards, and David Milliard.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <Logout/>
                <Footer/>
            </div>
        );
    }
}