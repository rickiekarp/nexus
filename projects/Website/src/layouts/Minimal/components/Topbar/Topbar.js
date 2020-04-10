import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import { AppBar, Toolbar, IconButton, Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';
import './Topbar.css';

const useStyles = makeStyles(() => ({
  colorPirmary: {
    boxShadow: 'none',
    background: 'blue'
  }
}));

const Topbar = props => {
  const { className, ...rest } = props;

  const classes = useStyles();

  return (
    <AppBar
      className={classes.root, className}
      position="fixed"
    >
      <Toolbar>
        <nav class="navbar navbar-fixed-top navbar-dark bg-inverse">
          <div class="container">
            <Link to="/home">
              <Button renderAs="button" color="primary">
                <span>Home</span>
              </Button>
            </Link>
            <Link to="/projects">
              <Button renderAs="button" color="primary">
                <span>Projects</span>
              </Button>
            </Link>
            <Link to="/resume">
              <Button renderAs="button" color="primary">
                <span>Resume</span>
              </Button>
            </Link>
            <Link to="/contact">
              <Button renderAs="button" color="primary">
                <span>Contact</span>
              </Button>
            </Link>
          </div>
        </nav>

        <Link to="/dashboard">
          <Button renderAs="button" color="primary">
            <span>Dashboard</span>
          </Button>
        </Link>
        <Link to="/sign-in">
          <Button renderAs="button" color="primary">
            <span>Login</span>
          </Button>
        </Link>
      </Toolbar>
    </AppBar>
  );
};

Topbar.propTypes = {
  className: PropTypes.string
};

export default Topbar;
