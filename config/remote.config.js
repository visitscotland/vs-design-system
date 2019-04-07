const transformConfig = require("../build/remote-response-transform")

module.exports = {
  // enter the correct URI and access_token for Contentful instance
  uriBase: "http://localhost:8080/site/api/documents/8578d653-71a3-4a29-b2bc-b9dbd9a2d660",
  transformResponse: transformConfig.transformRawResponse,
}
