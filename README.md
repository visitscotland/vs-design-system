![VisitScotland](https://sttc.visitscotland.com/static/img/logos/scotland-alba-logo-500.png)

# VisitScotland Design System

This is the repository for the **VisitScotland Design System**, which is a heavily modified version of [Vue Design System](https://vueds.com). It works in the same way as Vue Design System, with some changes:

- The included components have been changed to to suit VisitScotland's needs and have been moved into the `src/components` folder.
- Build scripts have been added (`npm run docs:remote`, `npm run styleguide:remote` or `npm run styleguide:remote:build`) to build the design system documentation using section content from a remote API.
- Some bugs in VDS have been fixed.
- The Color component has been changed to display colours in categories.
- The system build has been modified to include a "componentised" version of the build ()

## Getting started

Install the package:

- `git clone https://username@bitbucket.visitscotland.com/scm/vscom/design-system.git`
- `cd design-system`
- `npm install`

## Working with the documentation

### Running a local development version of the documentation site

The following commands will begin a development instance of the design system documentation site:

- `npm run build` (ensures token compilation)
- `npm run styleguide`

The documentation site should then be available at localhost:6060. Modifications to source files will trigger a rebuild and refresh of the browser.

### Building a static version of the documentation site

The following commands will build a static version of the documentation at `dist/docs` and serve it using http-server on the default port:

- `npm run build` (ensures token compilation)
- `npm run styleguide:build`
- `npm run serve-docs` (starts http-server in target dist folder)

### Populating the documentation site with remote content

It's possible to get the title and sections (pages) content for the documentation site from a remote API, during build. In order to do so:

- Edit `config/remote.config.js`, specifying sets of remote config profiles, each with URL, params and transforms. NOTE: the project already includes profiles for Contentful and Hippo.
- Set the `VS_DS_REMOTE_CONFIG_URL` and any other environment variables (e.g. `VS_DS_REMOTE_CONFIG_HIPPO_PROJECT_NAME`) by specifying them in a `.env` file in the package root, or manually some other way.
- Run the remote styleguide scripts (`npm run docs:remote`, `npm run styleguide:remote` or `npm run styleguide:remote:build`) instead of the regular scripts.

By default, the build selects the first profile defined in the `config/remote.config.js` export. To select a different profile, alter the npm script to pass the name of the desired profile as the --remote-profile arg to the `build/styleguide` script, e.g:

Common environment variables for remote config:
VS_DS_REMOTE_CONFIG_STRICT_SSL
VS_DS_REMOTE_CONFIG_URL

Environment variables for Hippo and Contentful config:
VS_DS_REMOTE_CONFIG_HIPPO_SECTIONS_FIELD_TITLE
VS_DS_REMOTE_CONFIG_HIPPO_SECTIONS_CONTENT_FIELD_TITLE
VS_DS_REMOTE_CONFIG_HIPPO_PROJECT_NAME
VS_DS_REMOTE_CONFIG_CONTENTFUL_TOKEN

```
    npm run docs:remote --remote-profile contentful
```

### Publishing the documentation to Heroku

If you are new to Heroku, [this guide](https://devcenter.heroku.com/articles/getting-started-with-nodejs) is a good guide to get you started with it.

The following commands will publsh the documentation site to a Heroku app.

- [Install and configure Heroku locally](https://devcenter.heroku.com/articles/getting-started-with-nodejs#set-up)
- `cd design-system`
- `heroku create`
- `git push heroku master`

Pushing to the Heroku remote triggers the following npm scripts in order on the Heroku server:

- build
- heroku-postbuild
- start

Editing any of those npm scripts will change what occurs on the Heroku server.

## Using the design system assets in other apps

The "system" and "system components" builds will compile artefacts for inclusion of the design system components and styles in other projects.

### System components build

This build creates a set of artefacts that allow for the inclusion of specific components of the design system in other projects. It is the recommended way of including assets from the design system in other projects.

- `npm run build:system:components`

This command compiles the tokens (via theo) then compiles the design system assets into discrete chunks and generates a manifest.json file in the `dist/system-components` folder. The manifest.json file lists which of the generated assets are needed to include each component in another project.

To include a component in another project:

- Reference all the relevent assets for the component as listed in the manifest.json
- Register the component using the [`Vue.component()` function](https://vuejs.org/v2/guide/components-registration.html)

### System build

This is the default Vue Design System build and produces an asset for inclusion of the **whole** design system in another project.

- `npm run build:system`

Detailed instructions for including the design system in this way [are detailed here](https://github.com/viljamis/vue-design-system/wiki/getting-started#using-design-system-as-an-npm-module).

## Developing the design system

The best way to develop the design system is to run a local development instance of the documentation site, as detailed above.

To see how the local, modified version of the design system behaves in a target local project, carry out the following to link the target project's dependency to the local version, rather than the published version, of the design system assets:

- `cd design-system`
- `yarn link` OR `npm link`
- `cd ../target-project`
- `yarn link vs-dotcom-ds` OR `npm link vs-dotcom-ds`

NOTE: It is `vs-dotcom-ds` because that is this package's name.

This will create a symlink between the target project folder and the local design-system folder. The target project's vs-dotcom-ds dependency will use the local design-system folder directly.

**It is advised to reference the built artefacts in the `dist/system` or `dist/system-components` folders, in which case the design system assets will need to be rebuilt (using `npm run build:system:components` or `npm run build:system` for changes to source files to be propogated into the target project**.

## VueX

To include VueX functionality in one or more of your components:

- Write a VueX store and then import and include it in the component definition object - this is enough to add the store's functionality to _components rendered in the design system documentation_.
- _To make the store functionality work in the production (system components) build_ - VueX must be available globally before the components are registered with Vue.

### Writing VueX Stores

See the VueX example component in the `src/components/examples` folder.

VueX stores should be added to components as needed and must be given a file name ending in `store.js` to ensure proper exposure in the system components build.

The stores should be colocalised with the relevant component and _modularised with namespacing_ [as detailed in the VueX docs](https://vuex.vuejs.org/guide/modules.html). The module name should be relevant to the component the store is included in.

Multiple components can use the same store and the state will be shared among them.

```js
import Vuex from "vuex"
import Vue from "vue"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    example: {
      namespaced: true,
      state: {
        count: 0,
      },
      mutations: {
        increment(state) {
          state.count++
        },
      },
      actions: {
        increment(context, message) {
          context.commit("increment")
        },
      },
    },
  },
})
```

### Accessing VueX stores in components

Example use inside components:

```js
export default {
  template: `
    <div>
      <span>{{ current }}</span>
      <vs-button @click="add">Increment</vs-button>
    </div>
  `,
  computed: {
    current() {
      return this.$store.state["moduleName"].count
    },
    methods: {
      add(arg) {
        let arg = this.otherThing

        return this.$store.dispatch("<moduleName>/increment", arg)
      },
    },
  },
}
```

### Exposure of stores in the system components build

The system components build treats each store as a module and creates a package for it with the naming convention `store<CapitalisedCamelCasedAndRationalisedFileName>`. So a store with the filename of `example.store.js` will be given a module name of `storeExampleStore` (and will be available in JS as `storeExampleStore.default`).

## Ideas for improvement

- Move Hippo design system consumer frontend package into design system hippo instance \*
- Fix deep selectors issue \*
- Fix full width view problem \*
- Modify webpack build to expose VueX stores with predictable module names \*
- Improvement readme with more info (e.g. bootstrap components, sass and utilities, colours and docs colours, icon usage) \*
- Improve nested nav sidebar and fix 4th level not being open \*
- Improve colours page to show tints/shades together with main colours \*
- Implement permanent solution for inclusion of vue and vue-x dependencies \*
- Create a dev version of the system-components build \*
- Change docs:remote (and other?) scripts to use theo:onchange
- Refactor breakpoints.bootstrap-vue.config.js breakpoints definition to generate dynamically from tokens
- Refactor theme colours so they are specified in a tokens file and auto generated (bootstrap)
- Refactor button and other components to use autogenerated theme colours
- Colour accessibility like Atlassian
- Improve docs:remote build to allow Hippo integration with that dev build
- XD/Zeplin integration

## To Add to readme

- Bootstrap and BootstrapVue
- Sass and utilities
- Colours and docs colours
- Icons
- Build scripts - description of each and highlight which ones we should be using
- Setup of Vue and VueX as externals in the system components build
