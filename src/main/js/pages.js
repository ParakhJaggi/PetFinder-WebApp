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
import {SideBar} from 'js/navigation';
import {NavBar} from 'js/navigation';
import {
	ChinchillaSwitch,
	FerretSwitch,
	OtherRodentSwitch,
	HamsterSwitch,
	GuineaPigSwitch,
	RabbitSwitch,
	LocationSlider
} from 'js/switches';
import Slider from 'react-rangeslider';
import WeeklyScheduler from 'react-week-scheduler';
import * as cookie from 'react-cookies';
import {RegistrationPetForm, EditPetForm} from 'js/pet';
import axios from 'axios';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Validation from 'js/alloy/utils/validation';
import * as ReduxForm from 'redux-form';

function logout() {
	cookie.remove('authentication', {path: '/'});
	cookie.remove('user', {path: '/'});
	window.location.reload(true);
}

export class Home extends React.Component {
	render() {
		/*TODO edit*/
		return (
			<div id="wrapper">
				<NavBar></NavBar>
				<SideBar></SideBar>
				<div className="container padded top-buffer">
					Welcome to our project!
				</div>
			</div>

		);
	}
}

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
							<a className="d-block small" href="/#/profile-page">ProfilePage</a>
							<a className="d-block small" href="/#/login">login</a>

						</div>
					</div>
				</div>
			</Pulse>

			</body>
		);
	}
}

const loginRedirectPage = '/profile-page';

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
								<a className="d-block small" href="/#/dashboard">Home</a>
								<a className="d-block small" href="/#/profile-page">ProfilePage</a>

							</div>
						</div>
					</div>
				</div>
			</Pulse>
			</body>
		);
	}
}

var myData;

function loadScheduleFromDatabase() {
	try {
		/*TODO Retrieve Serialized State*/
		//if (serializedState === null)
		// 		myData=null;
		//else
		//	myData=JSON.parse(serializedState);
	} catch (e) {
		console.log(e);
		return undefined;
	}
}

class ProfilePage extends React.Component {
	state = {
		pets: []
	};

	componentDidMount() {
		axios.get('/pets/all')
			.then(res => {
				const pets = res.pets;
				this.setState({pets});
			});
		myData = loadScheduleFromDatabase();
		window.addEventListener('beforeunload', this.saveToDB);

	}

	componentWillUnmount() {
		this.saveToDB();
		window.removeEventListener('beforeunload', this.saveToDB); // remove the event handler for normal unmounting
	}

	saveToDB() { // this will hold the cleanup code
		try {
			const serializedState = JSON.stringify(this.scheduler.state.days);
			/*TODO Add serialized state to user's database*/
		} catch (e) {
			console.log(e);
		}
	}

    onSubmit = pet => {
        return axios.post('/pets', pet);
    };

	render() {
		const startingDefault = {event: 'Not Available', color: '#faf1ff'};
		const blockingEvent = {event: 'Available', color: '#00d7dd'};
		const eventList = [startingDefault, blockingEvent];

        let {handleSubmit, submitting} = this.props;
        let onSuccess = this.props.success;

        return (

			<div className="container padded">
				<NavBar/>
				<SideBar/>
				<div className="top-buffer shiftRight">
					This is Profile Page.
					This will let users edit photo/add other info

				{_.isDefined(this.props.authentication)
					//<div>{this.props.authentication['access_token']}</div>
				}
				{_.isDefined(this.props.user) &&
				<div>Welcome, {this.props.user.principal}!</div>
				}
				{this.state.pets.map(pet =>
                    <EditPetForm pet={pet}/>
				)}
					<WeeklyScheduler
						defaultEvent={startingDefault} selectedEvent={blockingEvent} events={eventList}
						currentSchedule={myData}
						ref={(scheduler) => {
							this.scheduler = scheduler;
						}}
					/>
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

export class RodentSearch extends React.Component {
	render() {
		return (
			<html lang="en">
			<body id="page-top">
			<div id="wrapper">
				<NavBar></NavBar>
				<SideBar></SideBar>
				<div id="content-wrapper">
					<div class="top-buffer">
					</div>
					<div class="container shiftRight">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="#">Dashboard</a>
							</li>
							<li class="breadcrumb-item active">Find Pets</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Rodents
							</div>
							<div class="card-body">
								<table className="table" id="dataTable" width="100%" cellSpacing="0">
									<thead>
									<tr>
										<th className="pl-5"><span>Hamsters</span><HamsterSwitch/></th>
										<th className="pl-5"><span>Rabbits</span><RabbitSwitch/></th>
										<th className="pl-5"><span>Guinea Pigs</span><GuineaPigSwitch/></th>
									</tr>
									<tr>
										<th className="pl-5"><span>Ferrets</span><FerretSwitch/></th>
										<th className="pl-5"><span>Chinchillas</span><ChinchillaSwitch/></th>
										<th className="pl-5"><span>Other</span><OtherRodentSwitch/></th>
									</tr>
									</thead>
								</table>
								<LocationSlider/>
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</thead>
										<tfoot>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</tfoot>
										<tbody>
										<tr>
											<td>Jena Gaines</td>
											<td>Office Manager</td>
											<td>London</td>
											<td>30</td>
											<td>2008/12/19</td>
											<td>$90,560</td>
										</tr>
										<tr>
											<td>Quinn Flynn</td>
											<td>Support Lead</td>
											<td>Edinburgh</td>
											<td>22</td>
											<td>2013/03/03</td>
											<td>$342,000</td>
										</tr>
										<tr>
											<td>Charde Marshall</td>
											<td>Regional Director</td>
											<td>San Francisco</td>
											<td>36</td>
											<td>2008/10/16</td>
											<td>$470,600</td>
										</tr>
										<tr>
											<td>Haley Kennedy</td>
											<td>Senior Marketing Designer</td>
											<td>London</td>
											<td>43</td>
											<td>2012/12/18</td>
											<td>$313,500</td>
										</tr>
										<tr>
											<td>Tatyana Fitzpatrick</td>
											<td>Regional Director</td>
											<td>London</td>
											<td>19</td>
											<td>2010/03/17</td>
											<td>$385,750</td>
										</tr>
										<tr>
											<td>Michael Silva</td>
											<td>Marketing Designer</td>
											<td>London</td>
											<td>66</td>
											<td>2012/11/27</td>
											<td>$198,500</td>
										</tr>
										<tr>
											<td>Paul Byrd</td>
											<td>Chief Financial Officer (CFO)</td>
											<td>New York</td>
											<td>64</td>
											<td>2010/06/09</td>
											<td>$725,000</td>
										</tr>
										<tr>
											<td>Gloria Little</td>
											<td>Systems Administrator</td>
											<td>New York</td>
											<td>59</td>
											<td>2009/04/10</td>
											<td>$237,500</td>
										</tr>
										<tr>
											<td>Bradley Greer</td>
											<td>Software Engineer</td>
											<td>London</td>
											<td>41</td>
											<td>2012/10/13</td>
											<td>$132,000</td>
										</tr>
										<tr>
											<td>Dai Rios</td>
											<td>Personnel Lead</td>
											<td>Edinburgh</td>
											<td>35</td>
											<td>2012/09/26</td>
											<td>$217,500</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
						</div>
					</div>
					<footer class="footer navbar-fixed-bottom">
						<div class="container shiftRight my-auto">
							<div class="copyright text-center my-auto">
								<span>Copyright © Your Website 2018</span>
							</div>
						</div>
					</footer>
				</div>
			</div>
			<a class="scroll-to-top rounded" href="#page-top">
				<i class="fas fa-angle-up"></i>
			</a>

			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			     aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
							<button class="close" type="button" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">Select "Logout" below if you are ready to end your current session.
						</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <button class="btn btn-primary" onClick={()=>logout()}>Logout</button>
						</div>
					</div>
				</div>
			</div>
			</body>
			</html>
		);
	}
}

export class DogSearch extends React.Component {
	render() {
		return (
			<html lang="en">
			<body id="page-top">
			<div id="wrapper">
				<NavBar></NavBar>
				<SideBar></SideBar>
				<div id="content-wrapper">
					<div class="top-buffer">
					</div>
					<div class="container shiftRight">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="#">Dashboard</a>
							</li>
							<li class="breadcrumb-item active">Find Pets</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Dogs
							</div>
							<div class="card-body">
								<table className="table" id="dataTable" width="100%" cellSpacing="0">
									<Pulse>
										<thead>
										<tr>
										</tr>
										</thead>
									</Pulse>
								</table>
								<LocationSlider/>
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</thead>
										<tfoot>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</tfoot>
										<tbody>
										<tr>
											<td>Jena Gaines</td>
											<td>Office Manager</td>
											<td>London</td>
											<td>30</td>
											<td>2008/12/19</td>
											<td>$90,560</td>
										</tr>
										<tr>
											<td>Quinn Flynn</td>
											<td>Support Lead</td>
											<td>Edinburgh</td>
											<td>22</td>
											<td>2013/03/03</td>
											<td>$342,000</td>
										</tr>
										<tr>
											<td>Charde Marshall</td>
											<td>Regional Director</td>
											<td>San Francisco</td>
											<td>36</td>
											<td>2008/10/16</td>
											<td>$470,600</td>
										</tr>
										<tr>
											<td>Haley Kennedy</td>
											<td>Senior Marketing Designer</td>
											<td>London</td>
											<td>43</td>
											<td>2012/12/18</td>
											<td>$313,500</td>
										</tr>
										<tr>
											<td>Tatyana Fitzpatrick</td>
											<td>Regional Director</td>
											<td>London</td>
											<td>19</td>
											<td>2010/03/17</td>
											<td>$385,750</td>
										</tr>
										<tr>
											<td>Michael Silva</td>
											<td>Marketing Designer</td>
											<td>London</td>
											<td>66</td>
											<td>2012/11/27</td>
											<td>$198,500</td>
										</tr>
										<tr>
											<td>Paul Byrd</td>
											<td>Chief Financial Officer (CFO)</td>
											<td>New York</td>
											<td>64</td>
											<td>2010/06/09</td>
											<td>$725,000</td>
										</tr>
										<tr>
											<td>Gloria Little</td>
											<td>Systems Administrator</td>
											<td>New York</td>
											<td>59</td>
											<td>2009/04/10</td>
											<td>$237,500</td>
										</tr>
										<tr>
											<td>Bradley Greer</td>
											<td>Software Engineer</td>
											<td>London</td>
											<td>41</td>
											<td>2012/10/13</td>
											<td>$132,000</td>
										</tr>
										<tr>
											<td>Dai Rios</td>
											<td>Personnel Lead</td>
											<td>Edinburgh</td>
											<td>35</td>
											<td>2012/09/26</td>
											<td>$217,500</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
						</div>
					</div>
					<footer class="footer navbar-fixed-bottom">
						<div class="container shiftRight my-auto">
							<div class="copyright text-center my-auto">
								<span>Copyright © Your Website 2018</span>
							</div>
						</div>
					</footer>
				</div>
			</div>
			<a class="scroll-to-top rounded" href="#page-top">
				<i class="fas fa-angle-up"></i>
			</a>


			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			     aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
							<button class="close" type="button" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">Select "Logout" below if you are ready to end your current session.
						</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <button className="btn btn-primary" onClick={()=>logout()}>Logout</button>
						</div>
					</div>
				</div>
			</div>
			</body>
			</html>
		);
	}
}

export class BirdSearch extends React.Component {
	render() {
		return (
			<html lang="en">
			<body id="page-top">
			<div id="wrapper">
				<NavBar></NavBar>
				<SideBar></SideBar>
				<div id="content-wrapper">
					<div class="top-buffer">
					</div>
					<div class="container shiftRight">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="#">Dashboard</a>
							</li>
							<li class="breadcrumb-item active">Find Pets</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Birds
							</div>
							<div class="card-body">
								<table className="table" id="dataTable" width="100%" cellSpacing="0">
									<thead>
									<tr>
									</tr>
									</thead>
								</table>
								<LocationSlider/>
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</thead>
										<tfoot>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</tfoot>
										<tbody>
										<tr>
											<td>Jena Gaines</td>
											<td>Office Manager</td>
											<td>London</td>
											<td>30</td>
											<td>2008/12/19</td>
											<td>$90,560</td>
										</tr>
										<tr>
											<td>Quinn Flynn</td>
											<td>Support Lead</td>
											<td>Edinburgh</td>
											<td>22</td>
											<td>2013/03/03</td>
											<td>$342,000</td>
										</tr>
										<tr>
											<td>Charde Marshall</td>
											<td>Regional Director</td>
											<td>San Francisco</td>
											<td>36</td>
											<td>2008/10/16</td>
											<td>$470,600</td>
										</tr>
										<tr>
											<td>Haley Kennedy</td>
											<td>Senior Marketing Designer</td>
											<td>London</td>
											<td>43</td>
											<td>2012/12/18</td>
											<td>$313,500</td>
										</tr>
										<tr>
											<td>Tatyana Fitzpatrick</td>
											<td>Regional Director</td>
											<td>London</td>
											<td>19</td>
											<td>2010/03/17</td>
											<td>$385,750</td>
										</tr>
										<tr>
											<td>Michael Silva</td>
											<td>Marketing Designer</td>
											<td>London</td>
											<td>66</td>
											<td>2012/11/27</td>
											<td>$198,500</td>
										</tr>
										<tr>
											<td>Paul Byrd</td>
											<td>Chief Financial Officer (CFO)</td>
											<td>New York</td>
											<td>64</td>
											<td>2010/06/09</td>
											<td>$725,000</td>
										</tr>
										<tr>
											<td>Gloria Little</td>
											<td>Systems Administrator</td>
											<td>New York</td>
											<td>59</td>
											<td>2009/04/10</td>
											<td>$237,500</td>
										</tr>
										<tr>
											<td>Bradley Greer</td>
											<td>Software Engineer</td>
											<td>London</td>
											<td>41</td>
											<td>2012/10/13</td>
											<td>$132,000</td>
										</tr>
										<tr>
											<td>Dai Rios</td>
											<td>Personnel Lead</td>
											<td>Edinburgh</td>
											<td>35</td>
											<td>2012/09/26</td>
											<td>$217,500</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
						</div>
					</div>
					<footer class="footer navbar-fixed-bottom">
						<div class="container shiftRight my-auto">
							<div class="copyright text-center my-auto">
								<span>Copyright © Your Website 2018</span>
							</div>
						</div>
					</footer>
				</div>
			</div>
			<a class="scroll-to-top rounded" href="#page-top">
				<i class="fas fa-angle-up"></i>
			</a>


			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			     aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
							<button class="close" type="button" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">Select "Logout" below if you are ready to end your current session.
						</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <button className="btn btn-primary" onClick={()=>logout()}>Logout</button>
						</div>
					</div>
				</div>
			</div>
			</body>
			</html>
		);
	}
}

export class CatSearch extends React.Component {
	render() {
		return (
			<html lang="en">
			<body id="page-top">
			<div id="wrapper">
				<NavBar></NavBar>
				<SideBar></SideBar>
				<div id="content-wrapper">
					<div class="top-buffer">
					</div>
					<div class="container shiftRight">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="#">Dashboard</a>
							</li>
							<li class="breadcrumb-item active">Find Pets</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Cats
							</div>
							<div class="card-body">
								<table className="table" id="dataTable" width="100%" cellSpacing="0">
									<thead>

									</thead>
								</table>

								<LocationSlider/>
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
										<thead>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</thead>
										<tfoot>
										<tr>
											<th>Name</th>
											<th>Position</th>
											<th>Office</th>
											<th>Age</th>
											<th>Start date</th>
											<th>Salary</th>
										</tr>
										</tfoot>
										<tbody>
										<tr>
											<td>Jena Gaines</td>
											<td>Office Manager</td>
											<td>London</td>
											<td>30</td>
											<td>2008/12/19</td>
											<td>$90,560</td>
										</tr>
										<tr>
											<td>Quinn Flynn</td>
											<td>Support Lead</td>
											<td>Edinburgh</td>
											<td>22</td>
											<td>2013/03/03</td>
											<td>$342,000</td>
										</tr>
										<tr>
											<td>Charde Marshall</td>
											<td>Regional Director</td>
											<td>San Francisco</td>
											<td>36</td>
											<td>2008/10/16</td>
											<td>$470,600</td>
										</tr>
										<tr>
											<td>Haley Kennedy</td>
											<td>Senior Marketing Designer</td>
											<td>London</td>
											<td>43</td>
											<td>2012/12/18</td>
											<td>$313,500</td>
										</tr>
										<tr>
											<td>Tatyana Fitzpatrick</td>
											<td>Regional Director</td>
											<td>London</td>
											<td>19</td>
											<td>2010/03/17</td>
											<td>$385,750</td>
										</tr>
										<tr>
											<td>Michael Silva</td>
											<td>Marketing Designer</td>
											<td>London</td>
											<td>66</td>
											<td>2012/11/27</td>
											<td>$198,500</td>
										</tr>
										<tr>
											<td>Paul Byrd</td>
											<td>Chief Financial Officer (CFO)</td>
											<td>New York</td>
											<td>64</td>
											<td>2010/06/09</td>
											<td>$725,000</td>
										</tr>
										<tr>
											<td>Gloria Little</td>
											<td>Systems Administrator</td>
											<td>New York</td>
											<td>59</td>
											<td>2009/04/10</td>
											<td>$237,500</td>
										</tr>
										<tr>
											<td>Bradley Greer</td>
											<td>Software Engineer</td>
											<td>London</td>
											<td>41</td>
											<td>2012/10/13</td>
											<td>$132,000</td>
										</tr>
										<tr>
											<td>Dai Rios</td>
											<td>Personnel Lead</td>
											<td>Edinburgh</td>
											<td>35</td>
											<td>2012/09/26</td>
											<td>$217,500</td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
						</div>
					</div>
					<footer class="footer navbar-fixed-bottom">
						<div class="container my-auto">
							<div class="copyright text-center my-auto">
								<span>Copyright © Your Website 2018</span>
							</div>
						</div>
					</footer>
				</div>
			</div>
			<a class="scroll-to-top rounded" href="#page-top">
				<i class="fas fa-angle-up"></i>
			</a>


			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			     aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
							<button class="close" type="button" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">Select "Logout" below if you are ready to end your current session.
						</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <button className="btn btn-primary" onClick={()=>logout()}>Logout</button>
						</div>
					</div>
				</div>
			</div>
			</body>
			</html>
		);
	}
}


export class Dashboard extends React.Component {
	render() {
		return (

			<html lang="en">
			<body id="page-top">
			<div id="wrapper">
				<NavBar></NavBar>
				<SideBar></SideBar>
				<div id="content-wrapper">
					<div class="top-buffer">
					</div>
					<div class="container shiftRight">
						<ol class="breadcrumb">
							<li class="breadcrumb-item">
								<a href="#">Dashboard</a>
							</li>
							<li class="breadcrumb-item active">Find Pets</li>
						</ol>

						<Pulse>
							<div class="row justify-content-center ">
								<div class="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
									<a className="card-footer text-black-50 clearfix small z-1" href="#/dog-search">
										<span className="float-left"><b>Dogs</b></span>
										<span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
									</a>
									<div class="card-body align-bottom">
										<div className="card-img-overlay dog-crop align-bottom">
											<img src={'https://i.postimg.cc/DwXpZCDK/dogs.png'}
											     className=" dog-crop card-img-bottom"></img>
										</div>
									</div>

								</div>
								<div className="pr-2"></div>
								<div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
									<a className="card-footer text-black-50 clearfix small z-1" href="#/cat-search">
										<span className="float-left"><b>Cats</b></span>
										<span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
									</a>
									<div className="card-body">
										<div className="card-img-overlay">
											<img src={'https://i.postimg.cc/Jn1ppRh1/cats.png'}
											     className="card-img-bottom"></img>
										</div>
									</div>
								</div>
							</div>
							<div class="row justify-content-center ">
								<div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
									<a className="card-footer text-black-50 clearfix small z-1" href="#/rodent-search">
										<span className="float-left"><b>Rodents</b></span>
										<span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
									</a>
									<div className="card-body">
										<div className="card-img-overlay">
											<img src={'https://i.postimg.cc/kMLpm31y/bunny.png'}
											     className="card-img-bottom bunny-crop "></img>
										</div>
									</div>

								</div>
								<div className="pr-2"></div>
								<div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">

									<a className="card-footer text-black-50 clearfix small z-1" href="#/bird-search">
										<span className="float-left"><b>Birds</b></span>
										<span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
									</a>
									<div className="card-body">
										<div className="card-img-overlay">
											<img src={'https://i.postimg.cc/QxRSRQMY/birds.png'}
											     className="card-img-bottom portrait-crop"></img>
										</div>
									</div>
								</div>
							</div>
							<div className="row justify-content-center ">
								<div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
									<a className="card-footer text-black-50 clearfix small z-1" href="#/rodent-search">
										<span className="float-left"><b>Location Search</b></span>
										<span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
									</a>
									<div className="card-body">
										<div className="card-img-overlay">
											<img src={'https://i.postimg.cc/R0N0KMMq/traveling-with-animals.jpg'}
											     className="card-img-bottom bunny-crop "></img>
										</div>
									</div>

								</div>
								<div className="pr-2"></div>
								<div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">

									<a className="card-footer text-black-50 clearfix small z-1" href="#">
										<span className="float-left"><b>Advanced Search</b></span>
										<span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
									</a>
									<div className="card-body">
										<div className="card-img-overlay">
											<img
												src={'https://i.postimg.cc/JhZjZc7s/3958_D90_D1601-_D4_F3-_A5_A6-_D7_FB-872_BF841028_B.png'}
												className="card-img-bottom mag-crop"></img>
										</div>
									</div>
								</div>
							</div>
						</Pulse>

						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-chart-area"></i>
								Bar Chart Example
							</div>
							<div class="card-body">
								<Layout></Layout>

							</div>
							<div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
						</div>
					</div>

					<footer class="footer navbar-fixed-bottom">
						<div class="container shiftRight my-auto">
							<div class="copyright text-center my-auto">
								<span>Copyright © Your Website 2018</span>
							</div>
						</div>
					</footer>

				</div>


			</div>


			<a class="scroll-to-top rounded" href="#page-top">
				<i class="fas fa-angle-up"></i>
			</a>


			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
			     aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
							<button class="close" type="button" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">Select "Logout" below if you are ready to end your current session.
						</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            <button className="btn btn-primary" onClick={()=>logout()}>Logout</button>
						</div>
					</div>
				</div>
			</div>


			</body>

			</html>


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
		fetch('/hello/')
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
			.then(json => {
				console.log(json);
				this.setState({data: json.data});
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

var Chart = require('chart.js');

export class AboutUs extends React.Component {
	render() {
		return (
			<div>
				<NavBar/>
				<SideBar/>
			</div>
		);
	}
}

export class AddPet extends React.Component {
	render() {
		return (

			<div className="container shiftRight top-buffer">
				<NavBar/>
				<SideBar/>

				<div className="card">
					<RegistrationPetForm/>
				</div>
			</div>
		);
	}
}

class Layout extends React.Component {
	constructor(props) {
		super(props);
	}

	componentDidMount() {
		const node = this.node;

		var myChart = new Chart(node, {
			type: 'bar',
			data: {
				labels: ['Red', 'Blue', 'Yellow', 'Green'],
				datasets: [
					{
						label: '# of Likes',
						data: [12, 19, 7, 13],
						backgroundColor: [
							'rgba(255, 99, 132, 0.9)',
							'rgba(54, 162, 235, 0.9)',
							'rgba(255, 206, 86, 0.9)',
							'rgba(30,190,50,0.9)'
						]
					}
				]
			}
		});
	}

	render() {
		return (
			<div>
				<canvas
					style={{width: 800, height: 300}}
					ref={node => (this.node = node)}
				/>
			</div>
		);
	}
}


