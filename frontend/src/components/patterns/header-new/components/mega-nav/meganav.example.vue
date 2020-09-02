<template>
    <VsMegaNav>
        <template #mega-nav-top-menu-items>
            <VsMegaNavTopMenuItem
                v-for="(item, index) in header.mainNav"
                :key="index"
                :href="item.href"
                :cta-text="item.cta"
            >
                <template #button-content>
                    {{ item.title }}
                </template>
                <template #dropdown-content>
                    <ul>
                        <li
                            v-for="(navItem, navIndex) in item.dropdownNav"
                            :key="navIndex"
                        >
                            {{ navItem.title }}
                        </li>
                    </ul>
                </template>
            </VsMegaNavTopMenuItem>
        </template>

        <template #mega-nav-mobile-items>
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
