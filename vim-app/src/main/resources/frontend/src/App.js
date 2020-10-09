import React, {Component} from 'react';
import logo from './logo.svg';
import './App.scss';
import {userContext} from "./context/userContext";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      user: {}
    };
  }

  componentDidMount() {
    fetch('/api/userTest')
    .then(response => response.json())
    .then(data => {
      console.log(data);
      this.setState({userName: data.name})
    });
    console.log(this.state.userName)
  }

  render() {
    return (
        <userContext.Provider value={this.state.user}>
          <div className="App">
            <header className="App-header">
              <img src={logo} className="App-logo" alt="logo"/>
              <p>
                {this.state.userName}
              </p>
            </header>
          </div>
        </userContext.Provider>
    )
  };
}

export default App;
