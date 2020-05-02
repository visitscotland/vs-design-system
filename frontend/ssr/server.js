const express = require('express');
const path = require('path');
const fs = require('fs');
const vueServerRenderer = require('vue-server-renderer');
const vueTemplateCompiler = require('vue-template-compiler');

const setupDevServer = require('./config/setup-dev-server');
const { html: beautifyHtml } = require('js-beautify')

const serverBundle = require('../dist/ssr/vue-ssr-server-bundle.json');
const clientManifest = require('../dist/ssr/vue-ssr-client-manifest.json');
const testHTMLEntry = fs.readFileSync(path.resolve(__dirname, './src/dummy-ssr-template.html'), 'utf-8');

const port = 3000;
const app = express();

const createRenderer = (bundle) => {
    return vueServerRenderer.createBundleRenderer(bundle, {
        runInNewContext: true,
        template: fs.readFileSync(path.resolve(__dirname, 'index.html'), 'utf-8'),
        inject: true,
        clientManifest
    });
}

// Render the HTML page using Vue SSR on the provided app template
const renderPage = async (template) => {

    const compileOptions = {
        outputSourceRange: true
    }

    const compiled = vueTemplateCompiler.compileToFunctions(template, compileOptions)

    const context = {
        vueOptions: {
            render: compiled.render,
        },
        template,
    }

    return appendXTemplate(await renderer.renderToString(context), template);
}

// adds x-template containing the whole app template into the subject HTML page
const appendXTemplate = (subject, template) => {
    const search = /<script src="\/public\/components\/main-client.js" defer><\/script>/

    const replacement = [
        '<script type="text/x-template" id="app-template">',
            // the data-vue-app-init is removed otherwise the app will be instantiated twice
            template.replace('data-vue-app-init', ''),
        '</script>',
        '<script src="/public/components/main-client.js" defer></script>'
    ].join('\n')

    return subject.replace(search, replacement);
}

// you may want to serve static files with nginx or CDN in production
app.use('/public',  express.static(path.resolve(__dirname, '../dist/ssr/client')));

if (process.env.NODE_ENV === 'development') {
    setupDevServer(app, (serverBundle) => {
        renderer = createRenderer(serverBundle);
    });
} else {
    renderer = createRenderer(serverBundle);
}
    
app.get(/^\/(about)?\/?$/, async (req, res) => {
    // HTML is formatted before rendering to ensure successful hydration
    const formattedHTML = beautifyHtml(testHTMLEntry, { indent_with_tabs: true })

    let html

    try {
        html = await renderPage(formattedHTML)
    } catch (error) {
        if (error.code === 404) {
            return res.status(404).send('404 | Page Not Found');
        }
        console.log(error)
    }

    res.end(html);
});

app.listen(port, () => console.log(`Listening on: ${port}`));