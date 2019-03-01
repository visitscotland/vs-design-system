import React from "react"
import PropTypes from "prop-types"
import Styled from "rsg-components/Styled"
import logoSvg from "../../src/assets/svg/logo.svg"
// import docsConfig from '../../config/docs.config.js';

const logoUrl =
  "http://www.scopeproductions.co.uk/wp/wp-content/uploads/2016/10/LOGO-CLIENT-VISITSCOTLAND.png"

const styles = ({ fontFamily, color }) => ({
  title: {
    fontFamily: fontFamily.base,
    fontSize: 18,
    fontWeight: "normal",
  },
  svgWrapper: {
    width: "55%",
    marginBottom: "7px",
  },
})

export function LogoRenderer({ classes, children }) {
  const transformedLogo = logoSvg.replace(/^<svg /, `<svg style="fill: white" `)
  const logoWrapped = React.createElement("div", {
    dangerouslySetInnerHTML: { __html: transformedLogo },
    className: classes.svgWrapper,
  })

  const h1 = React.createElement("h1", { className: classes.title }, children)

  return [logoWrapped, h1]

  // const img = React.createElement('img', { className: classes.image, src: logoUrl, key: 'thistle-ds-logo-01' })
  // const h1 = React.createElement('h1', null, children);

  // return [img, h1];

  // return (
  // 	<h1 className={classes.logo}>
  // 		<img className={classes.image} src={logo} />
  // 		{children}
  // 	</h1>
  // );
}

LogoRenderer.propTypes = {
  classes: PropTypes.object.isRequired,
  children: PropTypes.node,
}

export default Styled(styles)(LogoRenderer)
