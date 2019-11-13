![VisitScotland](https://sttc.visitscotland.com/static/img/logos/scotland-alba-logo-500.png)

# VisitScotland Design System

This is the repository for the **VisitScotland Design System**, which is an implementation of [Vue Styleguidist](https://vue-styleguidist.github.io/) and was developed using [Vue Design System](https://vueds.com) as a starting point. Most of the original Vue Design System functionality should still work. However, the functionality of this package has been enhanced or modified in several areas:

- The included components have been changed to suit VisitScotland's needs and have been moved into subdirectories in the `src/components` folder.
- Build scripts have been added (`yarn docs:remote`, `yarn styleguide:remote` or `yarn styleguide:remote:build`) to build the design system documentation using content from a remote API (Bloomreach Hippo CMS or Contentful).
- Some bugs in VDS have been fixed.
- The Color component has been enhanced with categories and accessibility checks.
- A componentised version of the build has been created and is used as the primary build.
- NPM has been replaced with Yarn package manager.
- A Bloomreach Hippo CMS app has been added in the folder `hippo`, which acts as the source code for the CMS site to provide content to the design system.
- The Bloomreach Hippo CMS app includes a `frontend` package that consumes the design system components for demo use in the Hippo CMS site.

## Getting started

Install the package:

- `git clone https://username@bitbucket.visitscotland.com/scm/vscom/design-system.git`
- `cd design-system`
- `yarn install`

## Working with the design system (documentation) site

This package is able to generate a design system website that documents your components and site in general. There are various builds to either generate and start a local server running the site or generate artefacts for publication of the site elsewhere.

### Running a local development version of the design system

The design system site can be generated and served locally using various NPM scripts. All of these scripts use the `config/docs.config.js` to inform the layout and content of the generated site. The following commands will generate the site from this config file and associated local markdown files.

1. `yarn build` (compiles tokens and builds site)
2. `yarn styleguide`

After running those commands the documentation site should be available at [localhost:6060](http://localhost:6060). Modifications to source files will trigger a rebuild and refresh the browser. However, tokens will not be re-generated automatically on file change so this must be done manually.

An alternative build merges the local `config/docs.config.js` config with content from a remote database to generate the site. This build [requires the relevent remote config and environment variables](#remote-config).

`yarn docs:remote` (includes token compilation)

### Building a static version of the documentation site

The following script generates build artefacts for the documentation site into `dist/docs`, using the config in `config/docs.config.js`:

`yarn build:docs`

To include remote content in that build use this script instead ([along with the relevent remote config](#remote-config)):

`yarn build:docs:remote`

To serve the artefacts use this script, which starts a http-server in the `dist/docs` folder:

`yarn serve-docs`

### <a name="remote-config"></a> Configuring the remote build

Some of the builds (`yarn docs:remote` or `yarn styleguide:remote:build`) merge the local `config/docs.config.js` with content from a remote API to generate the design system site. These builds require valid configuration, as follows:

- Edit `config/remote.docs.config.js`, specifying sets of remote config profiles, each with URL, params and transforms. NOTE: the project already includes profiles for Hippo and Contentful.
- Set the `VS_DS_REMOTE_CONFIG_URL` and any other environment variables (e.g. `VS_DS_REMOTE_CONFIG_HIPPO_PROJECT_NAME`) by specifying them in a `.env` file in the package root, or manually some other way.

Common environment variables for remote config:
`VS_DS_REMOTE_CONFIG_STRICT_SSL`

`VS_DS_REMOTE_CONFIG_URL`

Environment variables for Hippo and Contentful config:
`VS_DS_REMOTE_CONFIG_HIPPO_SECTIONS_FIELD_TITLE`

`VS_DS_REMOTE_CONFIG_HIPPO_SECTIONS_CONTENT_FIELD_TITLE`

`VS_DS_REMOTE_CONFIG_HIPPO_PROJECT_NAME`

`VS_DS_REMOTE_CONFIG_CONTENTFUL_TOKEN`

Only the `VS_DS_REMOTE_CONFIG_URL` variable is truly needed. However, some of the other variables will be needed to ensure to specific remote profile is carried out properly.

By default, the build selects the first profile defined in the `config/remote.docs.config.js` export. To select a different profile, alter the npm script to pass the name of the desired profile as the --remote-profile arg passed to to the script, e.g:

`yarn docs:remote --remote-profile contentful`

## Using the design system assets in other apps

The "system" and "system components" builds will compile artefacts for inclusion of the design system components and styles in other projects.

### System components build

**This script is the recommended way of including assets from the design system in other projects.**

This build creates a set of artefacts that allow for the inclusion of specific components and global site-wide styles of the design system in other projects.

- `yarn build:system:components`

This script compiles the tokens (via theo) then compiles the design system assets into discrete chunks and generates a manifest.json file in the `dist/` folder. The manifest.json file lists which of the generated assets are needed to include each Vue component and Vue store in another project.

To include a component in another project:

- Reference all the relevent assets for the component as listed in the manifest.json.
- Register the component using the [`Vue.component()` function](https://vuejs.org/v2/guide/components-registration.html) or locally in another component/app.

The build also creates a core module (consisting of core.js and core.css artefacts) that contains any styles that need to applied globally to all components, such as the Bootstrap reboot styles. To add JS to this module, include it in `src/utils/core.system-components.js`, which is the entry file for the core module. This entry file imports `src/styles/core.styles.scss`, so add any global Sass to that file.

NOTE: An example webpack consuming package can be found in the `hippo/frontend` folder.

### System build

This is the default Vue Design System build and produces a monolithic asset for inclusion of **all parts** of the design system in another project. This script is generally not how we use the design system at VS.

- `yarn build:system`

**NOTE: this build is not supported in this package and may not function**

Detailed instructions for including the design system in this way [are detailed here](https://github.com/viljamis/vue-design-system/wiki/getting-started#using-design-system-as-an-npm-module).

## Development workflow

The best way to develop the design system is to run a local development instance of the documentation site, as detailed above.

To see how the local, modified version of the design system behaves in a target local project, carry out the following to link the target project's dependency to the local version, rather than the published version, of the design system assets. You will find an example of such an app, which runs these commands during it's install, in this repository at `hippo/frontend`.

- `cd design-system`
- `yarn link`
- `cd ../target-project`
- `yarn link vs-dotcom-ds`

NOTE: It is `vs-dotcom-ds` because that is this package's name.

This will create a symlink between the target project folder and the local design-system folder. The target project's vs-dotcom-ds dependency will use the local design-system folder directly.

**It is advised to reference the built artefacts in the `dist/` folder, in which case the design system assets will need to be rebuilt (using `yarn build:system:components`) for changes to source files to be propogated into the target project**.

## VueX

Adhere to [this styleguide](https://docs.vuestorefront.io/guide/vuex/vuex-conventions.html) and the following exceptions and additions when developing Vuex stores:

- Locate all Vuex stores with the components that use them the most.
- All Vuex stores must be modularised and namespaced.
- You do not need to include the module name in getters unless your modules are **NOT** namespaced, which they should be (see above).
- Do not register a store via the `store` key of a Vue component's configuration object. This will alow multiple stores to be.
- Only use getters and actions in external modules.
- Define getter and action names in a separate file and import the ones you need into the store and any other consumers.

To include VueX functionality in one or more of your components:

- Write a VueX store and then import and include it in the component definition object - this is enough to add the store's functionality to _components rendered in the design system documentation_.
- _To make the store functionality work in the production (system components) build_ - VueX must be available globally before the components are registered with Vue.

### Writing VueX Stores

See the VueX example component in the `src/components/examples` folder.

VueX stores should be added to components as needed and must be given a file name ending in `store.js` to ensure proper exposure in the system components build.

Multiple components can use the same store and the state will be shared among them.

```js
import Vuex from "vuex"
import Vue from "vue"

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    beans: {
      namespaced: true,
      state: {
        beans: [],
      },
      getters: {
        getBeanCount(state) {
          return state.beans.length
        },
        isBeanAdded: state => beanId => {
          return includes(state.beans, { id: beanId })
        },
      },
      mutations: {
        addBean(state, newBean) {
          state.beans.push(newBean)
        },
      },
      actions: {
        addBean({ context }, newBean) {
          context.commit("addBean", newBean)
        },
      },
    },
  },
})
```

### Accessing VueX stores in components

Example use inside components:

```js
import beanStore from "./bean-counter.store.js"

export default {
  template: `
    <div>
      <span>{{ totalBeans }}</span>
      <vs-button @click="addBean">Add new bean</vs-button>
    </div>
  `,
  computed: {
    totalBeans() {
      return beanStore.getters["beans/getBeanCount"]
    },
    methods: {
      addBean(arg) {
        return beanStore.dispatch("beans/addBean", this.newBean)
      },
    },
  },
}
```

### Exposure of stores in the system components build

The system components build treats each store as a module and creates a package for it with the naming convention `store<CapitalisedCamelCasedAndRationalisedFileName>`. So a store with the filename of `example.store.js` will be given a module name of `storeExampleStore` (and will be available in JS as `storeExampleStore.default`).

## Addition of fixtures for documentation examples

To make fixtures data available for easy inclusion in `docs` block examples, create `.json` files in `src/assets/fixtures/`, in subfolders if desired. The documentation site build automatically exposes the contained data in a key relative to the camel-cased folder and file names. For example:

`/src/assets/fixtures/header/main-nav.json`

```json

[
  {
    title: "home",
    href: "/"
  },
  ...
]
```

`/src/components/your/Component.vue`

```html
<docs>
  <main-nav>
    <nav-item v-href="header.mainNav[0].href">
      {{ header.mainNav[0].title }}
    </nav-item>
  </main-nav>
</docs>
```

## Logging

This package includes the `vuejs-logger` NPM package, which provides logging capabilities. To use it import the `src/utilities/logger.js` utility and use it as follows.

```js
import logger from "@/utils/logger"

logger.debug("test", this.a, 123)
logger.info("test", this.b)
logger.warn("test")
logger.error("test")
logger.fatal("test")
```

Check the [`vuejs-logger` documentation](https://www.npmjs.com/package/vuejs-logger) for further examples and options. Note that the syntax used in the examples shown here differs from the syntax used in the documentation. Use the syntax shown here to make it more obvious you're using an import.

To change the `vuejs-logger` configuration alter the options object passed to it in `src/utilities/logger.js`.

## Rendora Server-side rendering POC

The Rendora app in this repo can be used to run a local SSR service for prototyping of the use of SSR to render this repo's Hippo site, which includes Vue components or components from some other frontend framework.

The following instructions list how to get the demo running. These instructions install GO and build Rendora manually. There's also a Docker image for installing and running Rendora running - check the README inside the Rendora folder for details on how to get that to work. It would be replacing steps 2 and 3.

1. Install and build frontend assets

- `yarn` (in root directory)
- `cd hippo/frontend`
- `yarn`
- `yarn build`

2. Install Rendora

- Install GO V1.11
- `cd rendora` (from root directory)
- `make build`
- `sudo make install`

3. Run headless Chrome and Rendora

- `google-chrome --headless --remote-debugging-port=9222`
- `cd rendora` (from root directory)
- `rendora --config config.yaml`

4. Run Hippo

- `cd hippo` (from root directory)
- `mvn clean verify`
- `mvn -Pcargo.run -Dpath.repo=storage`

5. Enjoy that sweet SSR nectar

- Go to [http://localhost:8080/site/demo](http://localhost:8080/site/demo) and view source to see the Vue demo page without SSR
- Go to [http://localhost:3001/site/demo](http://localhost:3001/site/demo)

## TODO

- Fix deep selectors issue \*
- Alter docs to allow inclusion of dynamic full width view in examples \*
- Improve readme with more info (e.g. bootstrap components, sass and utilities, colours and docs colours, icon usage) \*
- Refactor sidebar nav to work more smoothly \*
- Implement permanent solution for inclusion of vue and vue-x dependencies \*
- Create a dev version of the system-components build for live inclusion in Hippo \*
- Change docs:remote (and other?) scripts to use theo:onchange \*
- Refactor breakpoints.bootstrap-vue.config.js breakpoints definition to generate dynamically from tokens
- Refactor theme colours so they are specified in a tokens file and auto generated (bootstrap)
- Refactor button and other components to use autogenerated theme colours

## To Add to readme

- Bootstrap and BootstrapVue
- Sass and utilities
- Colours and docs colours
- Icons
- Setup of Vue and VueX as externals in the system components build
