<template>
    <dt class="vs-icon-description-list__term">
        <slot />
    </dt>
</template>

<script>
/**
 * An icon description list term is a child component used within icon description list components.
 * The term is simply passed in as a slot
 */

export default {
    name: "VsIconDescriptionListTerm",
    status: "prototype",
    release: "0.0.1",
}
</script>

<style lang="scss" scoped>
.vs-icon-description-list__term {
    font-size: $h4-font-size;
    font-weight: $lead-font-weight;
    margin-bottom: $spacer-5;
}
</style>
