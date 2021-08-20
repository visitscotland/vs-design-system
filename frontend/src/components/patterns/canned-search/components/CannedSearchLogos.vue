<template>
    <div class="vs-canned-search-logos">
        <VsTooltip
            :title="goodToGoLogo"
            v-if="goodToGoLogo"
            data-test="vs-canned-search-logos__good-to-go"
        >
            <VsImg
                src="https://www.visitscotland.com/cms-images/logos/goodToGo.png"
                :alt="goodToGoLogo"
                class="vs-canned-search-logos__logo"
            />
        </VsTooltip>
        <VsTooltip
            :title="safeTravelsLogo"
            v-if="safeTravelsLogo"
            data-test="vs-canned-search-logos__safe-travels"
        >
            <VsImg
                src="https://www.visitscotland.com/cms-images/logos/WTTC-SafeTravels.png"
                :alt="safeTravelsLogo"
                class="vs-canned-search-logos__logo"
            />
        </VsTooltip>
        <VsTooltip
            v-for="award in awards"
            :title="award.name"
            :key="award.id"
        >
            <VsImg
                :src="award.image"
                :alt="award.name"
                class="vs-canned-search-logos__logo"
                data-test="vs-canned-search-logos__award-logo"
            />
        </VsTooltip>
    </div>
</template>

<script>
import VsTooltip from '@components/elements/tooltip/Tooltip';
import VsImg from '@components/elements/img/Img';

/**
* Component that displays a set of badges and awards for certain kinds of product card
* within canned search
*
* @displayName Canned Search Logos
*/

export default {
    name: 'VsCannedSearchLogos',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsTooltip,
        VsImg,
    },
    props: {
        /**
        * If set, the good to go logo appears. The text set in this property will appear as
        * the tooltip of that logo
        */
        goodToGoLogo: {
            type: String,
            default: '',
        },
        /**
        * If set, the safe travels logo appears. The text set in this property will appear as
        * the tooltip of that logo
        */
        safeTravelsLogo: {
            type: String,
            default: '',
        },
        /**
        * A set of award badges to display, each should contain a unique `id` (String), a `name`
        * (String) that appears as a tooltip and a url to an `image` (String)
        */
        awards: {
            type: Array,
            default: null,
        },
    },
};

</script>

<style lang="scss">
    .vs-canned-search-logos {
        margin-top: $spacer-9;
        margin-bottom: $spacer-4;
        min-height: $spacer-9;

        .vs-tooltip {
            z-index: 1;
            cursor: pointer;
        }
    }

    .vs-canned-search-logos__logo {
        max-height: $spacer-9;

        &:not(:last-child) {
            padding-right: $spacer-2;
        }
    }
</style>

<docs>
```jsx
    <VsCannedSearchLogos
        goodToGoLogo="Good to Go"
        safeTravelsLogo="Safe Travels"
        :awards="[
            {
                'id':'qatasteourbest',
                'name':'Taste Our Best',
                'image':'http://visitscotlandpreview.nmdemo.net/wsimgs/awards/TOB-updated-white-back_286148508_714060445.jpg'
            },
            {
                'id': 'qag2s',
                'name': 'Green Tourism Silver',
                'image': 'http://visitscotlandpreview.nmdemo.net/wsimgs/awards/GT_silver_200x200_275519812.png',
                'type': 'GREEN_TOURISM'
            }
        ]"
    />
```
</docs>
