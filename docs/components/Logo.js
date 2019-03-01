import React from "react"
import PropTypes from "prop-types"
import Styled from "rsg-components/Styled"
import logoSvg from "../../src/assets/svg/logo.svg"

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
}

LogoRenderer.propTypes = {
  classes: PropTypes.object.isRequired,
  children: PropTypes.node,
}

export default Styled(styles)(LogoRenderer)
