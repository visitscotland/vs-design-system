<template>
    <div
        class="vs-address"
        data-test="vs-address"
    >
        <VsIcon
            name="map-marker"
            class="vs-address__map-marker mr-2"
        />
        <address class="vs-address__address-box">
            <slot />
        </address>
    </div>
</template>
<script>

/**
 * Address
 *
 * @displayName Address
 */
export default {
    name: 'VsAddress',
    status: 'prototype',
    release: '0.1.0',
};
</script>

<style lang="scss">
    .vs-address {
        padding: $spacer-2 $spacer-0;
    }

    .vs-address span {
        display: block;
    }

    .vs-address__map-marker {
        display: inline-block;
        vertical-align: top;
    }

    .vs-address__address-box {
        line-height: $line-height-s;
        display: inline-block;
        vertical-align: top;
    }
</style>
