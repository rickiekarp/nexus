import React from 'react';
import { makeStyles } from '@material-ui/styles';
import { Grid, Typography } from '@material-ui/core';
import { Link } from 'react-router-dom';

import {
  Button
} from '@material-ui/core';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(4)
  },
  content: {
    paddingTop: 150,
    textAlign: 'center'
  },
  image: {
    marginTop: 50,
    display: 'inline-block',
    maxWidth: '100%',
    width: 560
  }
}));

const Home = () => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid
        container
        justify="center"
        spacing={4}
      >
        <Grid
          item
          lg={6}
          xs={12}
        >
          <div className={classes.content}>
            <Link to="/dashboard">
              <Button renderAs="button" color="primary">
                <span>Dashboard</span>
              </Button>
            </Link>
          </div>
        </Grid>
      </Grid>
    </div>
  );
};

export default Home;
