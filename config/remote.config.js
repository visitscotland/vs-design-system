const transformConfig = require("../build/contentful-response-transform")

module.exports = {
  // enter the correct URI and access_token for Contentful instance
  uriBase: "https://cdn.contentful.com/spaces/xf5qyv95yk3c/environments/master/entries",
  uriParams: {
    access_token: "73c1a3252bd526262639b627c11271fc6d561848991ef76ce37453431eeda6f6",
    content_type: "instance",
    include: 5,
  },
  transformResponse: transformConfig.transformRawResponse,
}
