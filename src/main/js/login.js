import React from 'react';
import * as ReduxForm from 'redux-form';
import {connect} from 'react-redux';

import * as Validation from 'js/alloy/utils/validation';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Users from 'js/users';


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
	state => ({}),
	dispatch => ({
		authenticate: (principal, password, onSuccess) => dispatch(Users.Actions.authenticate(principal, password, onSuccess))
	})
)(LoginForm);

export {LoginForm};

class RegistrationForm extends React.Component {
	onSubmit = user => {
		return this.props.register(user);
	};

	render() {
		let {handleSubmit, submitting} = this.props;
		let onSuccess = this.props.success;

		return (

			<form name="form" onSubmit={handleSubmit(form => this.onSubmit(form))}>
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
                                validators={[Validation.requiredValidator]}/>
                <Bessemer.Field name="zip" friendlyName="Zip code"
                                validators={[Validation.requiredValidator]}/>

				<Bessemer.Button loading={submitting}>Register</Bessemer.Button>
			</form>

		);
	}
}

RegistrationForm = ReduxForm.reduxForm({form: 'register'})(RegistrationForm);

RegistrationForm = connect(
	state => ({}),
	dispatch => ({
		register: user => dispatch(Users.Actions.register(user))
	})
)(RegistrationForm);

export {RegistrationForm};