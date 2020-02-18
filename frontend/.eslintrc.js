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
		"no-console": "off"
	},
};
