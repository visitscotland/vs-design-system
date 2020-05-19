
if (!process.env.VS_SSR_PROXY_TARGET_HOST) {
    require("dotenv").config()
}

// This prepends the target host address to all script and link href and src values,
// respectively, that begin with "/" - workaround instead of outputting absolute
// URLs from the target site
const correctLocalAssetUrls = (inputHtml) => {

    const search = /(<(?:script|link)\s+(?:[^>]*?\s+)?(?:href|src)=(?:["']))([\.]*\/)/g;

    return inputHtml.replace(search, `$1${process.env.VS_SSR_PROXY_TARGET_HOST}/`);
}

module.exports = correctLocalAssetUrls;
