import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import { authenticationService } from '../_services';
import { Main } from 'layouts';
import PropTypes from 'prop-types';

export const PrivateRoute = ({ layout: Layout, component: Component, ...rest }) => (
    <Route {...rest} render={props => {
        const currentUser = authenticationService.currentUserValue;
        if (!currentUser) {
            // not logged in so redirect to login page with the return url
            return <Redirect to={{ pathname: '/signin', state: { from: props.location } }} />
        }

        // authorised so return component
        return (
            <Route
                {...rest}
                render={matchProps => (
                    <Layout>
                    <Component {...matchProps} />
                    </Layout>
                )}
                />
        );
    }} />
)


PrivateRoute.propTypes = {
    component: PropTypes.any.isRequired,
    layout: PropTypes.any.isRequired,
    path: PropTypes.string
  };
  
export default PrivateRoute;
  