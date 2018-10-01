import React from 'react';
import { HashRouter, Route } from 'react-router-dom';

import * as Pages from 'js/pages';

export default class Index extends React.Component {
	render() {
		return (
			<HashRouter>
				<div>
					<Route exact path="/" component={Pages.LoginPage} />
					<Route exact path="/register" component={Pages.RegisterPage} />
					<Route exact path="/login" component={Pages.LoginPage} />
					<Route exact path="/profile-page" component={Pages.ProfilePage} />
					<Route exact path="/rodent-search" component={Pages.RodentSearch} />
                    <Route exact path="/dog-search" component={Pages.DogSearch} />
                    <Route exact path="/cat-search" component={Pages.CatSearch} />
                    <Route exact path="/bird-search" component={Pages.BirdSearch} />
					<Route exact path="/dashboard" component={Pages.Dashboard} />
                    <Route exact path="/home" component={Pages.Home} />
                    <Route exact path="/about-us" component={Pages.AboutUs} />
                    <Route exact path="/sits" component={Pages.AddPet} />
				</div>
			</HashRouter>
		);
	}
}