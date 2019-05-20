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
- Set the `REMOTE_CONFIG_CONTENTFUL_SPACE` and `REMOTE_CONFIG_CONTENTFUL_TOKEN` environment variables - by specifying them in a `.env` file in the package root, or manually some other way.
- Run the remote styleguide scripts (`npm run docs:remote`, `npm run styleguide:remote` or `npm run styleguide:remote:build`) instead of the regular scripts.

By default, the build selects the first profile defined in the `config/remote.config.js` export. To select a different profile, alter the npm script to pass the name of the desired profile as the --remote-profile arg to the `build/styleguide` script, e.g:

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

The best way to develop the design system is to run a local development instance of the documentation site, as detailed above. Using the

To see how the local, modified version of the design system behaves in a target local project, carry out the following to link the target project's dependency to the local version, rather than the published version, of the design system assets:

- `cd design-system`
- `yarn link` OR `npm link`
- `cd ../target-project`
- `yarn link vs-dotcom-ds` OR `npm link vs-dotcom-ds`

NOTE: It is vs-dotcom-ds because that is this project's name.

This will create a symlink between the target project folder and the local design-system folder. The target project's vs-dotcom-ds dependency will use the local design-system folder directly.

**It is advised to reference the built artefacts in the `dist/system` or `dist/system-components` folders, in which case the design system assets will need to be rebuilt (using `npm run build:system-components` or `npm run build:system` for changes to source files to be propogated into the target project**.

## Ideas for improvement

- Add ability to have multiple `<docs>` examples output to component documentation and ability to insert named `<docs>` examples into documentation content
- Colour accessibility like Atlassian
- XD/Zeplin integration
- Icons page listing out all available icons as well as icon options & guidelines
