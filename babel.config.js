module.exports = {
    presets: ["@babel/preset-env"],
    plugins: [
        "@babel/plugin-syntax-dynamic-import",
        "@babel/plugin-proposal-class-properties",
        "@babel/plugin-proposal-object-rest-spread",
        "@babel/plugin-transform-runtime",
    ],
    exclude: [],
    ignore: [/node_modules\/(?!bootstrap-vue)/],
    env: {
        test: {
            presets: ["@babel/preset-env"],
            plugins: [
                "@babel/plugin-syntax-dynamic-import",
                "@babel/plugin-proposal-class-properties",
                "@babel/plugin-proposal-object-rest-spread",
                "macros",
            ],
        },
    },
}
