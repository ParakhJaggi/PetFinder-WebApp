import React from 'react';
import axios from 'axios';

class SitterRow extends React.Component {
	onClick(index) {
		let days = [false, false, false, false, false, false, false];
		days[index] = true;
		axios.post('/api/user/requestBooking', {
			principal: this.props.sitter.principal,
			days: days
		});
	}

	render() {
		return (
			<tr>
				<td>
					{this.props.sitter.principal}
				</td>
				{this.props.sitter.days.map((available, index) =>
					<td>
						<button onClick={this.onClick.bind(this, index)} disabled={!available}
						>Request Booking
						</button>
					</td>
				)}
			</tr>
		);
	}
}

export class SitterTable extends React.Component {
	state = {
		sitters: []
	};

	componentDidMount() {
		axios.get('/api/user/getavailablesitters')
			.then(res => {
				const sitters = res.users;
				//console.log(res);
				this.setState({sitters: sitters});
			});
	}

	render() {
		return (
            <div className="card mb-3">
                <div className="card-header">
                    <i className="fas fa-table"></i>
                    Available Sitters
                </div>
                <div className="card-body">
            <table className="table" id="dataTable" width="100%" cellSpacing="0">
				<thead>
				<tr>
					<th>Name</th>
					<th>Monday</th>
					<th>Tuesday</th>
					<th>Wednesday</th>
					<th>Thursday</th>
					<th>Friday</th>
					<th>Saturday</th>
					<th>Sunday</th>
				</tr>
				</thead>
				<tbody>
				{this.state.sitters.map(sitter =>
					<SitterRow sitter={sitter}/>
				)}
				</tbody>
			</table>
				</div></div>
		);
	}
}