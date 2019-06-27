const mountVue = require("cypress-vue-unit-test")

const { map, join, partial, each, clone } = require("lodash")

export default {
  init,
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

  const initialData = clone(data)

  const app = {
    template,
    data,
  }

  const extensions = {
    components: { [tag]: definition },
  }

  const mount = mountVue(app, { extensions })

  beforeEach(partial(resetState, initialData))

  beforeEach(mount)
}

function constructTemplateProp(value, propName) {
  return ":" + propName + '="' + propName + '"'
}

function resetState(data) {
  if (!Cypress.vue) {
    return
  }
  each(data, (value, key) => {
    Cypress.vue[key] = value
  })
}
