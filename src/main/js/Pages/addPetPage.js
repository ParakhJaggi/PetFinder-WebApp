import React from 'react';
import {NavBar, SideBar, Footer} from 'js/navigationModules/navigation';
import {RegistrationPetForm} from 'js/petModules/pet';
import {Logout} from 'js/profileModules/logoutHelpers';

export class AddPet extends React.Component {
	render() {
		return (
			<div>
				<div className="container shiftRight top-buffer">
					<NavBar/>
					<SideBar/>
					<ol className="breadcrumb">
						<li className="breadcrumb-item">
							<a href="#/sits">Add Pet</a>
						</li>
					</ol>
					<div className="card mb-3">
						<div className="card-header">
							<i className="fas fa-table"></i>
							Add Pet
						</div>
						<div className='pl-3 pr-3 pt-3'>
							<RegistrationPetForm/>
						</div>

					</div>
					<Logout/>

				</div>
				<Footer/>
			</div>
		);
	}
}