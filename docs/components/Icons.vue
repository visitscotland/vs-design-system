<template>
    <vs-row>
        <vs-col cols="12" sm="6" md="4" xl="3" v-for="path in icons" v-bind:key="path">
            <div class="card">
                <vs-icon :name="path" />
                <pre>{{ path }}</pre>
            </div>
        </vs-col>
    </vs-row>
</template>

<script>
const allIcons = getAllIcons()
import VsIcon from "@components/elements/icon/"
import { VsRow, VsCol } from "@components/elements/layout"
import { trimStart, map } from "lodash"

export default {
    name: "Icons",
    components: { VsIcon, VsCol, VsRow },
    data() {
        return {
            icons: allIcons,
        }
    },
    methods: {},
}

function getAllIcons() {
    const all = require.context("@/assets/svg/icons", false, /^\.\/.*\.svg$/)
    return map(all.keys(), key => {
        return trimStart(key, "./").replace(".svg", "")
    })
}
</script>

<style lang="scss" scoped>
.card {
    text-align: center;
    padding: 0.5rem 0;
    margin-bottom: 1rem;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
}
</style>

<docs>
  ```jsx
    <icons />
  ```
</docs>
