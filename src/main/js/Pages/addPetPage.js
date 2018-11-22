import React from 'react';
import {NavBar, SideBar} from 'js/navigationModules/navigation';
import {RegistrationPetForm} from 'js/petModules/pet';
import {Logout} from 'js/profileModules/logoutHelpers';

export class AddPet extends React.Component {
    render() {
        return (

            <div className="container shiftRight top-buffer">
                <NavBar/>
                <SideBar/>

                <div className="card">
                    <RegistrationPetForm/>
                </div>
                <Logout/>
            </div>
        );
    }
}