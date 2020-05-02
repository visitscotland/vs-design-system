const path = require('path');
const merge = require('webpack-merge');
const base = require('./webpack.system-components.conf');

delete base.entry

module.exports = merge(base, {
    entry: {
        core: "./build/core.system-components.js",
        VsImageWithCaption: './src/components/patterns/image-with-caption/ImageWithCaption.vue',
        VsImageLocationMap: './src/components/patterns/image-location-map/ImageLocationMap.vue',
        VsButton: './src/components/elements/button/Button.vue',
        VsCol: './src/components/elements/layout/Col.vue',
        VsRow: './src/components/elements/layout/Row.vue',
        VsSvg: './src/components/elements/svg/Svg.vue',
        VsImg: './src/components/elements/img/Img.vue',
        VsIcon: './src/components/elements/icon/Icon.vue',
    },
    output: {
        path: path.resolve(__dirname, "../dist/ssr"),
    },
});
