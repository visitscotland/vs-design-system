<template>
    <Component
        :is="tag"
        @click.native.prevent="toggleContent"
        ref="self"
        :aria-expanded="contentIsVisible"
        :is-on="contentIsVisible"
        v-bind="$attrs"
        class="vs-drawer-toggle"
    >
        <slot />
    </Component>
</template>

<script>
import VsButton from "@components/elements/button"
import { getters } from "./drawer.store"
import { IS_ACTIVE_CONTENT } from "./drawer.store.getter-types"
import { CLOSE_DRAWER, SHOW_DRAWER_CONTENT } from "./drawer.store.action-types"

export default {
    name: "VsDrawerToggle",
    components: {
        VsButton,
    },
    props: {
        tag: {
            type: String,
            default: "vs-button",
        },
        drawerKey: {
            type: String,
            required: true,
        },
        contentKey: {
            type: String,
            required: true,
        },
    },
    computed: {
        contentIsVisible() {
            return getters.getters[`drawer/${IS_ACTIVE_CONTENT}`](
                this.contentKey,
                this.drawerKey,
            )
        },
    },
    methods: {
        closeDrawer() {
            return getters
                .dispatch(`drawer/${CLOSE_DRAWER}`, {
                    drawerKey: this.drawerKey,
                })
                .then(this.focusSelf)
        },
        showContent() {
            return getters.dispatch(`drawer/${SHOW_DRAWER_CONTENT}`, {
                drawerKey: this.drawerKey,
                contentKey: this.contentKey,
                returnFocusElement: this.$refs.self,
            })
        },
        toggleContent() {
            if (this.contentIsVisible) {
                this.closeDrawer()
            } else {
                this.showContent()
            }
        },
        focusSelf() {
            this.$refs.self.$el.focus()
        },
    },
}
</script>
