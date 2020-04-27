<template>
    <Component
        data-test="mobile-promo-list"
        :is="type"
        class="list-unstyled"
    >
        <VsMobileNavListItem
            v-for="(item, index) in allButLastPromos"
            :level="2"
            :href="item.href"
            :is-external="item.isExternal"
            :title="item.title"
            :subnav="item.subnav"
            :promo-list="item.promoList"
            :promo-item="item.promoItem"
            :key="'listPromos' + index"
        />
        <VsMobileNavPromoItem
            :href="lastPromo.href"
            :is-external="lastPromo.isExternal"
            :title="lastPromo.title"
            :button-text="lastPromo.buttonText"
            :description="lastPromo.description"
            :image-link="lastPromo.imageLink"
        />
    </Component>
</template>

<script>
import VsSvg from "../../../../elements/svg/Svg"
import VsMobileNavPromoItem from "./MobileNavPromoItem"
import VsMobileNavListItem from "./MobileNavListItem"

export default {
    name: "VsMobileNavPromoList",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsSvg,
        VsMobileNavPromoItem,
        VsMobileNavListItem,
    },
    props: {
        /**
         * The html element name used for the component
         */
        type: {
            type: String,
            default: "ul",
        },
        list: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
        }
    },
    computed: {
        // Needs work - breaking lint rules

        // lastPromo() {
        //     const lastPromo = this.list.filter((item, index) => {
        //         if (index === this.list.length - 1) {
        //             return item
        //         }
        //     })
        //     return lastPromo[0]
        // },
        // allButLastPromos() {
        //     const allButLast = this.list.filter((item, index) => {
        //         if (index !== this.list.length - 1) {
        //             return item
        //         }
        //     })
        //     return allButLast
        // },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
</style>

<docs>
  ```jsx

    <div style="max-width: 400px;">
      <vs-mobile-nav-promo-list
        :list="header.mainNav[4].promoList"
      />
    </div>
  ```
</docs>
