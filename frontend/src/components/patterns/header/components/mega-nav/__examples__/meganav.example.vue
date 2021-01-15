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
