const transformConfig = require("../build/remote-response-transform")

module.exports = {
  // enter the URI and any required params for the remote api
  uriBase: "http://localhost:8080/site/api/documents/8578d653-71a3-4a29-b2bc-b9dbd9a2d660",
  transformResponse: transformConfig.transformRawResponse,
}

/**
 * Contentful example
 */

// const transformConfig = require("../build/contentful-response-transform")

// module.exports = {
//   uriBase: "https://cdn.contentful.com/spaces/xf5qyv95yk3c/environments/master/entries",
//   uriParams: {
//     access_token: "73c1a3252bd526262639b627c11271fc6d561848991ef76ce37453431eeda6f6",
//     content_type: "instance",
//     include: 5,
//   },
//   transformResponse: transformConfig.transformRawResponse,
// }
