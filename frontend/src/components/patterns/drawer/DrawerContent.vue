<template>
    <Component
        :is="wrappingElement"
        class="position-relative"
        v-show="isVisible"
        @focus="focusOnContent"
        tabindex="-1"
    >
        <VsCol
            order="2"
            v-if="container"
        >
            <div class="d-none d-md-block position-absolute close-button-wrapper">
                <VsCloseButton
                    @click.native="closeDrawer"
                    @keydown.native="checkKeydown($event)"
                    ref="closeButton"
                >
                    Close this drawer
                </VsCloseButton>
            </div>
        </VsCol>

        <Component
            :md="container ? 11 : null"
            :is="container ? 'vs-col' : 'div'"
        >
            <slot />
            <button
                @focus="closeDrawer"
                class="catch-focus__button"
            />
        </Component>
    </Component>
</template>

<script>
import Vue from "vue"
import { isFunction, get } from "lodash"

import VsCloseButton from "@components/patterns/close-button/CloseButton"
import VsRow from "@components/elements/layout/Row"
import VsCol from "@components/elements/layout/Col"
import logger from "@/utils/logger"
import VsDrawer from "./Drawer"

import { getters } from "./drawer.store"
import { IS_ACTIVE_CONTENT } from "./drawer.store.getter-types"
import { CLOSE_DRAWER } from "./drawer.store.action-types"

export default {
    name: "VsDrawerContent",
    components: {
        VsCloseButton,
        VsRow,
        VsCol,
    },
    props: {
        /**
         * unique key for this content - used to discover when to show
         * the content according to the drawer VueX store.
         */
        contentKey: {
            type: String,
            required: true,
        },
        /**
         * Whether or not the close drawer button is shown
         */
        showClose: {
            type: Boolean,
            default: false,
        },
        /**
         * Determines whether and what gains focus when this content
         * becomes visible.
         * `false, "content", "close"`
         */
        openFocus: {
            type: [String, Boolean],
            default: false,
            validator: (value) => value === false || value.match(/(content|close)/),
        },
    },
    data() {
        return {
            parentDrawer: {
            },
        }
    },
    computed: {
        isVisible() {
            return getters.getters[`drawer/${IS_ACTIVE_CONTENT}`](
                this.contentKey,
                this.parentDrawerKey,
            )
        },
        hasDefaultSlot() {
            return isFunction(get(this.$scopedSlots, "default"))
        },
        parentDrawerKey() {
            return get(this.parentDrawer, "$options.propsData.drawerKey")
        },
        container() {
            return get(this.parentDrawer, "$options.propsData.container", false)
        },
        wrappingElement() {
            return this.container ? "vs-row" : "div"
        },
    },
    watch: {
        isVisible(newVal, oldVal) {
            if (!oldVal && newVal) {
                Vue.nextTick(this.onBecomeVisible)
            }
        },
    },
    mounted() {
        this.setParentDrawer()

        if (!this.$parent) {
            logger.error("VsDrawerContent is not inside a parent VsDrawer")
        }
    },
    methods: {
        closeDrawer() {
            getters
                .dispatch(`drawer/${CLOSE_DRAWER}`, {
                    drawerKey: this.parentDrawerKey,
                })
                .then((returnFocusElement) => {
                    if (isFunction(get(returnFocusElement, "$el.focus"))) {
                        returnFocusElement.$el.focus()
                    }
                })
        },
        onBecomeVisible() {
            if (this.openFocus === "close" && this.showClose) {
                this.$refs.closeButton.$el.focus()
            } else if (this.openFocus === "content") {
                this.focusOnContent()
            }
        },
        focusOnContent() {
            const slotContent = get(this.$slots, "default[0]")

            const $el = get(slotContent, "elm")

            if (isFunction(get($el, "focus"))) {
                $el.focus()
            }
        },
        checkKeydown($event) {
            if ($event.key === "Tab" && $event.shiftKey) {
                this.closeDrawer()
            }
        },
        setParentDrawer() {
            let $current = this.$parent
            let i = 0

            // TODO: Replace with a non-hacky solution ASAP or if one
            // doesn't exist, use a prop instead
            while (i < 500) {
                if ($current.$parent === undefined) {
                    $current = null
                    break
                }

                $current = $current.$parent

                if ($current.$options.name === VsDrawer.name) {
                    break
                }

                i += 1
            }

            this.parentDrawer = $current
        },
    },
}
</script>

<style lang="scss" scoped>
.close-button-wrapper {
    right: 0;
    top: 0;
}

.catch-focus__button {
    width: 0;
    height: 0;
    margin: 0;
    padding: 0;
    border: none;
    display: block;
}
</style>
