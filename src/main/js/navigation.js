import _ from 'lodash';

import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';
import * as Users from 'js/users';
import * as Login from 'js/login';
import 'js/dogs2.jpg';
import 'js/cats.png';
import 'js/bunny.png';
import 'js/birds.png';
import Pulse from 'react-reveal/Pulse';
import 'bootstrap';
import 'jquery';
import 'js/sb-admin';
import 'react-chartjs-2';
import {LoginForm} from 'js/login';
import {RegistrationForm} from 'js/login';
import {Redirect} from 'react-router-dom';
import Switch from 'react-switch';


export class NavBar extends React.Component {
	render() {
		/*TODO edit*/
		return (
			<div className="fixed-top">
				<nav className="navbar navbar-expand navbar-light bg-blue">

					<a className="navbar-brand mr-1" href="#/dashboard">Tempeturs</a>
					<form className="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
						<div className="input-group">
						</div>
					</form>
					<ul className="navbar-nav ml-auto ml-md-0">
						<li className="nav-item dropdown no-arrow mx-1">
							<a className="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button"
							   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i className="fas fa-bell fa-fw"></i>
								<span className="badge badge-danger">9+</span>
							</a>
							<div className="dropdown-menu dropdown-menu-right" aria-labelledby="alertsDropdown">
								<a className="dropdown-item" href="#">Action</a>
								<a className="dropdown-item" href="#">Another action</a>
								<div className="dropdown-divider"></div>
								<a className="dropdown-item" href="#">Something else here</a>
							</div>
						</li>
						<li className="nav-item dropdown no-arrow">
							<a className="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
							   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i className="fas fa-user-circle fa-fw  "></i>
							</a>
							<div className="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
								<a className="dropdown-item" href="#/profile-page">Settings</a>
								<a className="dropdown-item" href="#">Activity Log</a>
								<div className="dropdown-divider"></div>
								<a className="dropdown-item" href="#" data-toggle="modal"
								   data-target="#logoutModal">Logout</a>
							</div>
						</li>
					</ul>
				</nav>
			</div>
		);
	}
}

export class SideBar extends React.Component {
	render() {
		/*TODO edit*/
		return (
			<ul className="sidebar navbar-nav sticky fixed-top">
				<div className="top-buffer"></div>
				<li className="nav-item active">
					<a className="nav-link" href="#/dashboard">
						<i className="fas fa-fw fa-tachometer-alt"></i>
						<span>Dashboard</span>
					</a>
				</li>
				<li className="nav-item dropdown">
					<a className="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button"
					   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<i className="fas fa-fw fa-bone"></i>
						<span>Find a Pet</span>
					</a>
					<div className="dropdown-menu" aria-labelledby="pagesDropdown">
						<h6 className="dropdown-header">Find a Pet:</h6>
						<a className="dropdown-item" href="#/dog-search">Dogs</a>
						<a className="dropdown-item" href="#/cat-search">Cats</a>
						<a className="dropdown-item" href="#/rodent-search">Rodents</a>
						<a className="dropdown-item" href="#/other-search">Other</a>
						<div className="dropdown-divider"></div>
						<h6 className="dropdown-header"></h6>
						<a className="dropdown-item" href="#/location-search">Location Search</a>
						<a className="dropdown-item" href="#/advanced-search">Advanced Search</a>
					</div>
				</li>
				<li className="nav-item">
					<a className="nav-link" href="#/sits">
						<i className="fas fa-fw fa-chart-area"></i>
						<span>Add Pet</span></a>
				</li>
				<li className="nav-item">
					<a className="nav-link" href="#/about-us">
						<i className="fas fa-fw fa-table"></i>
						<span>About Us</span></a>
				</li>
			</ul>
		);
	}
}