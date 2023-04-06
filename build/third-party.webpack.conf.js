const path = require('path');
const glob = require('glob');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

// Hard code this to production but can be adapted to accept args to change env.
const mode = 'production';

module.exports = {
    mode,
    output: {
        path: path.resolve(__dirname, '../dist/ssr/client/styles/third-party'),
        filename: '[name]',
    },

    resolve: {
        extensions: ['.css', '.scss'],
        alias: {
            // Provides ability to include node_modules with ~
            '~': path.resolve(process.cwd(), 'src'),
        },
    },

    entry: glob.sync('./src/styles/third-party/**.scss').reduce((obj, el) => {
        /* eslint-disable-next-line */
        obj[path.parse(el).name] = el;
        return obj;
    }, {

    }),

    module: {
        rules: [
            {
                test: /\.scss$/,
                use: [
                    // Extract and save the final CSS.
                    MiniCssExtractPlugin.loader,
                    // Load the CSS, set url = false to prevent following urls to fonts and images.
                    {
                        loader: 'css-loader',
                        options: {
                            url: false,
                            importLoaders: 1,
                        },
                    },
                    // Add browser prefixes and minify CSS.
                    {
                        loader: 'postcss-loader',
                    },
                    // Load the SCSS/SASS
                    {
                        loader: 'sass-loader',
                    },
                ],
            },
        ],
    },
    plugins: [
        // Define the filename pattern for CSS.
        new MiniCssExtractPlugin({
            filename: '[name].css',
            chunkFilename: '[id].css',
        }),
    ],
};
