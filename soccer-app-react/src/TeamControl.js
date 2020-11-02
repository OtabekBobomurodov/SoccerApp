import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.css'
import axios from "axios"
import {Modal, ModalHeader, ModalBody} from "reactstrap"
import AsyncSelect from 'react-select/async';

class TeamControl extends Component {
    constructor(props) {
        super(props);
        this.state = {
            teams:[],
            players:[],
            fixtures:[],
            nextGame:[],
            buyPlayers:[],
            sellPlayers:[],
            fixtureN:0,
            teamId:0,
            tableModal: false,
            fixtureModal:false,
            fixtureNumber:0,
            nextGameModal:false,
            teamRating:0,
            transferModal: false,
            buyPlayerId:0,
            sellPlayerId:0,
            sellTeamId:0,
            buyTeamId:0,
            restartModal: false,
            selectedOption:null,
            teamBudget:0,
            champModal:false,
            selectedFile: []
        }
    }

    getTeams =()=> {
        axios({
            url: "http://localhost:8080/teams",
            method: "get",
            params: {
                teamName: ""
            }
        }).then(res => {
            this.setState({
                teams: res.data
            })
        })
    };

    getPlayers=(val)=> {
        axios({
            url: "http://localhost:8080/getPlayers",
            method: "get",
            params: {
                teamId: val
            }
        }).then(res => {
            this.setState({
                players: res.data.players,
                teamRating: res.data.overallRating,
                teamBudget: res.data.teamBudget
            })
        })
    };



    componentDidMount() {
        this.getTeams();
    }

    render() {
        const{teams, players, champModal, restartModal, teamBudget, selectedFile, teamId, teamRating,buyPlayers,sellPlayers, sellTeamId, buyTeamId, buyPlayerId, sellPlayerId, fixtureNumber, transferModal, fixtureN, nextGame, nextGameModal, fixtures, fixtureModal, tableModal} = this.state;

        // const fileSelectHandler =(event)=> {
        //     this.setState({
        //         selectedFile: event.target.files
        //     })
        // };
        //
        // const fileUploadHandler =()=> {
        //     const fd = new FormData();
        //     for (const key of Object.keys(selectedFile)) {
        //         fd.append('image', selectedFile[key], selectedFile.name);
        //     }
        //
        //     axios({
        //         url:"http://localhost:8080/uploadFiles",
        //         method:"post",
        //         params: {
        //             files: selectedFile
        //         },
        //
        //     })
        //
        //     axios.post('http://localhost:8080/uploadFiles', fd, {
        //         onUploadProgress: progressEvent => {
        //             console.log('Upload Progress: ' + Math.round(progressEvent.loaded / progressEvent.total * 100) + "%");
        //         }
        //     })
        // };

        const openModal=()=> {
            this.getTeams();
            this.setState({
                tableModal: true
            })
        };

        const openFixtureModal =()=> {
            this.setState({
                fixtureModal:true
            })
        };

        const openTransferModal =()=> {
            this.setState({
                transferModal:true
            })
        };

        const openRestartModal=()=> {
            this.setState({
                restartModal:true
            })
        }

        const openNextGameModal=()=> {

            axios({
                url: "http://localhost:8080/nextGame",
                method: "get",
            }).then(res => {
                this.setState({
                    nextGame: res.data.list,
                    fixtureN: res.data.fixtureNumber
                })
            });

            this.setState({
                nextGameModal: true
            })
        };

        const openChampModal=()=> {
            this.setState({
                champModal: true
            })
        };

        const forPlayersSell =(event)=> {
            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});

            axios({
                url: "http://localhost:8080/getPlayers",
                method: "get",
                params: {
                    teamId: event.target.value
                }
            }).then(res => {
                this.setState({
                    sellPlayers: res.data.players,
                    teamRating: res.data.overallRating
                })
            })
        };

        const forPlayersBuy =(event)=> {
            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});

            axios({
                url: "http://localhost:8080/getPlayers",
                method: "get",
                params: {
                    teamId: event.target.value
                }
            }).then(res => {
                this.setState({
                    buyPlayers: res.data.players,
                    teamRating: res.data.overallRating
                })
            })
        };


        const getFixtures =(event)=> {
            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});

            axios({
                url: "http://localhost:8080/fixtures",
                method: "get",
                params: {
                    fixtureNumber: event.target.value
                }
            }).then(res => {
                this.setState({
                    fixtures: res.data
                })
            })
        };

        const playGame=()=> {
            axios({
                url: "http://localhost:8080/playGame",
                method: "put",
            }).then(res => {
                this.setState({
                    nextGame: res.data
                })
            })

            axios({
                url: "http://localhost:8080/nextGame",
                method: "get",
            }).then(res => {
                this.setState({
                    fixtureN: res.data.fixtureNumber
                })
            });
        };

        const restartSeason=()=> {
            axios({
                url: "http://localhost:8080/restart",
                method: "put",
            }).then(res => {
                alert("Season started again!");
                this.componentDidMount();
            })
        };

        const transfer=()=> {
            axios({
                url: "http://localhost:8080/transfer",
                method: "put",
                params: {
                    buyPlayerId: buyPlayerId,
                    sellPlayerId: sellPlayerId
                }
            }).then(res => {
                if(res.data.success) {
                    alert(res.data.message);
                    close();
                    this.componentDidMount();
                }
                else {
                    alert(res.data.message);
                    close();
                }
            })
        };

        const nextSeason=()=> {
            axios({
                url: "http://localhost:8080/nextSeason",
                method: "put",
            }).then(res => {
                alert("Welcome to next Season");
                close();
                this.componentDidMount();
            })
        };

        const loadOptions = (inputValue, callback) => {
            axios({
                url: "http://localhost:8080/teams",
                method: "get",
                params: {
                    teamName:inputValue
                }
            }).then(res => {
                const tempArray=[];
                res.data.forEach((element) => {
                    tempArray.push({value:element.id, label:element.name})
                });
                callback(tempArray);
            })
        };

        const handleTypes = (event) => {
            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});
        };

        const close = ()=> {
            this.setState({
                tableModal: false,
                fixtureModal: false,
                fixtures:[],
                nextGameModal:false,
                transferModal:false,
                teamRating:0,
                restartModal: false,
                champModal:false
            })
        };

        const handleChange = selectedOption => {
            this.setState({selectedOption: selectedOption});
            this.getPlayers(selectedOption.value)
        };

        return(
            <div>
                  <Modal isOpen={tableModal}>
                    <ModalHeader>
                        Table
                    </ModalHeader>
                    <ModalBody>
                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Goals</th>
                                <th>Points</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                teams.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.name}</th>
                                        <th>{item.goals}</th>
                                        <th>{item.points}</th>
                                    </tr>
                                )
                            }
                            </tbody>
                        </table>
                        <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                        {/*<button style={{marginLeft: 250}} className={fixtureN==0?"btn btn-success":"btn btn-secondary"}>Next season</button>*/}
                        {fixtureN==14?<button style={{marginLeft: 280}} className={"btn btn-warning"} onClick={nextSeason}>Next Season</button>:""}
                    </ModalBody>
                </Modal>

                <Modal isOpen={champModal}>
                    <ModalHeader>
                        Champions List
                    </ModalHeader>
                    <ModalBody>
                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Champ Time</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                teams.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.name}</th>
                                        <th>{item.champion}</th>
                                    </tr>
                                )
                            }
                            </tbody>
                        </table>
                        <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                    </ModalBody>
                </Modal>

                <Modal isOpen={fixtureModal}>
                    <ModalHeader>
                        Fixtures
                    </ModalHeader>
                    <ModalBody>
                        <label>Select a fixture number: </label>
                        <select name={"fixtureNumber"}  onChange={getFixtures}>
                            <option value={"0"}>Tanlang...</option>
                            <option value={"1"}>1</option>
                            <option value={"2"}>2</option>
                            <option value={"3"}>3</option>
                            <option value={"4"}>4</option>
                            <option value={"5"}>5</option>
                            <option value={"6"}>6</option>
                            <option value={"7"}>7</option>
                            <option value={"8"}>8</option>
                            <option value={"9"}>9</option>
                            <option value={"10"}>10</option>
                            <option value={"11"}>11</option>
                            <option value={"12"}>12</option>
                            <option value={"13"}>13</option>
                            <option value={"14"}>14</option>
                        </select>
                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Team1</th>
                                <th>Score</th>
                                <th>Team2</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                fixtures.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.team1.name}</th>
                                        <th>{item.goalTeam1} - {item.goalTeam2}</th>
                                        <th>{item.team2.name}</th>
                                    </tr>
                                )
                            }
                            </tbody>
                        </table>
                        <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                    </ModalBody>
                </Modal>


                <Modal isOpen={nextGameModal}>
                    <ModalHeader>
                        Next Game
                    </ModalHeader>
                    <ModalBody>
                        <h5>Fixture #{fixtureN}</h5>
                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Team1</th>
                                <th>Score</th>
                                <th>Team2</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                nextGame.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.team1.name}</th>
                                        <th>{item.goalTeam1} - {item.goalTeam2}</th>
                                        <th>{item.team2.name}</th>
                                    </tr>
                                )
                            }
                            </tbody>
                        </table>
                        <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                        <button className={"btn btn-success"} onClick={playGame}>Play</button>
                    </ModalBody>
                </Modal>

                <Modal isOpen={transferModal}>
                    <ModalHeader>
                       Transfer player
                    </ModalHeader>
                    <ModalBody>
                        <form onSubmit={transfer}>
                            <label>Purchase player from team: </label>
                            <select className={"form-control"} name={"buyTeamId"}  onChange={forPlayersBuy}>
                                <option value={"0"}>Choose...</option>
                                {
                                    teams.map(item =>
                                        <option value={item.id}>{item.name}</option>)
                                }
                            </select><br/>
                            <label>Select player: </label>
                            <select className={"form-control"} name={"buyPlayerId"}  onChange={handleTypes}>
                                <option value={"0"}>Choose...</option>
                                {
                                    buyPlayers.map(item =>
                                        <option value={item.id}>{item.name} {item.rating}</option>)
                                }
                            </select><br/>
                            <label>Sell player to team: </label>
                            <select className={"form-control"} name={"sellTeamId"}  onChange={forPlayersSell}>
                                <option value={"0"}>Choose...</option>
                                {
                                    teams.map(item =>
                                        <option value={item.id}>{item.name}</option>)
                                }
                            </select><br/>
                            <label>Select player: </label>
                            <select className={"form-control"} name={"sellPlayerId"}  onChange={handleTypes}>
                                <option value={"0"}>Choose...</option>
                                {
                                    sellPlayers.map(item =>
                                        <option value={item.id}>{item.name} {item.rating}</option>)
                                }
                            </select><br/>

                            <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                            <button className={"btn btn-success"}>Submit</button>
                        </form>
                    </ModalBody>
                </Modal>


                <Modal isOpen={restartModal}>
                    <ModalHeader>
                        Do you want to restart the season?
                    </ModalHeader>
                    <ModalBody>
                            <form onSubmit={restartSeason}>
                                <button type={"button"} className={"btn btn-danger"} onClick={close}>No</button>
                                <button style={{marginLeft: 10}} className={"btn btn-success"}>Yes</button>
                            </form>
                    </ModalBody>
                </Modal>

                <div className={"row"}>
                    <div className={"col"}>
                        <div style={{width:300}}>
                            <AsyncSelect
                                // closeMenuOnSelect={false}
                                cacheOptions
                                loadOptions={loadOptions}
                                defaultOptions
                                isMulti
                                onChange={handleChange}
                                isDisabled={false}
                                isClearable={true}
                            />
                        </div>
                        {/*<label>Select a team: </label>*/}
                        {/*<select name={"teamId"}  onChange={forPlayers}>*/}
                        {/*    <option value={"0"}>Choose...</option>*/}
                        {/*    {*/}
                        {/*teams.map(item =>*/}
                        {/*<option value={item.id}>{item.name}</option>)*/}
                        {/*    }*/}
                        {/*</select>*/}
                       
                        <button type={"button"} className={"btn btn-info float-sm-right"}
                                onClick={openModal}>Cup Table
                        </button>
                        <button type={"button"} className={"btn btn-secondary float-sm-right"}
                                onClick={openFixtureModal}>Fixtures
                        </button>
                        <button type={"button"} className={"btn btn-success float-sm-right"}
                                onClick={openNextGameModal}>Next Game
                        </button>
                        <button type={"button"} className={"btn btn-danger float-sm-right"}
                                onClick={openRestartModal}>Restart cup
                        </button>
                        <button type={"button"} className={"btn btn-success float-sm-right"}
                                onClick={openTransferModal}>Transfer
                        </button>
                        <button type={"button"} className={"btn btn-warning float-sm-right"}
                                onClick={openChampModal}>Champions
                        </button>


                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Player name</th>
                                <th>Position</th>
                                <th>Rating</th>
                                <th>Transfer Fee</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                players.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.name}</th>
                                        <th>{item.position}</th>
                                        <th>{item.rating}</th>
                                        <th>{item.transferFee/1000000}m</th>
                                        </tr>
                                )

                            }
                            </tbody>
                        </table>

                        <h3>Team rating: {teamRating}</h3>
                        <h3>Team budget: {teamBudget/1000000}m</h3>

                        {/*<div className={"form-group"}>*/}
                        {/*    <input  type="file" onChange={fileSelectHandler} multiple/>*/}
                        {/*</div>*/}
                        {/* /!*ref={fileInput => this.fileInput = fileInput}*!/*/}
                        {/*/!*<button onClick={()=> this.fileInput.click()}>Pick Img</button>*!/*/}
                        {/*<button onClick={fileUploadHandler}>Submit</button>*/}

                    </div>
                </div>
            </div>
        )
    }
}

export default TeamControl