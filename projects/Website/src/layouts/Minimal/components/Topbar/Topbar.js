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

  const { history } = props;

  function logout() {
    props.authenticationService.logout();
    history.push('/login');
  }

  return (
    <AppBar
      className={classes.root, className}
      position="sticky"
    >
      <Toolbar>
        <nav className="navbar navbar-fixed-top navbar-dark bg-inverse">
          <div className="container">
            <Link to="/home">
              <Button color="primary">
                <span>Home</span>
              </Button>
            </Link>
            <Link to="/projects">
              <Button color="primary">
                <span>Projects</span>
              </Button>
            </Link>
            <Link to="/resume">
              <Button color="primary">
                <span>Resume</span>
              </Button>
            </Link>
            <Link to="/contact">
              <Button color="primary">
                <span>Contact</span>
              </Button>
            </Link>
          </div>
        </nav>

        <div>
            { props.currentUser &&
                <nav className="navbar navbar-fixed-top navbar-dark bg-inverse">
                  <div className="container">
                    <Link to="/dashboard">
                      <Button color="primary">
                        <span>Dashboard</span>
                      </Button>
                    </Link>
                    <Link to="/login">
                      <Button color="primary" onClick={logout}>
                        <span>Logout</span>
                      </Button>
                    </Link>
                  </div>
                </nav>
            }

            {!props.currentUser &&
                <Link to="/login">
                  <Button color="primary">
                    <span>Login</span>
                  </Button>
                </Link>
            }
        </div>
        
      </Toolbar>
    </AppBar>
  );
};

Topbar.propTypes = {
  className: PropTypes.string
};

export default Topbar;
