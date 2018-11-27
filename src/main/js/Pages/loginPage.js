import React from 'react';
import {Redirect} from 'react-router-dom';
import Pulse from 'react-reveal/Pulse';
import {LoginForm} from 'js/profileModules/login';
import {Logout} from 'js/profileModules/logoutHelpers';

const loginRedirectPage = '/about-us';

export class LoginPage extends React.Component {
	state = {
		shouldRedirect: false
	}
	setRedirect = () => {
		this.setState({shouldRedirect: true});
	}
	redirectPage = () => {
		if (this.state.shouldRedirect) {
			return <Redirect to={loginRedirectPage}/>;
		}
	}

	render() {
		return (
			<body className="login-background fixed-top " id="page-top">
			{this.redirectPage()}
			<Pulse>
				<div className="container justify-content-center">
					<div className="card card-login mx-auto mt-5">
						<div className="card-header">Login</div>
						<div className="card-body">
							<LoginForm success={this.setRedirect}/>
							<div className="text-center">
								<a className="d-block small mt-3" href="#/register">Register an Account</a>
								<a className="d-block small" href="forgot-password.html">Forgot Password?</a>
							</div>
						</div>
					</div>
				</div>
			</Pulse>
			<Logout/>
			</body>
		);
	}
}