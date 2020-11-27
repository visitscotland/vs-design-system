<template>
    <VsMegaNav
        href="/"
        menu-toggle-alt-text="Toggle Menu"
    >
        <template #megaNavTopMenuItems>
            <VsMegaNavTopMenuItem
                v-for="(item, index) in header.mainNav"
                :key="index"
                :href="item.href"
                :cta-text="item.cta"
            >
                <template #buttonContent>
                    {{ item.title }}
                </template>
                <template #dropdownContent>
                    <VsMegaNavList
                        v-for="(subHeading, subHeadingIndex) in item.dropdownNav"
                        :key="subHeadingIndex"
                    >
                        <VsMegaNavListItem slot="navListHeading">
                            {{ subHeading.title }}
                        </VsMegaNavListItem>

                        <VsMegaNavListItem
                            slot="navListItems"
                            v-for="(navLink, navLinkIndex)
                                in subHeading.dropdownNav"
                            :key="navLinkIndex"
                            :href="navLink.href"
                        >
                            {{ navLink.title }}
                        </VsMegaNavListItem>

                        <VsMegaNavListItem
                            v-if="subHeading.href"
                            :href="subHeading.href"
                            subheading-link
                            slot="navHeadingCtaLink"
                        >
                            {{ subHeading.cta }}
                        </VsMegaNavListItem>
                    </VsMegaNavList>
                </template>
            </VsMegaNavTopMenuItem>
        </template>

        <template #megaNavMobileItems>
            <VsMegaNavMobileToggle
                v-for="(item, index) in header.mainNav"
                :key="index"
                :href="item.href"
                :cta-text="item.cta"
            >
                {{ item.title }}
            </VsMegaNavMobileToggle>
        </template>
    </VsMegaNav>
</template>
