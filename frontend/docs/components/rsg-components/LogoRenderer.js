import React from 'react';
import PropTypes from 'prop-types';
import Styled from 'rsg-components/Styled';
import logo from '@images/ds-logo-png.png';

const styles = () => ({
	image: {
		width: '230px',
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
