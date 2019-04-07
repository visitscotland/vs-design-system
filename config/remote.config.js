const transformConfig = require("../build/contentful-response-transform")

module.exports = {
  // enter the correct URI and access_token for Contentful instance
  uriBase: "https://cdn.contentful.com/spaces/xxxxxxxxxx/environments/master/entries",
  uriParams: {
    access_token: "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
    content_type: "instance",
    include: 5,
  },
  transformResponse: transformConfig.transformRawResponse,
}
