// import React, {Component} from 'react';
// import axios from "axios";
// import {BrowserRouter, Redirect, Switch} from 'react-router-dom';
// import {Modal, ModalHeader, ModalBody} from "reactstrap"
// import { NavLink } from 'react-router-dom';
//
// class Login extends Component{
//     constructor(props) {
//         super(props);
//         this.state = {
//             username:"",
//             password:"",
//             authenticated: false,
//             loginModalVisible: false
//         }
//     }
//
//
//
//     componentDidMount() {
//
//     }
//
//     render() {
//
//         const openLoginModal = (id) => {
//             this.setState({
//                 loginModalVisible: true,
//             })
//         };
//
//         const  login=(event)=> {
//             event.preventDefault();
//             axios({
//                 url: "http://localhost:8080/loginn",
//                 method: "post",
//                 params: {
//                     username: event.target[0].value,
//                     password: event.target[1].value
//                 }
//             }).then(res => {
//                 if(res.data.success) {
//                     alert(res.data.message);
//
//
//                 }
//                 else {
//                     alert(res.data.message)
//                 }
//
//             })
//         };
//
//         return (
//             <div style={{width:300}}>
//                 {/*<Modal isOpen={this.state.loginModalVisible}>*/}
//                 {/*    <ModalHeader>*/}
//                 {/*        Sign In*/}
//                 {/*    </ModalHeader>*/}
//                 {/*    <ModalBody>*/}
//                 {/*        <form onSubmit={login}>*/}
//                 {/*            <label>Username</label>*/}
//                 {/*            <input className={"form-control"} type={"text"}*/}
//                 {/*                   placeholder={"Username"} required/><br/>*/}
//                 {/*            <label>Password</label>*/}
//                 {/*            <input className={"form-control"} type={"text"}*/}
//                 {/*                   placeholder={"Password"} required/><br/>*/}
//
//                 {/*            <button style={{marginLeft: 10}} className={"btn btn-success"}>Sign In</button>*/}
//                 {/*        </form>*/}
//                 {/*    </ModalBody>*/}
//                 {/*</Modal>*/}
//                 {/*<button type={"button"} className={"btn btn-info float-sm-right"}*/}
//                 {/*        onClick={openLoginModal}>Sign In*/}
//                 {/*</button>*/}
//                 <form onSubmit={login}>
//                     <label>Username</label>
//                     <input className={"form-control"} type={"text"}
//                            placeholder={"Username"} required/><br/>
//                     <label>Password</label>
//                     <input className={"form-control"} type={"text"}
//                            placeholder={"Password"} required/><br/>
//
//                     <button style={{marginLeft: 10}} className={"btn btn-success"} >Sign In</button>
//                 </form>
//             </div>
//         )
//     }
// }
//
// export default Login;
