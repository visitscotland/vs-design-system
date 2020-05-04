const path = require('path');

const express = require('express');
const { some } = require('lodash');

const { renderPage, initRenderer } = require('./ssr');
const { getPage } = require('./proxy');

const port = 3000;
const app = express();

const skipPathFragments = [
    '/public',
    '/site/autoreload',
    '/favicon.ico',
]

const skipSsr = (path) => {
    return some(skipPathFragments, (fragment) => {
        return path.indexOf(fragment) !== -1
    })
}

// you may want to serve static files with nginx or CDN in production
app.use('/public',  express.static(path.resolve(__dirname, '../../dist/ssr/client')));

app.use(async (req, res) => {
    let renderedPage;

    if(skipSsr(req.path)) {
        res.status(400).send();
        return;
    }

    const cmsRenderedHtml = await getPage(req.path);

    try {
        renderedPage = await renderPage(cmsRenderedHtml);
    } catch (error) {
        if (error.code === 404) {
            return res.status(404).send('404 | Page Not Found');
        }
        console.log(error);
    }

    // res.status(200).send(renderedPage);
    res.status(200).send(renderedPage || cmsRenderedHtml);
});

app.listen(port, () => console.log(`Listening on: ${port}`));

initRenderer(app);
