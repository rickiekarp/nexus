import React, { useState, useEffect } from "react";
import { makeStyles } from '@material-ui/styles';
import { Grid } from '@material-ui/core';

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


const Contact = () => {
  const classes = useStyles();

  const [hasError, setErrors] = useState(false);
  const [contactData, setContactData] = useState({});

  useEffect(() => {
    async function fetchData() {
      const res = await fetch("/HomeServer/api/contact");
      res
        .json()
        .then(res => setContactData(res))
        .catch(err => setErrors(err));
    }

    fetchData();
  }, []);

  console.log(contactData);

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

          </div>
        </Grid>
      </Grid>
    </div>
  );
};

export default Contact;