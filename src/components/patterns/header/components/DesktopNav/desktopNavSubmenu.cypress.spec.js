const VsDesktopNavSubmenu = require("./DesktopNavSubmenu.vue").default
const VsDesktopNavListItem = require("./DesktopNavListItem.vue").default
const { vueHelper } = require("@cypress/helpers/index.js").default
const sizes = ["iphone-6", "ipad-2", [1024, 768]]
const mainNav = require("@/assets/fixtures/header/mainNav.json")
const item = mainNav[0]
const level2 = item.subnav[0]
const level3 = level2.subnav[0]

const props = {
  level: 1,
  href: item.href,
  subnav: item.subnav,
  isExternal: item.isExternal,
  title: item.title,
  subnav: item.subnav,
  promoList: item.promoList,
  promoItem: item.promoItem,
  widget: item.widget,
  subnavId: 1,
}

const extensions = {
  components: { VsDesktopNavListItem },
}

const childContent = `
  <vs-desktop-nav-list-item
    slot: "subnav"
    level: 2
    href: ${level2.href}
    subnav: ${level2.subnav}
    isExternal: ${level2.isExternal}
    title: ${level2.title}
  >
    <vs-desktop-nav-list-item
      slot: "subnav"
      level: 3
      href: ${level3.href}
      subnav: ${level3.subnav}
      isExternal: ${level3.isExternal}
      title: ${level3.title}
    >
    </vs-desktop-nav-list-item>
  </vs-desktop-nav-list-item>
`
vueHelper.init("vs-desktop-nav-submenu", VsDesktopNavSubmenu, { props, childContent, extensions })

describe("Desktop Nav Submenu component", () => {
  sizes.forEach(size => {
    context(`${size} resolution`, () => {
      beforeEach(() => {
        if (Cypress._.isArray(size)) {
          cy.viewport(size[0], size[1])
        } else {
          cy.viewport(size)
        }
      })

      it("should render a list item", () => {
        cy.get('[data-test="desktop-nav-submenu"]').should("be.visible")
      })
    })
  })
})
