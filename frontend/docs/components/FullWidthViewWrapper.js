import React, { Component } from "react"
import { join } from "lodash"

export default class FullWidthViewWrapper extends Component {
  state = { fullWidth: false }

  toggleFullWidth = () => {
    this.setState({ fullWidth: !this.state.fullWidth })
  }

  render() {
    let toggleButton = React.createElement(
      "button",
      { className: "vds-example-toggle", onClick: this.toggleFullWidth, key: 0 },
      [(this.state.fullWidth ? "Exit" : "Enter") + " full width view"]
    )

    return React.createElement("div", { className: this.renderWrapperClasses() }, [
      toggleButton,
      ...this.props.children,
    ])
  }

  renderWrapperClasses() {
    let classes = ["vds-example"]

    if (this.state.fullWidth) {
      classes.push("vds-example-full-screen")
    }

    return join(classes, " ")
  }
}
