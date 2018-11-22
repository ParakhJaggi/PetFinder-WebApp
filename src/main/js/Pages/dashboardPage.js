import React from 'react';
import {NavBar, SideBar} from 'js/navigationModules/navigation';
import Pulse from 'react-reveal/Pulse';
import {Logout} from 'js/profileModules/logoutHelpers';

export class Dashboard extends React.Component {
    render() {
        return (

            <html lang="en">
            <body id="page-top">
            <div id="wrapper">
                <NavBar></NavBar>
                <SideBar></SideBar>
                <div id="content-wrapper">
                    <div class="top-buffer">
                    </div>
                    <div class="container shiftRight">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="#">Dashboard</a>
                            </li>
                            <li class="breadcrumb-item active">Find Pets</li>
                        </ol>

                        <Pulse>
                            <div class="row justify-content-center ">
                                <div class="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
                                    <a className="card-footer text-black-50 clearfix small z-1" href="#/dog-search">
                                        <span className="float-left"><b>Dogs</b></span>
                                        <span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
                                    </a>
                                    <div class="card-body align-bottom">
                                        <div className="card-img-overlay dog-crop align-bottom">
                                            <img src={'https://i.postimg.cc/DwXpZCDK/dogs.png'}
                                                 className=" dog-crop card-img-bottom"></img>
                                        </div>
                                    </div>

                                </div>
                                <div className="pr-2"></div>
                                <div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
                                    <a className="card-footer text-black-50 clearfix small z-1" href="#/cat-search">
                                        <span className="float-left"><b>Cats</b></span>
                                        <span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
                                    </a>
                                    <div className="card-body">
                                        <div className="card-img-overlay">
                                            <img src={'https://i.postimg.cc/Jn1ppRh1/cats.png'}
                                                 className="card-img-bottom"></img>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row justify-content-center ">
                                <div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">
                                    <a className="card-footer text-black-50 clearfix small z-1" href="#/rodent-search">
                                        <span className="float-left"><b>Rodents</b></span>
                                        <span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
                                    </a>
                                    <div className="card-body">
                                        <div className="card-img-overlay">
                                            <img src={'https://i.postimg.cc/kMLpm31y/bunny.png'}
                                                 className="card-img-bottom bunny-crop "></img>
                                        </div>
                                    </div>

                                </div>
                                <div className="pr-2"></div>
                                <div className="col-xl-4 col-sm-9 mb-3 card text-white bg-light o-hidden h-35">

                                    <a className="card-footer text-black-50 clearfix small z-1" href="#/bird-search">
                                        <span className="float-left"><b>Birds</b></span>
                                        <span className="float-right">
                    <i className="fas fa-angle-right"></i>
                  </span>
                                    </a>
                                    <div className="card-body">
                                        <div className="card-img-overlay">
                                            <img src={'https://i.postimg.cc/QxRSRQMY/birds.png'}
                                                 className="card-img-bottom portrait-crop"></img>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Pulse>


                    </div>

                    <footer class="footer navbar-fixed-bottom">
                        <div class="container shiftRight my-auto">
                            <div class="copyright text-center my-auto">
                                <span>Copyright © Your Website 2018</span>
                            </div>
                        </div>
                    </footer>

                </div>
            </div>
            <Logout/>
            </body>
            </html>
        );
    }

}