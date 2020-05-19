import { head } from "lodash"

import createApp from "./app"

// This is not a very robust way to do this
// TODO: replace with a more robust solution
const isServerRendered = () => {
    const appContainer = head(document.body.children)

    return appContainer && appContainer.hasAttribute("data-server-rendered")
}

const context = {
    vueOptions: {
        el: "[data-vue-app-init]",
    },
}

if (isServerRendered()) {
    context.vueOptions.template = "#app-template"
}

createApp(context)
