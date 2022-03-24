<template>
    <VsButton
        class="vs-favourites-view__button p-0 p-sm-1 position-relative"
        @click.native="openFavourites"
        variant="transparent"
        :animate="false"
        v-if="favouritesCount > 0"
    >
        <span class="sr-only">View favourites</span>
        <span
            class="vs-favourites-view-button-count"
            v-if="favouritesCount > 0"
        >
            <span class="sr-only">Current favourites count:</span> {{ favouritesCount }}
        </span>
        <VsIcon
            v-if="favouritesCount > 0"
            name="favourite-filled"
            size="sm"
            variant="primary"
        />
        <VsIcon
            v-else
            name="favourite"
            size="sm"
            variant="dark"
        />
    </VsButton>
</template>

<script>
import store from '../favourites.store';

/**
 * TODO: Document usage
 *
 * @displayName Favourites View Button
 */
export default {
    name: 'VsListFavouritesViewButton',
    status: 'prototype',
    release: '0.0.1',
<<<<<<< HEAD
    components: {
        VsIcon,
    },
=======
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709
    props: {
    },
    // store,
    computed: {
        favouritesCount() {
            return store.getters['favourites/getFavouritesCount'];
        },
        favourites() {
            return store.getters['favourites/getFavourites'];
        },
    },
};
</script>

<style lang="scss">
.vs-favourites-view-button-count {
<<<<<<< HEAD
    color: #fff;
=======
    color: $color-white;
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709
    display: block;
    font-size: 0.75rem;
    width: 100%;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
}
</style>
<<<<<<< HEAD

<docs>
  ```jsx
    <VsListFavouritesViewButton />
  ```
</docs>
=======
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709
