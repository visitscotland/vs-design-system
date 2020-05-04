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
        VsLink: './src/components/elements/link/Link',
        VsContainer: './src/components/elements/layout/Container',
        VsHeading: './src/components/elements/heading/Heading',
        VsRichTextWrapper: './src/components/elements/rich-text-wrapper/RichTextWrapper',
        VsBreadcrumb: './src/components/elements/breadcrumb/Breadcrumb',
        VsBreadcrumbItem: './src/components/elements/breadcrumb/BreadcrumbItem',
        VsSocialShare: './src/components/patterns/social-share/SocialShare',
        VsListicleItem: './src/components/patterns/listicle/ListicleItem',
        VsIconList: './src/components/patterns/icon-list/IconList',
        VsIconListItem: './src/components/patterns/icon-list/IconListItem',
        VsSocialCreditLink: './src/components/patterns/social-credit-link/SocialCreditLink',
    },
    output: {
        path: path.resolve(__dirname, "../dist/ssr"),
    },
});
