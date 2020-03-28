import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/styles';
import { AppBar, Toolbar, IconButton, Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';

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
