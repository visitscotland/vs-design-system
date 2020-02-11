const { merge } = require("lodash")

/**
 * Define connection details and response transformations for remote config here
 *
 * Export object, each key defines a named remote config profile.
 *
 * See Contentful example below for details:
 */

// module.exports = {
//  contentful: {
//     /**
//      * URI base is required and is the URL to poll for config, without query params
//      */
//     uriBase: "https://cdn.contentful.com/spaces/xxxxxxxxxxxx/environments/master/entries",
//
//     /**
//      * Map of query params to add to the request
//      */
//     uriParams: {
//       access_token: "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
//       content_type: "instance",
//       include: 5,
//     },
//
//     /**
//      * Options object passed to request-promise-native
//      */
//     requestOptions: {
//        strictSSL: true,
//        transform: require("../build/contentful-response-transform")
//     }
//   }
// }

const commonConfig = {
  uriBase: process.env.VS_DS_REMOTE_CONFIG_URL,
  requestOptions: {
    strictSSL: process.env.VS_DS_REMOTE_CONFIG_STRICT_SSL !== "false",
  },
}

const hippo = merge({}, commonConfig, {
  requestOptions: {
    transform: require("../build/remote-response-transform-hippo").transformRawResponse,
  },
})

const contentful = merge({}, commonConfig, {
  uriParams: {
    access_token: process.env.VS_DS_REMOTE_CONFIG_CONTENTFUL_TOKEN,
    content_type: "instance",
    include: 5,
  },
  requestOptions: {
    transform: require("../build/remote-response-transform-contentful").transformRawResponse,
  },
})

module.exports = {
  hippo,
  contentful,
}
