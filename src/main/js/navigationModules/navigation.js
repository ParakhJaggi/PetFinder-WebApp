import React from 'react';
import 'bootstrap';
import 'jquery';
import 'js/navigationModules/sb-admin';
import 'react-chartjs-2';

export class NavBar extends React.Component {
	render() {
		return (
			<div className="fixed-top">
				<nav className="navbar navbar-expand navbar-light bg-blue">

					<a className="navbar-brand mr-1" href="#/dashboard">Tempeturs</a>
					<form className="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
						<div className="input-group">
						</div>
					</form>
					<ul className="navbar-nav ml-auto ml-md-0">
						<li className="nav-item dropdown no-arrow">
							<a className="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
							   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								<i className="fas fa-user-circle fa-fw  "></i>
							</a>
							<div className="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
								<a className="dropdown-item" href="#/profile-page">Settings</a>
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
					</div>
				</li>
				<li className="nav-item">
					<a className="nav-link" href="#/sits">
						<i className="fas fa-fw fa-chart-area"></i>
						<span>Add Pet</span></a>
				</li>
				<li className="nav-item">
					<a className="nav-link" href="#/review-page">
						<i className="fas fa-fw fa-table"></i>
						<span>Leave a Review</span></a>
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

export class Footer extends React.Component{
	render(){
		return(<footer className="footer navbar-fixed-bottom">
            <div className="container shiftRight my-auto">
                <div className="copyright text-center my-auto">
                    <span>Tempeturs - 2018</span>
                </div>
            </div>
        </footer>);
	}
}