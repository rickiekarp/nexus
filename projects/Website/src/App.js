import React, { Component } from 'react';
import { Chart } from 'react-chartjs-2';
import { ThemeProvider } from '@material-ui/styles';
import validate from 'validate.js';

import { chartjs } from './helpers';
import theme from './theme';
import 'react-perfect-scrollbar/dist/css/styles.css';
import './assets/scss/index.scss';
import validators from './common/validators';
import Routes from './Routes';

import { history } from './_helpers';
import { Router } from 'react-router-dom';

Chart.helpers.extend(Chart.elements.Rectangle.prototype, {
  draw: chartjs.draw
});

validate.validators = {
  ...validate.validators,
  ...validators
};

export default class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
        currentUser: null
    };
  }

  render() {

    return (
      <ThemeProvider theme={theme}>
        <Router history={history}>
          <Routes></Routes>
            {/* <Topbar currentUser={currentUser} authenticationService={authenticationService}></Topbar>
              <Routes></Routes>
            </Paper> */}
        </Router>
      </ThemeProvider>
    );
  }
}
