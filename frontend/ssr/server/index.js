const express = require("express");
const { some } = require("lodash");
const getPort = require("get-port");
const proxy = require("express-http-proxy");

const { renderPage, initRenderer } = require("./ssr");

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

const postProxyHandler = async (proxyRes, proxyResData, userReq) => {

    if (doNotPerformSsrOn(userReq.path)) {
        console.log(`Proxying request to ${userReq.path}`);
    } else {
        console.log(`Attempting SSR on request to ${userReq.path}`)

        try {
            const renderedResponse = await renderPage(proxyResData.toString("utf8"));

            console.log(`Completed SSR on request to ${userReq.path}`)

            return renderedResponse;
        } catch (error) {
            console.error(`Failed SSR on request to ${userReq.path}, error to follow`)
            console.error(error);
        }
    }

    return proxyResData;
}

app.use(proxy(process.env.VS_SSR_PROXY_TARGET_HOST, {
    userResDecorator: postProxyHandler,
}));


(async () => {
    const port = await getPort({
        port: getPort.makeRange(minPort, maxPort),
    });

    app.listen(port, () => console.log(`Listening on: ${port}`));
})();


initRenderer(app);
