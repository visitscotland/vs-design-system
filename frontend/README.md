![VisitScotland](https://sttc.visitscotland.com/static/img/logos/scotland-alba-logo-500.png)

# Vue SSR

This branch includes an SSR package that can be used to spin up a NodeJS/Express app that performs SSR on a target site. When a client request comes into the app, a proxy request to the target site is made at the same path. The page HTML that is returned is rendered by Vue SSR and returned to the client.

## Get Started

To install and start the local Hippo and NodeJS SSR instances:

```sh
# Install and run the parent Hippo site (this also installs and builds the necessary FE assets)
mvn clean verify && mvn -Pcargo.run

# Add the relevant environment variables to the `frontend/.env` file, creating if necessary
touch frontend/.env

# Start the SSR app on http://localhost:8082
cd frontend
yarn ssr:start
```

This will start:
- the Hippo instance at [http://localhost:8080](http://localhost:8080)
- the SSR app at [http://localhost:8082](http://localhost:8082)

The Hippo instance is basically unchanged from the existing Hippo instance. To browse the SSR'ed Hippo site through the SSR app, browse to the same URL except at the SSR app host.

For example, to view the SSR version of the Hippo page [http://localhost:8080/site/see-do/victorian-heritage-trail](http://localhost:8080/site/see-do/victorian-heritage-trail), browse to [http://localhost:8082/site/see-do/victorian-heritage-trail](http://localhost:8082/site/see-do/victorian-heritage-trail).

The site is browsable just like the regular Hippo site. Note that it's not possible to access the CMS pages via the SSR site.

**NOTE: do not use the ssr:serve:dev npm script. It is broken and will be fixed soon** 

## SSR Environment variable

The NodeJS app requires an environment variable called `VS_SSR_PROXY_TARGET_HOST`, which is the host address of the target site. If the target is the local Hippo site the value should be `http://localhost:8080`. The easiest way to provide this is to add the following line to the `frontend/.env` file:

```sh
VS_SSR_PROXY_TARGET_HOST = http://localhost:8080
```

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

```sh
# Clone the repo
git clone https://username@bitbucket.visitscotland.com/scm/vscom/design-system.git

# Navigate to frontend dir
cd frontend

# Install the package
yarn install
```

Start up the development environment (a hot-reloading design system instance)

```sh
# Create a `.env` file containing the necessary environment variables (see below)
touch .env

# Build and run the design system
yarn system
```

The design system instance will be running at [localhost:6060](http://localhost:6060) and is independent of the parent Hippo instance.

## The design system

This package can be used to generate a design system using one of the `system` build scripts. The design system documents the components and styles contained in the `src` folder, as well as arbitrary design information. Additionally, the design system provides a contained development environment that hot-reloads when source files are changed.

To generate the design system using either of the `system` build scriots, the relevent environment variables must be set either in a `.env` file or made available as system variables on the system **building** the site.

```sh
# Generates and runs the local instance of the deisgn system at localhost:6060
yarn system

# Generates assets encoding the design system for hosting on a production server
yarn system:build
``` 

### Design system Config

The layout and content of the design system is generated from a combination of the source files and configuration in `config/docs.config.js`. Details of the available config settings can be found in the [vue-styleguidist documentation](https://vue-styleguidist.github.io/Configuration.htm).

There is one custom config key `remoteProfiles`, which can be used to specify config for including content from remote sources in the generated design system. Details for the remote configuration [can be found below](#remote-config).

### <a name="remote-config"></a> Configuring the remote build

When included, the `remoteProfiles` key in `config/docs.config.js` specifies config profiles for merging content from a remote API into the static local config in that file.

These builds require valid configuration, as follows:

- The `remoteProfiles` key in `config/docs.config.js` is a map of objects - each key being the profile name and the value being an object with the profile's config. The project currently includes profiles for Hippo and Contentful, which are specified in `config/remote.docs.config.js`. See `config/remote.docs.config.js` for details on how to properly configure a remote profile.
- Set the `VS_DS_REMOTE_CONFIG_URL` and any other environment variables (e.g. `VS_DS_REMOTE_CONFIG_HIPPO_PROJECT_NAME`) by specifying them in a `.env` file in the package root, or some other way.

Common environment variables for remote config:
`VS_DS_REMOTE_CONFIG_URL` - sets the URL for the API call
`VS_DS_REMOTE_CONFIG_STRICT_SSL` - used to set the `strictSSL` value passed to `request-promise-native`, which will be true unless this variable has the value "false"
`VS_DS_REMOTE_PROFILE` - used to specify which named profile to run

Environment variables for Hippo and Contentful config:
`VS_DS_REMOTE_CONFIG_HIPPO_SECTIONS_FIELD_TITLE`
`VS_DS_REMOTE_CONFIG_HIPPO_SECTIONS_CONTENT_FIELD_TITLE`
`VS_DS_REMOTE_CONFIG_HIPPO_PROJECT_NAME`
`VS_DS_REMOTE_CONFIG_CONTENTFUL_TOKEN`

Only the `VS_DS_REMOTE_CONFIG_URL` variable is truly needed. However, each profile generally requires other variables to be set.

By default, the build selects the first profile defined on the `remoteProfiles` key. A different profile can be selected in one of the following ways:

1. Set the `VS_DS_REMOTE_PROFILE` environment variable to the desired profile name
2. Pass the profile name as the --remote-profile arg when running the package.json script, e.g `yarn system --remote-profile contentful`

### Developing in the design system instance

Run `yarn system`

This build script generates and serves the design system at [localhost:6060](http://localhost:6060).

Modifications to source files will trigger a rebuild and refresh the browser. **Tokens will not be recompiled automatically on file change but tokens can be recompiled manually by running:**

`yarn theo`

### Building static design system assets for production

The following script generates build artefacts encoding the design system site into `dist/system`, using the config in `config/docs.config.js`:

`yarn system:build`

The artefacts can be served as a static site using a standard http server.

## Using the components and styles in downstream projects

The `library` and `library:dev` build scripts compile artefacts consisting of all the js, css and other assets required for inclusion of any of the Vue components, Vuex stores or core styles from the `src` folder in a downstream project.

The assets are built into the `dist/library` folder. A `manifest.json` file is also generated, which lists which of the generated assets must be included to include each Vue component or Vue store in another project.

To include a component in another project:

- Reference all the relevent assets for the component as listed in the manifest.json.
- Register the component using the [`Vue.component()` function](https://vuejs.org/v2/guide/components-registration.html) or locally in another component/app.

The build also creates a core module (consisting of core.js and core.css artefacts) that contains any styles that need to applied globally to all components, such as the Bootstrap reboot styles. To add JS to this module, include it in `build/core.system-components.js`, which is the entry file for the core module. This entry file imports `src/styles/core.styles.scss`, so add any global Sass to that file.

NOTE: An example webpack consuming package can be found in the `hippo/frontend` folder.



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
