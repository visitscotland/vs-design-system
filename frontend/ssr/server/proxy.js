const http = require("http");
const url = require("url");

if (!process.env.VS_SSR_PROXY_TARGET_HOST) {
    require("dotenv").config();
}

const makeUrl = (subPath) => {
    if (!process.env.VS_SSR_PROXY_TARGET_HOST) {
        throw new Error("Target host URL missing");
    }

    return url.resolve(process.env.VS_SSR_PROXY_TARGET_HOST, subPath);
}

const getPage = async (uriPath) => new Promise((resolve, reject) => {
    const targetUrl = makeUrl(uriPath);

    console.log(`Proxying request to ${targetUrl}`);

    http.get(targetUrl, (resp) => {
        let data = "";

        resp.on("data", (chunk) => {
            data += chunk;
        });

        resp.on("end", () => {
            resolve(data);
        });
    }).on("error", (err) => {
        reject(err);
    });
});

module.exports = {
    getPage,
}
