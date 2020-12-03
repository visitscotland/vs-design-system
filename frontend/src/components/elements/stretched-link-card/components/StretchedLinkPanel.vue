<template>
    <div
        class="stretched-link-panel"
        :class="days ? 'stretched-link-panel--days' : ''"
        :data-test="days ? 'stretched-link-panel--days' : 'stretched-link-panel'"
    >
        <p v-if="days">
            <span class="stretched-link-panel__days">{{ days }}</span> days
        </p>

        <template v-if="!days">
            <VsIcon
                name="transport"
                variant="reverse-white"
                class="stretched-link-panel__icon"
            />
            <p data-test="stretched-link-panel__icon">
                {{ text }}
            </p>
        </template>
    </div>
</template>

<script>
export default {
    name: 'VsStretchedLinkPanel',
    status: 'prototype',
    release: '0.0.1',
    props: {
        days: {
            type: String,
            default: '',
        },
        text: {
            type: String,
            default: '',
        },
    },
};
</script>
