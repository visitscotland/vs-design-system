const http = require("http");
const path = require("path");

if (!process.env.VS_SSR_PROXY_TARGET_HOST) {
    require("dotenv").config();
}

const makeUrl = (subPath) => {
    if (!process.env.VS_SSR_PROXY_TARGET_HOST) {
        throw new Error("Target host URL missing");
    }

    return path.join(process.env.VS_SSR_PROXY_TARGET_HOST, subPath);
}

const getPage = async (path) => {
    return new Promise((resolve, reject) => {
        const url = makeUrl(path);

        console.log(`Proxying request to ${url}`);

        http.get(url, (resp) => {
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
    })
}

module.exports = {
    getPage,
}
