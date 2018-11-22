import React from 'react';
import 'bootstrap';
import 'jquery';
import 'js/navigationModules/sb-admin';
import 'react-chartjs-2';
import * as cookie from 'react-cookies';

function logout() {
	cookie.remove('authentication', {path: '/'});
	cookie.remove('user', {path: '/'});
	window.location.replace('...');
}


export class Logout extends React.Component {
	render() {
		return (
			<div className="modal fade" id="logoutModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			     aria-hidden="true">
				<div className="modal-dialog" role="document">
					<div className="modal-content">
						<div className="modal-header">
							<h5 className="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
							<button className="close" type="button" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div className="modal-body">Select "Logout" below if you are ready to end your current session.
						</div>
						<div className="modal-footer">
							<button className="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
							<button className="btn btn-primary" onClick={() => logout()}>Logout</button>
						</div>
					</div>
				</div>
			</div>);
	}
}

