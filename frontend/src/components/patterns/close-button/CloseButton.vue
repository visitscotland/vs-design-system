<template>
    <VsButton
        class="px-3"
        variant="transparent"
        :animate="false"
    >
        <span class="sr-only"><slot /></span>
        <VsIcon
            name="close"
            size="xs"
            variant="secondary"
        />
    </VsButton>
</template>

<script>
export default {
    name: "VsCloseButton",
}
</script>

<style lang="scss" scoped></style>
