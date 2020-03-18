This package is an example webpack build that consumes the component library assets from the VisitScotland.com design system and uses them in a Hippo CMS site.

## Setup

```
yarn install

// OR

yarn
```

This script installs this package's dependencies, as well as the sibling frontend package's dependencies.

## Use

### Building frontend assets and generating freemarker templates

```
yarn build
```

This script executes the `frontend` package's `yarn build:system:components` build, before running the webpack build of this package, which:
   - Copies all assets built by the `frontend`'s components build (contained in the `node_modules/vs-dotcom-ds/dist/` folder) into the parent Hippo app's webfiles folder (at `/repository-data/webfiles/src/main/resources/site/design-system`) for inclusion in the Hippo site.
   - Copies `vue-app-init.ftl` to Hippo Freemarker template directory (at `/repository-data/webfiles/src/main/resources/site/freemarker/include/vs-dotcom-ds`).
   - Generates Freemarker templates for all Vue components and VueX stores listed in the `manifest.json` file to the `components` and `stores` subfolders, respectively, of the Freemarker template directory.

### Using the frontend components in the Hippo site

1. Include the Vue freemarker template for the component you want to include

```html
<#include "../include/vs-dotcom-ds/container.ftl">
```

2. Add `data-vue-app-init` attribute on a parent container to ensure the Vue app is initialised (see base-layout.ftl)

3. Reference the Vue component in the HTML markup

```html
<vs-container fluid></vs-container>
```
