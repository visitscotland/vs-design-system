/**
 * This is Vue Design System’s helper util that
 * highlights the currently active nav item.
 */

export default {
  methods: {
    clearActiveLinks() {
      const activeLinks = document.querySelectorAll(".vueds-active")
      if (activeLinks) {
        ;[].forEach.call(activeLinks, function(element) {
          element.classList.remove("vueds-active")
        })
      }
    },
    activateItem(linkItem) {
      linkItem.classList.add("vueds-active")

      // Check for a grandparent nav link item
      const parent = linkItem.parentNode.parentNode

      if (parent && parent.className.match(/(rsg--item)/)) {
        this.activateItem(parent)
      }
    },
    refreshClickHandlers(element, index, navType) {
      if (this.navClickHandlers[navType][index]) {
        element.removeEventListener("click", this.navClickHandlers[navType][index], false)
      }

      this.navClickHandlers[navType][index] = this.click.bind(this)
      element.addEventListener("click", this.navClickHandlers[navType][index], false)
    },
    click(event) {
      if (this.clearActiveLinks) {
        this.clearActiveLinks()
      } else {
        this.methods.clearActiveLinks()
      }

      this.activateItem(event.target.parentNode)
    },
    init() {
      let currentURL = ""
      const sidebar = document.querySelector("div[class^='rsg--sidebar']")

      if (process && process.env && process.env.NODE_ENV === "test") {
        currentURL = "/example/"
      } else {
        currentURL =
          window.location.pathname + window.location.hash.split("?")[0].replace(/%20/g, " ")
      }

      if (sidebar) {
        if (this.clearActiveLinks) {
          this.clearActiveLinks()
        } else {
          this.methods.clearActiveLinks()
        }

        const navLinks = sidebar.querySelectorAll("nav > ul > li > a")
        const subNavLinks = sidebar.querySelectorAll("nav > ul > li ul a")
        const currentPage = sidebar.querySelector("a[href='" + currentURL + "']")
        const search = sidebar.querySelector("div[class^='rsg--search'] input")
        const self = this

        // init handler object
        if (!self.navClickHandlers) {
          self.navClickHandlers = {
            navLinks: {},
            subNavLinks: {},
          }
        }

        if (currentURL) {
          if (currentPage) {
            currentPage.parentNode.classList.add("vueds-active")
            const parent = currentPage.parentNode.parentNode.parentNode
            if (parent.className.match(/(rsg--item)/)) {
              currentPage.parentNode.parentNode.parentNode.classList.add("vueds-active")
            }
          } else if (currentURL === "/" && sidebar.querySelectorAll("a")[0].parentNode) {
            sidebar.querySelectorAll("a")[0].parentNode.classList.add("vueds-active")
          }
        }

        if (search && !search.classList.contains("set")) {
          search.setAttribute("placeholder", "Type to filter")
        }

        // Cleanup

        if (navLinks) {
          ;[].forEach.call(navLinks, function(element, i) {
            self.refreshClickHandlers(element, i, "navLinks")
          })
        }

        if (subNavLinks) {
          ;[].forEach.call(subNavLinks, function(element, i) {
            self.refreshClickHandlers(element, i, "subNavLinks")
          })
        }
      }
    },
  },
  mounted() {
    this.init()
  },
}
