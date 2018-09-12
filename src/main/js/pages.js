import _ from 'lodash';

import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import * as Users from 'js/users';
import * as Login from 'js/login';


export class Home extends React.Component {
	render() {
		/*TODO edit*/
		return (
			<div className="container padded">
				<NavBar></NavBar>
                Welcome to our project!
			</div>
		);
	}
}
export class NavBar extends React.Component {
	render() {
        /*TODO edit*/
		return (
            <div className="container padded">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <a class="navbar-brand " style={{color: 'blue'}}>Navigation</a>
                        <ul class="nav ml-auto">
                            <li><Link to="/register">Register</Link></li>
                            <li><Link to="/login">Login</Link></li>
                            <li><Link to="/profile-page">Edit Profile</Link></li>
                            <li><Link to="/page-2">Page 2</Link></li>
                            <li><Link to="/page-3">Page 3</Link></li>
                        </ul>
                    </div>
                </nav>
            </div>
		);
	}
}
export class RegisterPage extends React.Component {
	render() {
		return (
			<div className="container padded">
                <NavBar></NavBar>
                <div className="row">
					<div className="col-6 offset-md-3">
						<h2>Register</h2>
						<hr />
						<Login.RegistrationForm />
					</div>
				</div>
			</div>
		);
	}
}

export class LoginPage extends React.Component {
	render() {
		return (
			<div className="container padded">
                <NavBar></NavBar>
                <div className="row">
					<div className="col-6 offset-md-3">
						<h2>Login</h2>
						<hr />
						<Login.LoginForm />
					</div>
				</div>
			</div>
		);
	}
}

class ProfilePage extends React.Component {
	render() {
		return (
			<div className="container padded">
                <NavBar></NavBar>
				This is Profile Page.
				This will let users edit photo/add other info

				{ _.isDefined(this.props.authentication) &&
				<div>{this.props.authentication['access_token']}</div>
				}

				{ _.isDefined(this.props.user) &&
				<div>Welcome, {this.props.user.principal}!</div>
				}
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

export { ProfilePage };

export class Page2 extends React.Component {
	render() {
		return (
			<div className="container padded">
                <NavBar></NavBar>
				<Hello></Hello>
			</div>
		);
	}
}

export class Page3 extends React.Component {
	render() {
		return (
			<div className="container padded">
                <NavBar></NavBar>
				This is page 3. Unknown Purpose

			</div>
		);
	}
}

export class Hello extends React.Component {

    constructor(props) {
        super(props);

        this.state = {data: null};
    }

    componentDidMount() {
        this._getData();
    }


    _getData = () => {
        fetch('http://localhost:8000/hello/')
            .then(response => {
                if (response.ok) {
                    return response;
                } else {
                    let errorMessage =
                            '${response.status(${response.statusText})',
                        error = new Error(errorMessage);
                    throw(error);
                }
            })
            .then(response => response.json())
            .then(json =>{
                console.log(json);
                this.setState({ data: json.data });
            });
    }

    render() {
        return (
            <div>
                <h1>{this.state.data}</h1>
            </div>
        );
    }
}