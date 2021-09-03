<template>
    <div
        class="vs-canned-search-badges"
    >
        <div
            v-if="badgeOne"
            class="vs-canned-search-badges__badge vs-canned-search-badges__badge--teal
            vs-canned-search-badges__badge--tr"
        >
            {{ badgeOne }}
        </div>
        <div
            v-if="badgeTwo"
            class="vs-canned-search-badges__badge vs-canned-search-badges__badge--pink
            vs-canned-search-badges__badge--tr2"
        >
            {{ badgeTwo }}
        </div>
        <div
            v-if="badgeThree"
            class="vs-canned-search-badges__badge vs-canned-search-badges__badge--light-pink
            vs-canned-search-badges__badge--br"
        >
            {{ badgeThree }}
        </div>
    </div>
</template>

<script>

/**
* Badges to show information in the stretched link component
*
* @displayName Canned Search Badges
*/

export default {
    name: 'VsCannedSearchBadges',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
        * Content for the top right, teal badge
        */
        badgeOne: {
            type: String,
            default: '',
        },
        /**
        * Content for the middle right, pink badge
        */
        badgeTwo: {
            type: String,
            default: '',
        },
        /**
        * Content for the bottom right, light pink badge
        */
        badgeThree: {
            type: String,
            default: '',
        },
    },
};

</script>

<style lang="scss">
    .vs-canned-search-badges {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;

        .vs-canned-search-badges__badge {
            position: absolute;
            padding: $spacer-0 $spacer-2;
            text-transform: uppercase;
            letter-spacing: $spacing_l;

            &--teal {
                color: $color-white;
                background-color: $color-secondary-teal-shade-2;
            }

            &--pink {
                color: $color-white;
                background-color: $color_pink;
            }

            &--light-pink {
                background-color: $color_pink_tint_6;
                color: $color_pink;
                font-weight: $font-weight-bold;
            }

            &--tr {
                top: $spacer-2;
                right: $spacer-2;
            }

            &--tr2 {
                top: 2.5em;
                right: $spacer-2;
            }

            &--br {
                bottom: $spacer-2;
                right: $spacer-2;
                padding: $spacer-0 $spacer-4;
            }
        }
    }

</style>

<docs>
```jsx
    <div
        style="position: relative; height: 10em; width: 10em;"
    >
        <VsCannedSearchBadges
            badgeOne="one"
            badgeTwo="two"
            badgeThree="three"
        />
    </div>
```
</docs>
