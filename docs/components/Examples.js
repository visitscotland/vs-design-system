import React, { Component } from "react"
import PropTypes from "prop-types"
import Styled from "rsg-components/Styled"

import { split, last, get } from "lodash"

const styles = () => ({
  // Just default jss-isolate rules
  root: {},
})

export class ExamplesRenderer extends Component {
  static propTypes = {
    classes: PropTypes.object.isRequired,
    children: PropTypes.node,
  }

  state = { fullWidth: false, baseClass: null }

  constructor(props) {
    super(props)
    this.state.baseClass = props.classes.root
  }

  toggleFullWidth = () => {
    this.setState({ fullWidth: !this.state.fullWidth })
  }

  render() {
    let key = last(split(get(this.props, "classes.root"), "-"))

    let toggleButton = React.createElement(
      "button",
      { className: "vds-example-toggle", onClick: this.toggleFullWidth, key: key },
      [(this.state.fullWidth ? "Exit" : "Enter") + " full width view"]
    )

    let className =
      this.state.baseClass +
      " vds-example" +
      (this.state.fullWidth ? " vds-example-full-screen" : "")

    return React.createElement("article", { className: className }, [
      toggleButton,
      ...this.props.children,
    ])
  }
}

export default Styled(styles)(ExamplesRenderer)
