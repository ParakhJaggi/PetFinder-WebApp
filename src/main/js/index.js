import React from 'react';
import {HashRouter, Route} from 'react-router-dom';

//import * as Pages from 'js/profileModules/logoutHelpers';
import {RegisterPage} from 'js/Pages/registerPage';
import {LoginPage} from 'js/Pages/loginPage';
import {AboutUs} from 'js/Pages/aboutUsPage';
import {AddPet} from 'js/Pages/addPetPage';
import {OtherSearch, CatSearch, DogSearch, RodentSearch} from 'js/Pages/petPages';
import {Dashboard} from 'js/Pages/dashboardPage';
import {ReviewPage} from 'js/Pages/reviewPage';
import {ProfilePage} from 'js/Pages/theProfilePage';

export default class Index extends React.Component {
	render() {
		return (
			<HashRouter>
				<div>
					<Route exact path="/" component={LoginPage}/>
					<Route exact path="/register" component={RegisterPage}/>
					<Route exact path="/login" component={LoginPage}/>
					<Route exact path="/profile-page" component={ProfilePage}/>
					<Route exact path="/rodent-search" component={RodentSearch}/>
					<Route exact path="/dog-search" component={DogSearch}/>
					<Route exact path="/cat-search" component={CatSearch}/>
					<Route exact path="/other-search" component={OtherSearch}/>
					<Route exact path="/dashboard" component={Dashboard}/>
					<Route exact path="/review-page" component={ReviewPage}/>
					<Route exact path="/sits" component={AddPet}/>
                    <Route exact path="/about-us" component={AboutUs}/>
				</div>
			</HashRouter>
		);
	}
}