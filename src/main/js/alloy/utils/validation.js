import _ from 'lodash';

export class Validator {
	constructor(spec, error) {
		this.spec = spec;
		this.error = error;
	}
}

let Spec = {};

Spec.makeOptional = spec => val => _.isEmpty(val) ? true : spec(val);

export { Spec };

export const score =(val) => val.match([0-9]);
export const scoreValidator = new Validator(score, (details) => details.friendlyName + ' must be 0-10.');
export const required = value => !!value;
export const requiredValidator = new Validator(required, (details) => details.friendlyName + ' is required.');

export const isEmail = (val) => val.match(/^[a-zA-Z0-9](\.?\+?[a-zA-Z0-9_-]){0,}@[a-zA-Z0-9-]+\.([a-zA-Z]{1,6}\.)?[a-zA-Z]{2,6}$/);
export const emailValidator = new Validator(isEmail, (details, value) => value + ' is not a valid email address.');

export const isValidPassword = (val) => val.toString().length >= 6 && val.match(/^[a-zA-Z0-9!@#$%^&*]{6,64}$/);
export const passwordValidator = new Validator(isValidPassword, (details) => details.friendlyName + ' must be a valid password.');