import React from 'react';
import {NavBar, SideBar} from 'js/navigationModules/navigation';
import dogAboutUs from '../../resources/images/dogau.jpg';
import axios from 'axios';
import * as cookie from "react-cookies";

function DeleteAccount() {
	axios.get('/api/user/deleteThisUser').then( res => {
		window.alert('You have been deleted');
		cookie.remove('authentication', {path: '/'});
		cookie.remove('user', {path: '/'});
		window.location.replace('...');
	});
}

export class Settings extends React.Component {
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
										<p className="card-text text-justify">If you want to delete your account please click the button.</p>
										<div className="btn btn-primary" onClick={DeleteAccount}>Delete Account</div>
									</div>
								</div>
								<div className="col-md-4">
									<img className="w-100" src={dogAboutUs}/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}
}



