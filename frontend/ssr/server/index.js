const express = require("express");
const { some } = require("lodash");
const getPort = require("get-port");

const { renderPage, initRenderer } = require("./ssr");
const getProxyServer = require("./proxy");

if (!process.env.VS_SSR_PROXY_TARGET_HOST) {
    require("dotenv").config();
}

// Port range for the Node app
const minPort = 8082;
const maxPort = 8082;

const app = express();

const noSSRPaths = [
    "/site/webfiles",
    "/site/autoreload",
    "/favicon.ico",
];

const doNotPerformSsrOn = (uriPath) => some(
    noSSRPaths,
    (fragment) => uriPath.indexOf(fragment) !== -1,
);

const postProxyProcess = async (path, rawResponse) => {

    if (doNotPerformSsrOn(path)) {
        console.log(`Proxying request to ${path}`);
    } else {
        console.log(`Attempting SSR on request to ${path}`)

        try {
            const renderedResponse = renderPage(rawResponse);

            console.log(`Completed SSR on request to ${path}`)

            return renderedResponse;
        } catch (error) {
            console.error(`Failed SSR on request to ${path}, error to follow`)
            console.error(error);
        }
    }

    return rawResponse;
}

const proxy = getProxyServer(process.env.VS_SSR_PROXY_TARGET_HOST, postProxyProcess)


app.use((req, res) => {
    proxy.web(req, res);
});

(async () => {
    const port = await getPort({
        port: getPort.makeRange(minPort, maxPort),
    });

    app.listen(port, () => console.log(`Listening on: ${port}`));
})();


initRenderer(app);
