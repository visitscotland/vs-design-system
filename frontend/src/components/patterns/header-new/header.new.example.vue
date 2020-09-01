<template>
    <div style="min-height: 600px;">
        <VsHeaderNew>
            <template slot="global-navbar">
                <div
                    class="bg-primary-purple"
                    style="height: 35px"
                >
                    <VsContainer>
                        <VsRow>
                            <VsCol cols="12">
                                <span style="color: white">Global Nav</span>
                            </VsCol>
                        </VsRow>
                    </VsContainer>
                </div>
            </template>
            <template slot="mega-nav">
                <VsMegaNav>
                    <template slot="mega-nav-top-menu-items">
                        <VsMegaNavTopMenuItem
                            v-for="(item, index) in header.mainNav"
                            :key="index"
                            :href="item.href"
                            :cta-text="item.cta"
                            :control-id="'top-menu-item' + index"
                        >
                            <template slot="button-content">
                                {{ item.title }}
                            </template>
                            <template slot="dropdown-content">
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

                    <template slot="mega-nav-mobile-items">
                        <VsMegaNavMobileToggle
                            v-for="(item, index) in header.mainNav"
                            :key="index"
                            :href="item.href"
                            :cta-text="item.cta"
                            control-id="mobile-menu-toggle"
                        >
                            {{ item.title }}
                        </VsMegaNavMobileToggle>
                    </template>
                </VsMegaNav>
            </template>
        </VsHeaderNew>

        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    class="pt-6"
                >
                    Dummy page contents
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>
