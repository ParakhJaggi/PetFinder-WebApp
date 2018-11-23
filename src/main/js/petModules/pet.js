import React from 'react';
import * as Bessemer from 'js/alloy/bessemer/components';
import * as Validation from 'js/alloy/utils/validation';
import * as ReduxForm from 'redux-form';
import connect from 'react-redux/es/connect/connect';
import * as Users from 'js/profileModules/users';
import axios from 'axios';
import {SubmissionError} from 'redux-form';


class RegistrationPetForm extends React.Component {
	onSubmit = pet => {
		if(pet.type==='Dog'){
			if(pet.subtype!=='Dog'){
                throw new SubmissionError({ subtype: 'Subtype must be Dog'});
			}
		}
        if(pet.type==='Cat'){
            if(pet.subtype!=='Cat'){
                throw new SubmissionError({ subtype: 'Subtype must be Cat'});
            }
        }
        if(pet.type==='Rodent'){
            if(pet.subtype!=='Chinchilla'|| pet.subtype!=='Ferret'  ||
                pet.subtype!=='Hamster' || pet.subtype!=='Guinea Pig' ||
                pet.subtype!=='Rabbit'||pet.subtype!=='Other Rodent'){
                throw new SubmissionError({ subtype: 'Subtype does not Match'});
            }
        }
        if(pet.type==='Other'){
            if(pet.subtype!=='Reptile'&& pet.subtype!=='Bird'  &&
                pet.subtype!=='Other'){
                throw new SubmissionError({ subtype: 'Subtype does not Match'});
            }
        }
		return axios.post('/api/userPets/savePet', pet);
	};
    state = {
        selectedOption: null,
        selectedOption2: null
    }
    handleChange = (selectedOption) => {
        this.setState({selectedOption});
        console.log('Option selected:', selectedOption);
    };
    handleChange2 = (selectedOption2) => {
        this.setState({selectedOption2});
        console.log('Option selected:', selectedOption2);
    };

	render() {
		let {handleSubmit, submitting} = this.props;
		let onSuccess = this.props.success;

        const options = [
            {value: 'Dog', label: 'Dog'},
            {value: 'Cat', label: 'Cat'},
            {value: 'Rodent', label: 'Rodent'},
            {value: 'Other', label: 'Other'}
        ];
        const options2 = [
            {value: 'Dog', label: 'Dog'},
            {value: 'Cat', label: 'Cat'},
            {value: 'Chinchilla', label: 'Chinchilla'},
            {value: 'Ferret', label: 'Ferret'},
            {value: 'Rabbit', label: 'Rabbit'},
            {value: 'Guinea Pig', label: 'Guinea Pig'},
            {value: 'Hamster', label: 'Hamster'},
            {value: 'Other Rodent', label: 'Other Rodent'},
            {value: 'Bird', label: 'Bird'},
            {value: 'Reptile', label: 'Reptile'},
            {value: 'Other', label: 'Other'},
        ];
		return (

			<form name="form" onSubmit={handleSubmit(form => this.onSubmit(form))}>
				<Bessemer.Field name="name" friendlyName="Pet Name"
				                validators={[Validation.requiredValidator]}/>

				<Bessemer.Field name="type" friendlyName="Type"/*Todo Radio box*/
				                validators={[Validation.requiredValidator]}
								field={<Bessemer.Select className="pull-right" name="type" friendlyName="Type"
                                                                                                   options={options} value={this.state.selectedOption}
                                                                                                   onChange={this.handleChange}
                                                                                                   style={{width: 585}}/>}/>

				<Bessemer.Field name="subtype" friendlyName="Sub Type"
				                validators={[Validation.requiredValidator]} field={<Bessemer.Select className="pull-right" name="subtype" friendlyName="Sub Type"
                                                                                                    options={options2} value={this.state.selectedOption2}
                                                                                                    onChange={this.handleChange2}
                                                                                                    style={{width: 585}}/>}/>

				<Bessemer.Field name="preferences" friendlyName="Extra Preferences/Instructions"/>

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
	constructor(props) {
		super(props);

		this.state = {
			show: false,
		};

		this.toggleShown = this.toggleShown.bind(this);

	}

	onSubmit = pet => {
		let toPost = {
			'id': this.props.pet.id,
			'name': (pet[this.props.pet.id + 'name'] == null) ? this.props.pet.name : pet[this.props.pet.id + 'name'],
			'type': (pet[this.props.pet.id + 'type'] == null) ? this.props.pet.type : pet[this.props.pet.id + 'type'],
			'owner': this.props.pet.owner,
			'subtype': (pet[this.props.pet.id + 'subtype'] == null) ? this.props.pet.subtype : pet[this.props.pet.id + 'subtype'],
			'preferences': (pet[this.props.pet.id + 'preferences'] == null) ? this.props.pet.preferences : pet[this.props.pet.id + 'preferences']
		};
		return axios.post('/pets/edit', toPost);
	};

	toggleShown() {
		this.setState({show: !this.state.show});
	}

	render() {
		let {handleSubmit, submitting} = this.props;
		let onSuccess = this.props.success;

		if (this.state) {
			return (
				<React.Fragment>
					<div onClick={this.toggleShown} style={{cursor: 'pointer'}}>{!this.state.show &&
					<div>&#9655;{this.props.pet.name}</div>}{this.state.show &&
					<div>&#9661;{this.props.pet.name}</div>}</div>
					{
						this.state.show &&
						<form name={this.props.pet.id + 'form'} onSubmit={handleSubmit(form => this.onSubmit(form))}>
							<Bessemer.Field name={this.props.pet.id + 'name'} friendlyName="Pet Name"
							                defaultVal={this.props.pet.name}
							                value={this.props.pet.name}
							/>

							<Bessemer.Field name={this.props.pet.id + 'type'} friendlyName="Type (Can't Edit)"/*Todo Radio box*/
							                defaultVal={this.props.pet.type} field={<textarea rows="1" cols="30" placeholder={this.props.pet.type}  readOnly/>}
							/>

							<Bessemer.Field name={this.props.pet.id + 'subtype'} friendlyName="Sub Type (Can't Edit)"
							                defaultVal={this.props.pet.subtype} field={<textarea rows="1" cols="30" placeholder={this.props.pet.subtype}  readOnly/>}
							/>

							<Bessemer.Field name={this.props.pet.id + 'preferences'} friendlyName="Extra Preferences"
							                defaultVal={this.props.pet.preferences}
							/>

							<Bessemer.Button loading={submitting}>Update Pet</Bessemer.Button>
						</form>
					}
				</React.Fragment>
			);
		} else {
			return 'Mounting...';
		}
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