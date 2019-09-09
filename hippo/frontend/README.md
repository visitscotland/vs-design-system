This package is an example webpack build that consumes the component library assets from the VisitScotland.com design system and uses them in a Hippo CMS site.

## Setup

```
yarn install
```

## Use

### Updating assets and templates

```
yarn build
```

This script does 2 things:

1. Runs the `build:system:components` script in the root package
2. Runs this package's webpack build, which:
   - Copies all built assets from the `dist/system-components` folder into the parent Hippo app's webfiles folder (at `/repository-data/webfiles/src/main/resources/site/design-system`) for inclusion in the Hippo site.
   - Copies `vue-app-init.ftl` to Freemarker template location (at `/repository-data/webfiles/src/main/resources/site/freemarker/include/vs-dotcom-ds`).
   - Generates Freemarker templates for all Vue components and VueX stores listed in the `manifest.json` file to the `components` and `stores` subfolders, respectively, of the Freemarker template location.

You can skip step 1 by running:

```
yarn build:skip
```

This will use the artefacts from a previous `system-components` build of the root package. If you haven't yet done a build, it won't work due to the absence of the entry and source files.

### Including components in the Hippo site

1. Include the Vue freemarker template for the component you want to include

```html
<#include "../include/vs-dotcom-ds/container.ftl">
```

2. Add `data-vue-app-init` attribute on a parent container to ensure the Vue app is initialised (see base-layout.ftl)

3. Reference the Vue component in the HTML markup

```html
<vs-container fluid></vs-container>
```
