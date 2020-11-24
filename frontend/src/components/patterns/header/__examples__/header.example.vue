<template>
    <div style="min-height: 600px;">
        <VsHeader>
            <template #globalMenu>
                <VsGlobalMenu
                    dropdown-label="I nostri siti"
                    active-site="https://www.visitscotland.com/"
                >
                    <span
                        slot="third-menu-item"
                    >
                        <VsGlobalMenuLanguage>
                            <VsGlobalMenuLanguageItem language-name="English" />
                            <VsGlobalMenuLanguageItem language-name="Deutsch" />
                            <VsGlobalMenuLanguageItem language-name="Español" />
                            <VsGlobalMenuLanguageItem language-name="Français" />
                            <VsGlobalMenuLanguageItem language-name="Italiano" />
                            <VsGlobalMenuLanguageItem language-name="Nederlands" />
                        </VsGlobalMenuLanguage>
                    </span>
                </VsGlobalMenu>
            </template>
            <template #megaNav>
                <VsMegaNav
                    href="/"
                    menu-toggle-alt-text="Toggle Menu"
                >
                    <template #megaNavTopMenuItems>
                        <VsMegaNavTopMenuItem
                            v-for="(item, itemIndex) in header.mainNav"
                            :key="itemIndex"
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
                            v-for="(item, mobileItemIndex) in header.mainNav"
                            :key="mobileItemIndex"
                            :href="item.href"
                            :cta-text="item.cta"
                        >
                            {{ item.title }}
                        </VsMegaNavMobileToggle>
                    </template>
                </VsMegaNav>
            </template>
        </VsHeader>

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
