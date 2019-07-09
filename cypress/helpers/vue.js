const mountVue = require("cypress-vue-unit-test")

const { partial, each, clone, pickBy } = require("lodash")

const PROPS_DATA_KEY_NAME = "componentProps"

export default {
  init,
  setProp,
}

function init(tag, definition, props, childContent) {
  const template =
    `
        <div id="app">
            <component is="` +
    tag +
    `" v-bind="computedProps">` +
    childContent +
    `</component>
        </div>
    `

  const initialProps = clone(props)

  const app = {
    template,
    data: {
      [PROPS_DATA_KEY_NAME]: props,
    },
    computed: {
      computedProps() {
        return pickBy(this[PROPS_DATA_KEY_NAME])
      },
    },
  }

  const extensions = {
    components: { [tag]: definition },
  }

  const mount = mountVue(app, { extensions })

  beforeEach(partial(resetProps, initialProps))

  beforeEach(mount)
}

function resetProps(props) {
  if (!Cypress.vue) {
    return
  }
  each(props, (value, propName) => {
    Cypress.vue.$set(Cypress.vue[PROPS_DATA_KEY_NAME], propName, value)
  })
}

function setProp(propName, value) {
  Cypress.vue.$set(Cypress.vue[PROPS_DATA_KEY_NAME], propName, value)
}
