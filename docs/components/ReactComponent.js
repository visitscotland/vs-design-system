import React, { Component } from "react"
import { get, set, map, isEmpty, concat, join, dropRight, split, first, tail } from "lodash"
// import RsgReactComponent from 'rsg-components-default/ReactComponent/ReactComponent'
import VsgReactComponent from "rsg-components/VsgReactComponent/ReactComponent"
import { getHashAsArray } from "react-styleguidist/lib/client/utils/handleHash"

const CHILD_COMPONENT_DESCRIPTION_PRETEXT =
  "<p>This component utilises the following child components:</p>"

export default class ReactComponent extends Component {
  static propTypes = VsgReactComponent.PropTypes

  render() {
    return React.createElement(VsgReactComponent, transformComponentProps(this.props))
  }
}

function transformComponentProps(props) {
  let childComponents = get(props, "component.props.childComponents")

  if (!isEmpty(childComponents)) {
    set(
      props,
      "component.props.description",
      renderComponentDescription(get(props, "component.props.description"), childComponents)
    )
  }

  return props
}

function renderComponentDescription(originalDescription, childComponents) {
  // let localComponents = remove(childComponents, matchesProperty())
  return (
    "<div>" +
    "<p>" +
    originalDescription +
    "</p>" +
    CHILD_COMPONENT_DESCRIPTION_PRETEXT +
    "<ul>" +
    map(childComponents, renderChildComponentListItem) +
    "</ul>" +
    "</div>"
  )
}

function renderChildComponentListItem(componentDetails, componentName) {
  return "<li>" + childComponentLink(componentDetails, componentName) + "</li>"
}

function childComponentIsLocal(componentDetails) {
  return get(componentDetails, "packageName") === get(componentDetails, "packageName")
}

function localChildComponentLinkHref(rawHref, componentName) {
  let rootHashArray
  let currentHashArray = getHashAsArray(window.location.hash)
  let targetArray = split(rawHref, "/")
  let up = false

  if (first(targetArray) === "..") {
    targetArray = tail(targetArray)
    up = true
  }

  targetArray[targetArray.length - 1] = componentName

  rootHashArray = dropRight(currentHashArray, 1 + up)

  return "#/" + join(map(concat(rootHashArray, targetArray), encodeURIComponent), "/")
}

function childComponentLink(componentDetails, componentName) {
  let href = get(componentDetails, "link")

  if (!href) {
    return componentName
  }

  if (childComponentIsLocal(componentDetails)) {
    href = localChildComponentLinkHref(href, componentName)
  }

  return '<a href="' + href + '">' + componentName + "</a>"
}
