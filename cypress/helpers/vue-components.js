const mountVue = require("cypress-vue-unit-test")

const { map, zipObject, join } = require("lodash")

export default {
  init: init,
}

function init(tag, definition, data, childContent) {
  const template =
    `
        <div id="app">
            <component is="` +
    tag +
    `" ` +
    join(map(data, constructTemplateProp), " ") +
    `>` +
    childContent +
    `</component>
        </div>
    `

  const app = {
    template,
    data,
  }

  const extensions = {
    components: { [tag]: definition },
  }

  beforeEach(mountVue(app, { extensions }))
}

function constructTemplateProp(value, propName) {
  return ":" + propName + '="' + propName + '"'
}
