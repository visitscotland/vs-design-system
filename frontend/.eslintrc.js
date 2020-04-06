module.exports = {
	root: true,
	env: {
		// this section will be used to determine which APIs are available to us
		// (i.e are we running in a browser environment or a node.js env)
		node: true,
		browser: true,
		"jest/globals": true,
		"cypress/globals": true 
	},
	parserOptions: {
		parser: "babel-eslint",
		// specifying a module sourcetype prevent eslint from marking import statements as errors
		sourceType: "module"
	},
	extends: [
		// use the recommended rule set for both plain javascript and vue
		"eslint:recommended",
		"airbnb-base",
		"plugin:vue/recommended",
		"plugin:vue/strongly-recommended",
		"plugin:vue-a11y/recommended",
		"plugin:jest/recommended",
		"plugin:cypress/recommended"
	],
	"plugins": [
		"vue-a11y",
		"jest",
		"cypress"
	],
	rules: {
		"indent": [
			"warn",
			4
		],
		"vue/no-unused-vars": "error",
		"vue/order-in-components": "error",
		"vue/this-in-template": "error",
		"vue/component-name-in-template-casing": "error",
		"vue/script-indent": [
			"error",
			4
		],
		"vue/html-indent": [
			"error",
			4
		],
		"quotes": [
			"error",
			"double"
		],
		"no-extra-semi": "off",
		"semi": [
			"error",
			"never"
		],
		"semi-style": [
			"error",
			"last"
		],
	},
	"overrides": [
		// Disable no-console rule for build scripts
		{
			"files": [ 
				"src/system.js",
				"src/utils/core.system-components.js",
				"src/utils/entry.system-components.js",
				"{build,config,test}/**/*",
			],
			"rules": {
				"no-console": 0
			}
		}
	]
};
