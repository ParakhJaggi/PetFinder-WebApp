import React from 'react';
import axios from 'axios';
import {Footer, NavBar, SideBar} from 'js/navigationModules/navigation';
import Pulse from 'react-reveal/Pulse';
import {Logout} from 'js/profileModules/logoutHelpers';

export class ReviewPage extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			bookings: [],
			sitters: [],
			score: '',
			review: '',
			name: '',
			reload: ''
		};
		this.handleChange = this.handleChange.bind(this);
		this.handleChange2 = this.handleChange2.bind(this);
		this.handleChange3 = this.handleChange3.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleChange(event) {
		this.setState({name: event.target.value});
	}

	handleChange2(event) {
		if (event.target.value >= 0 && event.target.value <= 100)
			this.setState({score: event.target.value});
		else
			window.alert('Error, input between 0-100');
	}

	handleChange3(event) {
		this.setState({review: event.target.value});
	}

	handleSubmit(event) {
		event.preventDefault();
		let toPost = {
			'user': this.state.name,
			'review': this.state.review,
			'assignedScore': this.state.score
		};
		axios.post('/api/user/addReviewScore', toPost);
		location.reload();
		window.alert('Review sent');
	}


	componentDidMount() {
		axios.get('/api/user')
			.then(res => {
				if (res.type == 'SITTER') {
					window.alert('Sitters Can Not Review');
					window.location.href = '#/profile-page';
					location.reload();

				}

				const myBookings = res.bookings;
				this.setState({bookings: myBookings});
			});
		axios.get('/api/user/getavailablesitters')
			.then(res => {
				const mySitters = res.users;
				this.setState({sitters: mySitters});
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
							<li class="breadcrumb-item active">Bookings</li>
						</ol>
						<div class="card mb-3">
							<div class="card-header">
								<i class="fas fa-table"></i>
								Bookings
							</div>
							<div class="card-body">
								<div className="table-responsive">
									<table className="table table-bordered" id="dataTable" width="90%" cellSpacing="0">
										<Pulse>
											<thead>
											<tr>
												<td>Sitter Name</td>
												<td># Ratings</td>
												<td>Sitter Rating</td>
											</tr>
											</thead>
											<tbody>
											{this.state.sitters.map(sitters =>
												<tr>
													<td>{sitters.principal}</td>
													<td>{sitters.reviewCount - 1}</td>
													<td>{parseInt(sitters.reviewSum / sitters.reviewCount)}</td>
												</tr>
											)}

											</tbody>
										</Pulse>
									</table>
									<form onSubmit={this.handleSubmit}>
										<label>
											Enter name of Sitter you would like to review:
											<input type="text" value={this.state.name} onChange={this.handleChange}/>
										</label><br/>

										<label>
											Your Score:
											<input type="text" value={this.state.score} onChange={this.handleChange2}/>
										</label><br/>
										<label>
											Your Review (Optional):
										</label><br/><textarea className="form-control" rows="5" id="comment"
										                       type="text" value={this.state.review}
										                       onChange={this.handleChange3}></textarea>
										<input type="submit" value="Submit"/>
									</form>
								</div>
							</div>
						</div>
					</div>
					<Footer/>
				</div>
			</div>
			<Logout/>
			</body>
		);
	}
}