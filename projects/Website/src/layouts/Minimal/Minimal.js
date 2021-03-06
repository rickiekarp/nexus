import React, { Component } from 'react';
import { Topbar } from './components';
import { authenticationService } from '../../_services';

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
      <div id="main" >
        <Topbar currentUser={currentUser} authenticationService={authenticationService} />
        <main>{children}</main>
      </div>
    );
  }
}