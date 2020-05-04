
import Vue from 'vue';

import VsImageWithCaption from '../../src/components/patterns/image-with-caption/ImageWithCaption'
import VsImg from '../../src/components/elements/img/Img'
import VsSvg from '../../src/components/elements/svg/Svg'
import VsLink from '../../src/components/elements/link/Link'
import VsCol from '../../src/components/elements/layout/Col'
import VsRow from '../../src/components/elements/layout/Row'
import VsContainer from '../../src/components/elements/layout/Container'
import VsHeading from '../../src/components/elements/heading/Heading'
import VsRichTextWrapper from '../../src/components/elements/rich-text-wrapper/RichTextWrapper'
import VsBreadcrumb from '../../src/components/elements/breadcrumb/Breadcrumb'
import VsBreadcrumbItem from '../../src/components/elements/breadcrumb/BreadcrumbItem'
import VsSocialShare from '../../src/components/patterns/social-share/SocialShare'
import VsListicleItem from '../../src/components/patterns/listicle/ListicleItem'
import VsIconList from '../../src/components/patterns/icon-list/IconList'
import VsIconListItem from '../../src/components/patterns/icon-list/IconListItem'
import VsSocialCreditLink from '../../src/components/patterns/social-credit-link/SocialCreditLink'

export const createApp = (context) =>  {

    const vueOptions = Object.assign({}, context.vueOptions, {
        components: {
            VsImageWithCaption,
            VsImg,
            VsSvg,
            VsLink,
            VsCol,
            VsRow,
            VsContainer,
            VsHeading,
            VsRichTextWrapper,
            VsBreadcrumb,
            VsBreadcrumbItem,
            VsSocialShare,
            VsListicleItem,
            VsIconList,
            VsIconListItem,
            VsSocialCreditLink,
        },
        comments: true
    });

    const app = new Vue(vueOptions);

    return app
};