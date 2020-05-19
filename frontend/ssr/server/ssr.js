const vueServerRenderer = require("vue-server-renderer")
const vueTemplateCompiler = require("vue-template-compiler")

const cheerio = require("cheerio")
const { html: beautifyHtml } = require("js-beautify")

const setupDevServer = require("./setup-dev-server")

const serverBundle = require("../../dist/ssr/server/vue-ssr-server-bundle.json")
const clientManifest = require("../../dist/ssr/client/vue-ssr-client-manifest.json")

const transformHtml = require("./transform-html")

// const componentMap = require("../../build/entry.system-components.js")

// const testHTMLEntry = fs.readFileSync(
//    path.resolve(__dirname, '../src/dummy-ssr-template.html'), 'utf-8');

const appAttributeName = "data-vue-app-init"
const templatePlaceholderAttrName = "vue-ssr-outlet"
const templatePlaceholderHtml = `<span ${templatePlaceholderAttrName}></span>`
const xTemplateId = "app-template"

// const sourceImports = require("../src/generate-component-library-map")(componentMap)
// console.log(sourceImports)
let $template
let renderer

const formatHtml = (subjectHtml) => {
    return beautifyHtml(subjectHtml, {
        indent_with_tabs: true,
        preserve_newlines: false,
    })
}

const completeSsrTemplate = (appHtml/* , context */) => {
    // const headAssetInject = `
    //     ${context.renderResourceHints() }
    //     ${context.renderStyles() }
    // `;
    // const bodyAssetInject = `
    //     ${context.renderScripts()}
    // `;

    // $template('head').append(headAssetInject);
    // $template('body').append(bodyAssetInject);

    $template(`[${templatePlaceholderAttrName}]`).replaceWith(appHtml)

    const pageHtml = transformHtml($template.html())

    // return pageHtml;

    return formatHtml(pageHtml)
}

const createRenderer = (bundle) => vueServerRenderer.createBundleRenderer(bundle, {
    runInNewContext: true,
    template: completeSsrTemplate,
    inject: true,
    clientManifest,
});

const initRenderer = (app) => {
    if (process.env.NODE_ENV === "development") {
        setupDevServer(app, (bundle) => {
            renderer = createRenderer(bundle)
        })
    } else {
        renderer = createRenderer(serverBundle)
    }
}

// inserts pre-rendered app template into an x-template
const makeXTemplate = (template) => {
    // the data-vue-app-init must be removed otherwise the app will be instantiated twice
    return `
        <script type="text/x-template" id="${xTemplateId}">${template.replace(" data-vue-app-init", "")}</script>
    `
}

const prepSsrTemplate = ($page, formattedAppHtml) => {
    const xTemplateHtml = makeXTemplate(formattedAppHtml)

    $page(`[${templatePlaceholderAttrName}]`).after(xTemplateHtml)

    $template = $page
}

// Render the HTML page using Vue SSR on the provided app template
const renderApp = async (template) => {

    const compileOptions = {
        outputSourceRange: true,
    }

    const compiled = vueTemplateCompiler.compileToFunctions(template, compileOptions)

    const context = {
        vueOptions: {
            render: compiled.render,
            staticRenderFns: compiled.staticRenderFns,
        },
        template,
    }

    return renderer.renderToString(context)
}

const parsePageParts = (pageHtml) => {
    const $page = cheerio.load(pageHtml, {
        script: true,
        style: true,
        pre: true,
        comment: true,
    })

    const $appNode = $page(`[${appAttributeName}]`).replaceWith(templatePlaceholderHtml)

    if (!$appNode) {
        throw "App element missing"
    }

    return {
        $page,
        $appNode,
    }
}

const renderPage = async (pageHtml) => {
    const { $appNode, $page } = parsePageParts(pageHtml)

    // HTML is formatted before rendering to ensure successful hydration
    const formattedAppHtml = formatHtml(cheerio.html($appNode))

    prepSsrTemplate($page, formattedAppHtml)

    return renderApp(formattedAppHtml)
}

module.exports = {
    renderPage,
    initRenderer,
}