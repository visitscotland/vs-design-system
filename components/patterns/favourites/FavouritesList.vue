<template>
    <div v-if="favourites.length">
        <span class="vs-favourites-list__header text-uppercase">{{ listHeader }}</span>
        <ul class="list-unstyled">
            <li
                v-for="(item, index) in favourites"
                class="d-flex align-items-center w-100"
                :key="index"
            >
                <a
                    :href="item.href"
                    class="vs-favourites-list__link"
                >{{ item.title }}</a>
                <VsButton
                    class="p-2"
                    :animate="false"
                    variant="transparent"
                    @click.native.prevent="deleteFavourite(item.href)"
                >
                    <span class="sr-only">Remove from favourites</span>
                    <VsIcon
                        name="close"
                        size="xs"
                    />
                </VsButton>
            </li>
        </ul>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import favouritesStore from './favourites.store';

/**
 * TODO: Document usage
 *
 * @displayName Favourites List
 */
export default {
    name: 'VsFavouritesList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        listHeader: {
            type: String,
            default: 'Favourites list',
        },
    },
    data() {
        return {
        };
    },
    computed: {
        favourites() {
            return favouritesStore.getters['favourites/getFavourites'];
        },
        last() {
            return this.favourites.length - 1;
        },
    },
    methods: {
        deleteFavourite(href) {
            favouritesStore.dispatch('favourites/deleteFavourite', href);
            this.$emit('favourite-deleted');
        },
    },
};
</script>

<style lang="scss">
.vs-favourites-list__link {
    font-size: $font-size-8;
}

.vs-favourites-list__header {
    color: $color-gray-shade-2;
    font-size: $font-size-9;
    font-weight: $font-weight-light;
    letter-spacing: $letter-spacing-l;
}
</style>
