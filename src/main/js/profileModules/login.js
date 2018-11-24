import React from 'react';
import * as ReduxForm from 'redux-form';
import {connect} from 'react-redux';

import * as Validation from 'js/alloy/utils/validation';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Users from 'js/profileModules/users';


class LoginForm extends React.Component {
	onSubmit = ({principal, password}, success) => {
		return this.props.authenticate(principal, password, success);
	};

	render() {
		let {handleSubmit, submitting} = this.props;
		let onSuccess = this.props.success;
		return (


			<form name="form" onSubmit={handleSubmit(form => this.onSubmit(form, onSuccess))}>

				<Bessemer.Field name="principal" friendlyName="Email Address"
				                validators={[Validation.requiredValidator, Validation.emailValidator]}/>

				<Bessemer.Field name="password" friendlyName="Password"
				                validators={[Validation.requiredValidator, Validation.passwordValidator]}
				                field={<input className="form-control" type="password"/>}/>
				<Bessemer.Button loading={submitting}>Sign In</Bessemer.Button>

			</form>

		);
	}
}

LoginForm = ReduxForm.reduxForm({form: 'login'})(LoginForm);

LoginForm = connect(
	//state => ({}),
	dispatch => ({
		authenticate: (principal, password, onSuccess) => dispatch(Users.Actions.authenticate(principal, password, onSuccess))
	})
)(LoginForm);

export {LoginForm};
import { SubmissionError } from 'redux-form';
class RegistrationForm extends React.Component {
	onSubmit = user => {
		if(user.password != user.Secondpassword)
			throw new SubmissionError({ password: 'The passwords do not match.', Secondpassword: 'The passwords do not match.' });
		return this.props.register(user);
	};
	state = {
		selectedOption: null,
		selectedOption2: null
	}
	handleChange = (selectedOption) => {
		this.setState({selectedOption});
		//console.log('Option selected:', selectedOption);
	};
	handleChange2 = (selectedOption2) => {
		this.setState({selectedOption2});
		//console.log('Option selected:', selectedOption2);
	};

	render() {
		let {handleSubmit, submitting} = this.props;
		//let onSuccess = this.props.success;

		const options = [
			{value: 'Alabama', label: 'Alabama'},
			{value: 'Alaska', label: 'Alaska'},
			{value: 'Arizona', label: 'Arizona'},
			{value: 'Arkansas', label: 'Arkansas'},
			{value: 'California', label: 'California'},
			{value: 'Colorado', label: 'Colorado'},
			{value: 'Connecticut', label: 'Connecticut'},
			{value: 'Delaware', label: 'Delaware'},
			{value: 'Florida', label: 'Florida'},
			{value: 'Georgia', label: 'Georgia'},
			{value: 'Hawaii', label: 'Hawaii'},
			{value: 'Idaho', label: 'Idaho'},
			{value: 'Illinois', label: 'Illinois'},
			{value: 'Indiana', label: 'Indiana'},
			{value: 'Iowa', label: 'Iowa'},
			{value: 'Kansas', label: 'Kansas'},
			{value: 'Kentucky', label: 'Kentucky'},
			{value: 'Louisiana', label: 'Louisiana'},
			{value: 'Maine', label: 'Maine'},
			{value: 'Maryland', label: 'Maryland'},
			{value: 'Massachusetts', label: 'Massachusetts'},
			{value: 'Michigan', label: 'Michigan'},
			{value: 'Minnesota', label: 'Minnesota'},
			{value: 'Mississippi', label: 'Mississippi'},
			{value: 'Missouri', label: 'Missouri'},
			{value: 'Montana', label: 'Montana'},
			{value: 'Nebraska', label: 'Nebraska'},
			{value: 'Nevada', label: 'Nevada'},
			{value: 'New Hampshire', label: 'New Hampshire'},
			{value: 'New Jersey', label: 'New Jersey'},
			{value: 'New Mexico', label: 'New Mexico'},
			{value: 'New York', label: 'New York'},
			{value: 'North Carolina', label: 'North Carolina'},
			{value: 'North Dakota', label: 'North Dakota'},
			{value: 'Ohio', label: 'Ohio'},
			{value: 'Oklahoma', label: 'Oklahoma'},
			{value: 'Oregon', label: 'Oregon'},
			{value: 'Pennsylvania', label: 'Pennsylvania'},
			{value: 'Rhode Island', label: 'Rhode Island'},
			{value: 'South Carolina', label: 'South Carolina'},
			{value: 'South Dakota', label: 'South Dakota'},
			{value: 'Tennessee', label: 'Tennessee'},
			{value: 'Texas', label: 'Texas'},
			{value: 'Utah', label: 'Utah'},
			{value: 'Vermont', label: 'Vermont'},
			{value: 'Virginia', label: 'Virginia'},
			{value: 'Washington', label: 'Washington'},
			{value: 'West Virginia', label: 'West Virginia'},
			{value: 'Wisconsin', label: 'Wisconsin'},
			{value: 'Wyoming', label: 'Wyoming'},
			{value: 'Non-US', label: 'Non-US'}
		];
		const options2 = [
			{value: 'SITTER', label: 'Sitter'},
			{value: 'OWNER', label: 'Owner'}
		];
		return (

			<form name="form" onSubmit={handleSubmit(form => this.onSubmit(form))}>
				<Bessemer.Field name="type" friendlyName="Type"
				                validators={[Validation.requiredValidator]}
				                field={<Bessemer.Select className="pull-right" name="type" friendlyName="Type"
				                                        options={options2} value={this.state.selectedOption2}
				                                        onChange={this.handleChange2}
				                                        style={{width: 220}}/>}/>

				<br/><br/>
				<Bessemer.Field name="principal" friendlyName="Email Address"
				                validators={[Validation.requiredValidator, Validation.emailValidator]}/>

				<Bessemer.Field name="password" friendlyName="Password"
				                validators={[Validation.requiredValidator, Validation.passwordValidator]}
				                field={<input className="form-control" type="password"/>}/>

				<Bessemer.Field name="Secondpassword" friendlyName="Confirm"
				                validators={[Validation.requiredValidator, Validation.passwordValidator]}
				                field={<input className="form-control" type="password"/>}/>

				<Bessemer.Field name="address" friendlyName="Address"
				                validators={[Validation.requiredValidator]}/>
				<Bessemer.Field name="city" friendlyName="City"
				                validators={[Validation.requiredValidator]}/>
				<Bessemer.Field name="state" friendlyName="State"
				                validators={[Validation.requiredValidator]}
				                field={<Bessemer.Select className="pull-right" name="state" friendlyName="State"
				                                        options={options} value={this.state.selectedOption}
				                                        onChange={this.handleChange}
				                                        style={{width: 220}}/>}/>

				<br/><br/>
				<Bessemer.Field name="zip" friendlyName="Zip code"
				                validators={[Validation.requiredValidator]}/>

				<Bessemer.Button loading={submitting}>Register</Bessemer.Button>
			</form>

		);
	}
}

RegistrationForm = ReduxForm.reduxForm({form: 'register'})(RegistrationForm);

RegistrationForm = connect(
	//state => ({}),
	dispatch => ({
		register: user => dispatch(Users.Actions.register(user))
	})
)(RegistrationForm);

export {RegistrationForm};