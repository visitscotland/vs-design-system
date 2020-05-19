const path = require("path");
const fs = require("fs");

const express = require("express");
const { some } = require("lodash");
const getPort = require('get-port');

const { renderPage, initRenderer } = require("./ssr");
const { getPage } = require("./proxy");

const minPort = 3000;
const maxPort = 3100;

const app = express();

const skipPathFragments = [
    "/public",
    "/site/autoreload",
    "/favicon.ico",
]

const skipSsr = (uriPath) => {
    return some(skipPathFragments, (fragment) => {
        return uriPath.indexOf(fragment) !== -1
    })
}

// you may want to serve static files with nginx or CDN in production
app.use("/public", express.static(path.resolve(__dirname, "../../dist/ssr/client")));

app.use(async (req, res) => {
    let renderedPage;

    if (skipSsr(req.path)) {
        res.status(400).send();
        return;
    }

    const cmsRenderedHtml = await getPage(req.path);

    try {
        renderedPage = await renderPage(cmsRenderedHtml);
    } catch (error) {
        if (error.code === 404) {
            res.status(404).send("404 | Page Not Found");
            return;
        }
        console.log(error);
    }

    // res.status(200).send(renderedPage);
    res.status(200).send(renderedPage || cmsRenderedHtml);
});

(async () => {
    const port = await getPort({ port: getPort.makeRange(minPort, maxPort) });

    app.listen(port, () => console.log(`Listening on: ${port}`));
})();


initRenderer(app);
