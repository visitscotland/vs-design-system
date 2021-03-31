<template>
    <div
        class="vs-address"
        data-test="vs-address"
    >
        <VsContainer>
            <VsRow>
                <VsCol cols="2">
                    <VsIcon name="map-marker" />
                </VsCol>
                <VsCol
                    class="vs-address__address-box"
                    cols="10"
                >
                    <address>
                        <slot />
                    </address>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>
<script>
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/layout';
import VsIcon from '@components/elements/icon';

/**
 * Address
 *
 * @displayName Address
 */
export default {
    name: 'VsAddress',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsCol,
        VsRow,
        VsContainer,
        VsIcon,
    },
};
</script>

<style lang="scss">
    .vs-address {
        padding: $spacer-2;
    }

    .vs-address span {
        display: block;
    }

    .vs-address__address-box {
        line-height: $line-height-s;
    }
</style>

<docs>
  ```js

    <h3>Basic Address</h3>
    <VsAddress>
        <span>Edinburgh Castle,</span>
        <span>Castle Hill,</span>
        <span>Edinburgh,</span>
        <span>City of Edinburgh,</span>
        <span>EH1 2NG</span>
    </VsAddress>

  ```
</docs>
