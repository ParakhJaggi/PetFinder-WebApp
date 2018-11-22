import React from 'react';
import {Redirect} from 'react-router-dom';
import Pulse from 'react-reveal/Pulse';
import {RegistrationForm} from 'js/profileModules/login';
import {Logout} from 'js/profileModules/logoutHelpers';

const registerRedirectPage = '/profile-page';

export class RegisterPage extends React.Component {
    state = {
        shouldRedirect: false
    }
    setRedirect = () => {
        this.setState({shouldRedirect: true});
    }
    redirectPage = () => {
        if (this.state.shouldRedirect) {
            return <Redirect to={registerRedirectPage}/>;
        }
    }

    render() {
        return (

            <body className="register-background fixed-top " id="page-top">
            {this.redirectPage()}
            <Pulse>
                <div className="myContainer pull-left">
                    <div className="card card-login mx-auto mt-9">
                        <div className="card-header">Register</div>
                        <div className="card-body">
                            <RegistrationForm/>
                            <a className="d-block small" href="/#/login">login</a>

                        </div>

                    </div>
                </div>
            </Pulse>
            <Logout/>

            </body>
        );
    }
}