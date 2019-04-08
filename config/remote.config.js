/**
 * Define connection details and response transformations for remote config here
 *
 * See Contentful example below for details:
 */

// const transformConfig = require("../build/contentful-response-transform")

// module.exports = {
//   /**
//    * URI base is required and is the URL to poll for config, without query params
//    */
//   uriBase: "https://cdn.contentful.com/spaces/xxxxxxxxxxxx/environments/master/entries",
//   /**
//    * Map of query params to add to the request
//    */
//   uriParams: {
//     access_token: "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
//     content_type: "instance",
//     include: 5,
//   },
//   /**
//    * Function(s) to process the response returned by the initial request. Can take
//    * a number of forms, as documented below. Make sure to return the transformed response.
//    */
//
//   // Option 1: a single function, which is called with the apiResponse as its only argument
//
//   transformResponse: transformConfig.transformRawResponse,
//
//   // Options 2: an object consisting of func, and optionally args keys. The func key should have the
//   // transforming function as its value and the args key an array of args that are supplied to it along with the
//   // response (which is always the first arg)
//
//   transformResponse: {
//     func: transformConfig.transformRawResponse,
//     args: [
//       "arg 1",
//       { second: "arg" }
//     ]
//   }
//
//   // Option 3: an array of either or both of the above to options
//
//   transformResponse: [
//     {
//       func: transformConfig.transformRawResponse,
//       args: [
//         "arg 1",
//         { second: "arg" }
//       ]
//     },
//     transformConfig.transformRawResponse
//   ]
// }

const transformConfig = require("../build/remote-response-transform")

const hippoTransform = {
  projectName: "myproject",
}

module.exports = {
  // define the URI and any required params for the remote api
  uriBase: "http://localhost:8080/site/api/documents/1a0752b4-fda4-4289-9fff-69248c8de2ff",
  transformResponse: {
    func: transformConfig.transformRawResponse,
    args: [hippoTransform],
  },
}
