import React from 'react';
import Switch from 'react-switch';
import Slider from 'react-rangeslider';


export class GuineaPigSwitch extends React.Component {
	constructor() {
		super();
		this.state = {checked: true};
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(checked) {
		this.setState({checked});
	}

	render() {
		return (
			<div className="example">
				<Switch
					checked={this.state.checked}
					onChange={this.handleChange}
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
				/>

			</div>
		);
	}
}

export class HamsterSwitch extends React.Component {
	constructor() {
		super();
		this.state = {checked: true};
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(checked) {
		this.setState({checked});
	}

	render() {
		return (
			<div className="example">
				<Switch
					checked={this.state.checked}
					onChange={this.handleChange}
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
				/>
			</div>
		);
	}
}

export class ChinchillaSwitch extends React.Component {
	constructor() {
		super();
		this.state = {checked: true};
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(checked) {
		this.setState({checked});
	}

	render() {
		return (
			<div className="example">
				<Switch
					checked={this.state.checked}
					onChange={this.handleChange}
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
				/>
			</div>
		);
	}
}

export class FerretSwitch extends React.Component {
	constructor() {
		super();
		this.state = {checked: true};
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(checked) {
		this.setState({checked});
	}

	render() {
		return (
			<div className="example">
				<Switch
					checked={this.state.checked}
					onChange={this.handleChange}
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
				/>
			</div>
		);
	}
}

export class OtherRodentSwitch extends React.Component {
	constructor() {
		super();
		this.state = {checked: true};
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(checked) {
		this.setState({checked});
	}

	render() {
		return (
			<div className="example">
				<Switch
					checked={this.state.checked}
					onChange={this.handleChange}
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
				/>
			</div>
		);
	}
}

export class RabbitSwitch extends React.Component {
	constructor() {
		super();
		this.state = {checked: true};
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(checked) {
		this.setState({checked});
	}

	render() {
		return (
			<div className="example">
				<Switch
					checked={this.state.checked}
					onChange={this.handleChange}
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
				/>
			</div>
		);
	}
}

export class LocationSlider extends React.Component {
	constructor(props, context) {
		super(props, context);
		this.state = {
			value: 10
		};
	}

	handleChangeStart = () => {
		console.log('Change event started');
	};

	handleChange = value => {
		this.setState({
			value: value
		});
	};

	handleChangeComplete = () => {
		console.log('Change event completed');
	};

	render() {
		const {value} = this.state;
		return (
			<div className='slider'><span><b>Miles: {value}</b></span>
				<Slider
					min={0}
					max={50}
					value={value}
					step={5}
					onChangeStart={this.handleChangeStart}
					onChange={this.handleChange}
					onChangeComplete={this.handleChangeComplete}
				/>
			</div>
		);
	}
}