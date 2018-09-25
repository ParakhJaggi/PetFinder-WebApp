import React from 'react';
import { HashRouter, Route } from 'react-router-dom';

import * as Pages from 'js/pages';

export default class Index extends React.Component {
	render() {
		return (
			<HashRouter>
				<div>
					<Route exact path="/" component={Pages.LoginPage} />/*TODO Fix Routing*/
					<Route exact path="/register" component={Pages.RegisterPage} />
					<Route exact path="/login" component={Pages.LoginPage} />
					<Route exact path="/profile-page" component={Pages.ProfilePage} />
					<Route exact path="/page-2" component={Pages.Page2} />
					<Route exact path="/page-3" component={Pages.Page3} />
                    <Route exact path="/home" component={Pages.Home} />

				</div>
			</HashRouter>
		);
	}
}