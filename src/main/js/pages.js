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
import {SitterTable} from 'js/sitters';

function logout() {
	cookie.remove('authentication', {path: '/'});
	cookie.remove('user', {path: '/'});
	window.location.replace('...');
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
			<Logout/>
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
			<Logout/>
			</body>
		);
	}
}



class ProfilePage extends React.Component {
    constructor(props) {
        super(props);
        /* set the initial checkboxState to true */
        this.state = {
            mon: false,
            tues: false,
            wed: false,
            thurs: false,
            fri: false,
            sat: false,
            sun: false,
            pets: [],
        };

        axios.get('/api/user/getDays')
            .then(res => {
                const bools = res.bools;
                this.setState({mon: bools[0]});
                this.setState({tues: bools[1]});
                this.setState({wed: bools[2]});
                this.setState({thurs: bools[3]});
                this.setState({fri: bools[4]});
                this.setState({sat: bools[5]});
                this.setState({sun: bools[6]});
            });
    }


    onSubmit(event) {
    	console.log('Submitting');
        event.preventDefault();
        let toPost = {
            'bools':[this.state.mon,
            this.state.tues,
            this.state.wed,
            this.state.thurs,
            this.state.fri,
            this.state.sat,
			this.state.sun]
        };
        console.log(toPost);

        axios.post('/api/user/setdays',toPost);
    }
    /* callback to change the checkboxState to false when the checkbox is checked */
    toggleMonday(event) {
        this.setState({
            mon: !this.state.mon
        });
    }
    toggleTuesday(event) {
        this.setState({
            tues: !this.state.tues
        });
    }
    toggleWednesday(event) {
        this.setState({
            wed: !this.state.wed
        });
    }
    toggleThursday(event) {
        this.setState({
            thurs: !this.state.thurs
        });
    }
    toggleFriday(event) {
        this.setState({
            fri: !this.state.fri
        });
    }
    toggleSaturday(event) {
        this.setState({
            sat: !this.state.sat
        });
    }
    toggleSunday(event) {
        this.setState({
            sun: !this.state.sun
        });
    }

	render() {
        {console.log(this.state.tues);}
        let Mondaycheckbox = (
            <span>
        <input
            type="checkbox"
			checked={this.state.mon}
            onClick={this.toggleMonday.bind(this)}
        />
        <label>Monday</label>
      </span>
        );
        let Tuesdaycheckbox = (
            <span>
        <input
            type="checkbox"
            checked={this.state.tues}
            onClick={this.toggleTuesday.bind(this)}
        />
        <label>Tuesday</label>
      </span>
        );
        let Wednesdaycheckbox = (
            <span>
        <input
            type="checkbox"
            checked={this.state.wed}
            onClick={this.toggleWednesday.bind(this)}
        />
        <label>Wednesday</label>
      </span>
        );
        let Thursdaycheckbox = (
            <span>
        <input
            type="checkbox"
            checked={this.state.thurs}
            onClick={this.toggleThursday.bind(this)}
        />
        <label>Thursday</label>
      </span>
        );
        let Fridaycheckbox = (
            <span>
        <input
            type="checkbox"
            checked={this.state.fri}
            onClick={this.toggleFriday.bind(this)}
        />
        <label>Friday</label>
      </span>
        );
        let Saturdaycheckbox = (
            <span>
        <input
            type="checkbox"
            checked={this.state.sat}
            onClick={this.toggleSaturday.bind(this)}
        />
        <label>Saturday</label>
      </span>
        );
        const Sundaycheckbox = (
            <span>
        <input
            type="checkbox"
            defaultChecked={this.state.sun}
            onClick={this.toggleSunday.bind(this)}
        />
        <label>Sunday</label>
      </span>
        );

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
					{_.isDefined(this.props.user) &&
					<div>Do you have a notification? {this.props.user.notification}!</div>
					}
				{this.state.pets.map(pet =>
                    <EditPetForm pet={pet}/>
				)}
                    <div>
                        <form onSubmit={this.onSubmit.bind(this)}>
							<ul>
								<li>{Mondaycheckbox}</li>
                                <li>{Tuesdaycheckbox}</li>
                                <li>{Wednesdaycheckbox}</li>
                                <li>{Thursdaycheckbox}</li>
                                <li>{Fridaycheckbox}</li>
                                <li>{Saturdaycheckbox}</li>
                                <li>{Sundaycheckbox}</li>
							</ul>
                            <button type="submit">Save</button>
                        </form>
                    </div>
                    {
                        this.state.user &&
                        this.state.user.type === 'SITTER' &&
                        'You are a sitter.'
                    }
					{
                        this.state.user &&
                        this.state.user.type === 'OWNER' &&
                        <SitterTable />
					}
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
    state = {
        rodents: []
    };

    componentDidMount() {
        axios.get('/pets/rodents')
            .then(res => {
                const pets = res.pets;
                console.log(pets);
                this.setState({rodents: pets});
            });
    }
	filterHamster(){
    	this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype == 'hamster')});
    	console.log('hi');
    	window.reload();
	}
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
                                <div className="table-responsive">
                                    <table className="table table-bordered" id="dataTable" width="90%" cellSpacing="0">
                                        <Pulse>
                                            <thead>
                                            <tr>
                                                <td>Name</td>
                                                <td>Owner</td>
                                                <td>Subtype</td>
                                                <td>Preferences</td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {this.state.rodents.sort((a, b) => a.name > b.name).map(pet =>
                                                <tr>
                                                    <td>{pet.name}</td>
                                                    <td>{pet.owner}</td>
                                                    <td>{pet.subtype}</td>
                                                    <td>{pet.preferences}</td>
                                                </tr>
                                            )}
                                            </tbody>
                                        </Pulse>
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

			<Logout/>
			</body>
			</html>
		);
	}
}

export class DogSearch extends React.Component {
    state = {
        dogs: []
    };

    componentDidMount() {
        axios.get('/pets/dogs')
            .then(res => {
                const pets = res.pets;
                console.log(pets);
                this.setState({dogs: pets});
            });
    }

	render() {
		return (
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
								<LocationSlider/>
                                <div className="table-responsive">
                                    <table className="table table-bordered" id="dataTable" width="90%" cellSpacing="0">
                                        <Pulse>
                                            <thead>
                                            <tr>
                                                <td>Name</td>
                                                <td>Owner</td>
                                                <td>Subtype</td>
                                                <td>Preferences</td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {this.state.dogs.sort((a, b) => a.name > b.name).map(pet =>
                                                <tr>
                                                    <td>{pet.name}</td>
                                                    <td>{pet.owner}</td>
                                                    <td>{pet.subtype}</td>
                                                    <td>{pet.preferences}</td>
                                                </tr>
                                            )}
                                            </tbody>
                                        </Pulse>
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
			<Logout/>
			</body>
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
			<Logout/>
			</body>
			</html>
		);
	}
}

export class CatSearch extends React.Component {
    state = {
        cats: []
    };

    componentDidMount() {
        axios.get('/pets/cats')
            .then(res => {
                const pets = res.pets;
                console.log(pets);
                this.setState({cats: pets});
            });
    }
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
                                    <table class="table table-bordered" id="dataTable" width="90%" cellspacing="0">
                                        <Pulse>
                                            <thead>
                                            <tr>
                                                <td>Name</td>
                                                <td>Owner</td>
                                                <td>Subtype</td>
                                                <td>Preferences</td>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {this.state.cats.sort((a, b) => a.name > b.name).map(pet =>
                                                <tr>
                                                    <td>{pet.name}</td>
                                                    <td>{pet.owner}</td>
                                                    <td>{pet.subtype}</td>
                                                    <td>{pet.preferences}</td>
                                                </tr>
                                            )}
                                            </tbody>
                                        </Pulse>
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
			<Logout/>
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
			<Logout/>
			</body>
			</html>
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

export class Logout extends React.Component{
	render(){
		return(
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