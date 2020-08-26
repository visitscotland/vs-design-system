<template>
    <div style="min-height: 600px;">
        <VsHeaderNew>
            <template slot="global-navbar">
                <div
                    class="navbar bg-primary-purple"
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
                        >
                            {{ item.title }}

                            <template slot="subnav">
                                <ul>
                                    <li
                                        v-for="(subnavItem, subnavIndex) in item.subnav"
                                        :key="subnavIndex"
                                    >
                                        {{ subnavItem.title }}
                                    </li>
                                </ul>
                            </template>
                        </VsMegaNavTopMenuItem>
                    </template>

                    <template slot="mega-nav-mobile-items">
                        <VsMegaNavMobileListItem
                            v-for="(item, index) in header.mainNav"
                            :key="index"
                            :href="item.href"
                            :cta-text="item.cta"
                        >
                            {{ item.title }}
                        </VsMegaNavMobileListItem>
                    </template>
                </VsMegaNav>
            </template>
        </VsHeaderNew>

        <template class="container">
            <div class="row">
                <div
                    id="content-container"
                    class="col p-3"
                >
                    Dummy page contents
                </div>
            </div>
        </template>
    </div>
</template>
