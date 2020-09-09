<template>
    <div style="min-height: 600px;">
        <VsHeaderNew>
            <template #globalNav>
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
            <template #megaNav>
                <VsMegaNav>
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
