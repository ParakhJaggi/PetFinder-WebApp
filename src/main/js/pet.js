import React from 'react';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Validation from 'js/alloy/utils/validation';
import * as ReduxForm from 'redux-form';
import connect from 'react-redux/es/connect/connect';
import * as Users from 'js/users';
import axios from 'axios';

class RegistrationPetForm extends React.Component {
    onSubmit = pet => {
        return axios.post('/api/userPets/savePet', pet);
    };

    render() {
        let {handleSubmit, submitting} = this.props;
        let onSuccess = this.props.success;

        return (

            <form name="form" onSubmit={handleSubmit(form => this.onSubmit(form))}>
                <Bessemer.Field name="name" friendlyName="Pet Name"
                                validators={[Validation.requiredValidator]} />

                <Bessemer.Field name="type" friendlyName="Type"/*Todo Radio box*/
                                validators={[Validation.requiredValidator]}
                />

                <Bessemer.Field name="subtype" friendlyName="Sub Type"
                                validators={[Validation.requiredValidator]}/>

                <Bessemer.Field name="preferences" friendlyName="Extra Preferences"/>

                <Bessemer.Button loading={submitting}>Register</Bessemer.Button>
            </form>

        );
    }
}

RegistrationPetForm = ReduxForm.reduxForm({form: 'register'})(RegistrationPetForm);

RegistrationPetForm = connect(
    state => ({}),
    dispatch => ({
        register: user => dispatch(Users.Actions.register(user))
    })
)(RegistrationPetForm);

export {RegistrationPetForm};

class EditPetForm extends React.Component {
    onSubmit = pet => {
        let toPost = {
            'id':this.props.pet.id,
            'name':(pet[this.props.pet.id + 'name'] == null)?this.props.pet.name:pet[this.props.pet.id + 'name'],
            'type':(pet[this.props.pet.id + 'type']==null)?this.props.pet.type:pet[this.props.pet.id + 'type'],
            'owner':this.props.pet.owner,
            'subtype':(pet[this.props.pet.id + 'subtype'] == null)?this.props.pet.subtype:pet[this.props.pet.id + 'subtype'],
            'preferences':(pet[this.props.pet.id + 'preferences']==null)?this.props.pet.preferences:pet[this.props.pet.id + 'preferences']
        };
        return axios.post('/pets/edit', toPost);
    };

    render() {
        let {handleSubmit, submitting} = this.props;
        let onSuccess = this.props.success;

        return (
            <form name={this.props.pet.id + 'form'} onSubmit={handleSubmit(form => this.onSubmit(form))} initialValues={this.props.pet}>
				{'Your pet is named' + this.props.pet.name}
                <Bessemer.Field name={this.props.pet.id + 'name'} friendlyName="Pet Name"
                                defaultVal={this.props.pet.name}
                                value={this.props.pet.name}
                />

                <Bessemer.Field name={this.props.pet.id + 'type'} friendlyName="Type"/*Todo Radio box*/
                                defaultVal={this.props.pet.type}
                />

                <Bessemer.Field name={this.props.pet.id + 'subtype'} friendlyName="Sub Type"
                                defaultVal={this.props.pet.subtype}
                />

                <Bessemer.Field name={this.props.pet.id + 'preferences'} friendlyName="Extra Preferences"
                                defaultVal={this.props.pet.preferences}
                />

                <Bessemer.Button loading={submitting}>Register</Bessemer.Button>
            </form>

        );
    }
}

EditPetForm = ReduxForm.reduxForm({form: 'register'})(EditPetForm);

EditPetForm = connect(
    state => ({}),
    dispatch => ({
        register: user => dispatch(Users.Actions.register(user))
    })
)(EditPetForm);

export {EditPetForm};

const $ = require('jquery');
$.DataTable = require('datatables.net');

const columns = [
	{
		title: 'PetName',
		width: 120,
		data: 'name'
	},
	{
		title: 'OwnerName',
		width: 180,
		data: 'ownername'
	},
];

export class PetTable extends React.Component {
	componentDidMount() {
		$(this.refs.main).DataTable({
			dom: '<"data-table-wrapper"t>',
			data: this.props.names,
			columns,
			ordering: false
		});
	}

	componentWillUnmount() {
		$('.data-table-wrapper')
			.find('table')
			.DataTable()
			.destroy(true);
	}

	shouldComponentUpdate() {
		return false;
	}

	render() {
		return (
			<div>
				<table>

                </table>
			</div>);
	}
}