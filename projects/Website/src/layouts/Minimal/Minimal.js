import React, { Component } from 'react';
import { Topbar } from './components';
import { authenticationService } from '../../_services';
import './Minimal.css';

export default class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
        currentUser: null
    };
  }

  componentDidMount() {
    authenticationService.currentUser.subscribe(x => this.setState({ currentUser: x }));
  }


  render() {
    const { currentUser } = this.state;
    const { children } = this.props;

    return (
      <div id="main" className="minimalcontent">
        <Topbar currentUser={currentUser} authenticationService={authenticationService} />
        <main className="minimalcontent">{children}</main>
      </div>
    );
  }
}