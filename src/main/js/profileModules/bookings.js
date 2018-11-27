import React from 'react';
import axios from 'axios';

class BookingRow extends React.Component {
	onClick(index, action) {
		let days = [false, false, false, false, false, false, false];
		days[index] = true;
		axios.post('/api/user/' + action + 'Booking', {
			principal: this.props.pair[0],
			days: days
		});
	}

	renderDay(index, val) {
		switch (val) {
			case 1:
				return (
					<React.Fragment>
						<button onClick={this.onClick.bind(this, index, 'confirm')}
						>Confirm
						</button>
						<button onClick={this.onClick.bind(this, index, 'delete')}
						>Reject
						</button>
					</React.Fragment>
				);
			case 2:
				return (
					<button onClick={this.onClick.bind(this, index, 'delete')}
					>Cancel</button>
				);
			default:
				return;
		}
	}

	render() {
		return (
			<tr>
				<td>
					{this.props.pair[0]}
				</td>
				{this.props.pair[1].map((val, index) =>
					<td>
						{this.renderDay(index, val)}
					</td>
				)}
			</tr>
		);
	}
}

export class BookingTable extends React.Component {
	state = {
		rowdata: new Map()
	};

	updateDays(principal, data, val) {
		for (let i = 0; i < 7; i++) {
			if (data[i])
				this.state.rowdata.get(principal)[i] = val;
		}
	}

	render() {
		let self = this;
		this.props.user.requestedBookings.forEach(function (request) {
			if (!(self.state.rowdata.has(request.principal))) {
				self.state.rowdata.set(request.principal, [0, 0, 0, 0, 0, 0, 0]);
			}
			self.updateDays(request.principal, request.days, 1);
		});
		this.props.user.bookings.forEach(function (request) {
			if (!(self.state.rowdata.has(request.principal))) {
				self.state.rowdata.set(request.principal, [0, 0, 0, 0, 0, 0, 0]);
			}
			self.updateDays(request.principal, request.days, 2);
		});
		return (
                <div className="card mb-3">
                    <div className="card-header">
                        <i className="fas fa-table"></i>
                        My confirmed bookings
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
				{Array.from(this.state.rowdata).map((pair) =>
					<BookingRow user={this.props.user} pair={pair}/>
				)}
				</tbody>
			</table>
			</div>
				</div>

		);
	}
}