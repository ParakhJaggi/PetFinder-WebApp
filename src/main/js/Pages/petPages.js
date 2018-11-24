import React from 'react';
import axios from 'axios';
import {NavBar, SideBar,Footer} from 'js/navigationModules/navigation';
import Pulse from 'react-reveal/Pulse';
import Switch from 'react-switch';
import {Logout} from 'js/profileModules/logoutHelpers';

export class RodentSearch extends React.Component {
	constructor() {
		super();
		this.state = {
			rodents: [],
			allRodents: [],
			hamsterChecked: true,
			rabbitChecked: true,
			guineaPigChecked: true,
			ferretChecked: true,
			chinchillaChecked: true,
			otherChecked: true,
		};
		this.handleChangeHamster = this.handleChangeHamster.bind(this);
		this.handleChangeOther = this.handleChangeOther.bind(this);
		this.handleChangeChinchilla = this.handleChangeChinchilla.bind(this);
		this.handleChangeFerret = this.handleChangeFerret.bind(this);
		this.handleChangeRabbit = this.handleChangeRabbit.bind(this);
		this.handleChangeGuineaPig = this.handleChangeGuineaPig.bind(this);
	}


	componentDidMount() {
		axios.get('/pets/rodents')
			.then(res => {
				const pets = res.pets;
				console.log(pets);
				this.setState({rodents: pets});
				this.setState({allRodents: pets});
			});
	}

	handleChangeHamster(checked) {
		this.setState({checked});
		this.setState({hamsterChecked: !this.state.hamsterChecked});
		this.filterAnimals();
	}

	handleChangeRabbit(checked) {
		this.setState({checked});
		this.setState({rabbitChecked: !this.state.rabbitChecked});
		this.filterAnimals();
	}

	handleChangeFerret(checked) {
		this.setState({checked});
		this.setState({ferretChecked: !this.state.ferretChecked});
		this.filterAnimals();
	}

	handleChangeGuineaPig(checked) {
		this.setState({checked});
		this.setState({guineaPigChecked: !this.state.guineaPigChecked});
		this.filterAnimals();
	}

	handleChangeChinchilla(checked) {
		this.setState({checked});
		this.setState({chinchillaChecked: !this.state.chinchillaChecked});
		this.filterAnimals();
	}

	handleChangeOther(checked) {
		this.setState({checked});
		this.setState({otherChecked: !this.state.otherChecked});
		this.filterAnimals();
	}

	filterAnimals() {
		this.setState({rodents: this.state.allRodents});
		if (!this.state.hamsterChecked) {
			this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype != 'Hamster')});
		}
		if (!this.state.guineaPigChecked) {
			this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype != 'Guinea Pig')});
		}
		if (!this.state.rabbitChecked) {
			this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype != 'Rabbit')});
		}
		if (!this.state.otherChecked) {
			this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype != 'Other')});
		}
		if (!this.state.chinchillaChecked) {
			this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype != 'Chinchilla')});
		}
		if (!this.state.ferretChecked) {
			this.setState({rodents: this.state.rodents.filter(rodent => rodent.subtype != 'Ferret')});
		}
		//Note: You have to slide to fix, tap doesnt work
		this.forceUpdate();
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
							<li class="breadcrumb-item active">Rodents</li>
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
										<th className="pl-5"><span>Hamsters</span><Switch
											checked={this.state.hamsterChecked}
											onChange={this.handleChangeHamster}
											onColor="#86d3ff"
											onHandleColor="#2693e6"
											handleDiameter={30}
											uncheckedIcon={false}
											checkedIcon={false}
											boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
											activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
											height={20}
											width={48}
											className="react-switch"
											id="material-switch"
										/></th>
										<th className="pl-5"><span>Rabbits</span><Switch
											checked={this.state.rabbitChecked}
											onChange={this.handleChangeRabbit}
											onColor="#86d3ff"
											onHandleColor="#2693e6"
											handleDiameter={30}
											uncheckedIcon={false}
											checkedIcon={false}
											boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
											activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
											height={20}
											width={48}
											className="react-switch"
											id="material-switch"
										/></th>
										<th className="pl-5"><span>Guinea Pigs</span><Switch
											checked={this.state.guineaPigChecked}
											onChange={this.handleChangeGuineaPig}
											onColor="#86d3ff"
											onHandleColor="#2693e6"
											handleDiameter={30}
											uncheckedIcon={false}
											checkedIcon={false}
											boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
											activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
											height={20}
											width={48}
											className="react-switch"
											id="material-switch"
										/></th>
									</tr>
									<tr>
										<th className="pl-5"><span>Ferrets</span><Switch
											checked={this.state.ferretChecked}
											onChange={this.handleChangeFerret}
											onColor="#86d3ff"
											onHandleColor="#2693e6"
											handleDiameter={30}
											uncheckedIcon={false}
											checkedIcon={false}
											boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
											activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
											height={20}
											width={48}
											className="react-switch"
											id="material-switch"
										/></th>
										<th className="pl-5"><span>Chinchillas</span><Switch
											checked={this.state.chinchillaChecked}
											onChange={this.handleChangeChinchilla}
											onColor="#86d3ff"
											onHandleColor="#2693e6"
											handleDiameter={30}
											uncheckedIcon={false}
											checkedIcon={false}
											boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
											activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
											height={20}
											width={48}
											className="react-switch"
											id="material-switch"
										/></th>
										<th className="pl-5"><span>Other</span><Switch
											checked={this.state.otherChecked}
											onChange={this.handleChangeOther}
											onColor="#86d3ff"
											onHandleColor="#2693e6"
											handleDiameter={30}
											uncheckedIcon={false}
											checkedIcon={false}
											boxShadow="0px 1px 5px rgba(0, 0, 0, 0.6)"
											activeBoxShadow="0px 0px 1px 10px rgba(0, 0, 0, 0.2)"
											height={20}
											width={48}
											className="react-switch"
											id="material-switch"
										/></th>
									</tr>
									</thead>
								</table>
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
						</div>
					</div>
					<Footer/>
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
							<li class="breadcrumb-item active">Dogs</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Dogs
							</div>
							<div class="card-body">
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
						</div>
					</div>
					<Footer/>
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

export class OtherSearch extends React.Component {
	state = {
		other: []
	};

	componentDidMount() {
		axios.get('/pets/other')
			.then(res => {
				const pets = res.pets;
				console.log(pets);
				this.setState({other: pets});
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
							<li class="breadcrumb-item active">Other</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Other
							</div>
							<div class="card-body">
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
											{this.state.other.sort((a, b) => a.name > b.name).map(pet =>
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
						</div>
					</div>
					<Footer/>
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
							<li class="breadcrumb-item active">Cats</li>
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
						</div>
					</div>
				   <Footer/>
				</div>
			</div>
			<Logout/>
			</body>
			</html>
		);
	}
}