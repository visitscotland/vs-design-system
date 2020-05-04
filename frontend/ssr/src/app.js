
import Vue from 'vue';

import VsImageWithCaption from '@components/patterns/image-with-caption/ImageWithCaption'
import VsImg from '@components/elements/img/Img'
import VsSvg from '@components/elements/svg/Svg'
import VsLink from '@components/elements/link/Link'
import VsButtonWithIcon from '@components/elements/button/ButtonWithIcon'
import {
    VsCol,
    VsRow,
    VsContainer,
} from '@components/elements/layout'
import VsHeading from '@components/elements/heading/Heading'
import VsIcon from '@components/elements/icon/Icon'
import {
    VsDescriptionList,
    VsDescriptionListTerm,
    VsDescriptionListDetail,
} from '@components/elements/description-list'
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper'
import VsTooltip from '@components/elements/tooltip/Tooltip'
import VsBreadcrumb from '@components/elements/breadcrumb/Breadcrumb'
import VsBreadcrumbItem from '@components/elements/breadcrumb/BreadcrumbItem'
import VsSocialShare from '@components/patterns/social-share/SocialShare'
import VsListicleItem from '@components/patterns/listicle/ListicleItem'
import VsIconList from '@components/patterns/icon-list/IconList'
import VsIconListItem from '@components/patterns/icon-list/IconListItem'
import VsSocialCreditLink from '@components/patterns/social-credit-link/SocialCreditLink'
import VsPageIntro from '@components/patterns/page-intro/PageIntro'
import VsHero from '@components/patterns/hero/Hero'
import { 
    VsSummaryBoxList,
    VsSummaryBoxListItem,
    VsSummaryBoxLabel,
    VsSummaryBoxDisplay,
    VsSummaryBoxIconWithLabel,

} from '@components/patterns/summary-box'
import { 
    VsItinerary,
    // VsItineraryMap,
    VsItineraryDay,
    VsItineraryStop,
    VsItineraryTips,
    VsItineraryBorderOverlapWrapper,
} from '@components/patterns/itineraries'

export const createApp = (context) =>  {
    const defaultOptions = {
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
            VsButtonWithIcon,
            VsIcon,
            VsDescriptionList,
            VsDescriptionListTerm,
            VsDescriptionListDetail,
            VsTooltip,
            VsPageIntro,
            VsHero,
            VsSummaryBoxList,
            VsSummaryBoxListItem,
            VsSummaryBoxLabel,
            VsSummaryBoxDisplay,
            VsSummaryBoxIconWithLabel,
            VsItinerary,
            // VsItineraryMap,
            VsItineraryDay,
            VsItineraryStop,
            VsItineraryTips,
            VsItineraryBorderOverlapWrapper,
        },
        comments: true,
    }

    const vueOptions = Object.assign({}, defaultOptions, context.vueOptions)

    const app = new Vue(vueOptions)

    return app
};