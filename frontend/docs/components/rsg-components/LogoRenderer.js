import React from 'react';
import PropTypes from 'prop-types';
import Styled from 'rsg-components/Styled';
import logo from '@images/design_system_logo.svg';

const styles = () => ({
	image: {
		width: '12em',
	},
});

export function LogoRenderer({ classes }) {
    return (
        <img className={classes.image} src={logo} alt="VisitScotland Logo" key="vs-logo-img"/>
	);
}

LogoRenderer.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default Styled(styles)(LogoRenderer);
