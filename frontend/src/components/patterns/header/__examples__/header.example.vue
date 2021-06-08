<template>
    <div style="min-height: 600px;">
        <VsHeader>
            <template #globalMenu>
                <VsGlobalMenu
                    dropdown-label="Our websites"
                    active-site="https://www.visitscotland.com/"
                >
                    <template
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
                    </template>
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
                            :align="item.title === 'Inspiration'
                                ? 'bottom' : 'top'"
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
                            <template
                                slot="navFeaturedItems"
                                v-if="item.title === 'Accommodation'
                                    || item.title === 'Inspiration'"
                            >
                                <VsMegaNavFeaturedItem
                                    link="www.visitscotland.com"
                                    img-url="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                                    img-alt="Alt text"
                                >
                                    <template slot="vsFeaturedItemHeader">
                                        From our home to yours – see Scotland virtually
                                    </template>

                                    <template slot="vsFeaturedItemContent">
                                        <p>
                                            Although it’s not possible to come to Scotland
                                            at the moment, thanks to modern technology,
                                            you can still see stunning
                                        </p>
                                    </template>

                                    <template slot="vsFeaturedItemLink">
                                        A link to a page
                                    </template>
                                </VsMegaNavFeaturedItem>
                            </template>
                        </VsMegaNavTopMenuItem>
                    </template>
                    <template #megaNavMobileItems>
                        <VsAccordion>
                            <VsMegaNavAccordionItem
                                :title="item.title"
                                level="1"
                                :control-id="'menu_accordion_item_' + mobileItemIndex"
                                v-for="(item, mobileItemIndex) in header.mainNav"
                                :key="mobileItemIndex"
                            >
                                <VsMegaNavList v-if="item.href && item.cta">
                                    <VsMegaNavListItem
                                        slot="navListHeading"
                                        :href="item.href"
                                        cta-link
                                    >
                                        {{ item.cta }}
                                    </VsMegaNavListItem>
                                </VsMegaNavList>

                                <VsMegaNavAccordionItem
                                    :title="subHeading.title"
                                    level="2"
                                    :control-id="'menu_accordion_item_' + subHeadingIndex"
                                    v-for="(subHeading, subHeadingIndex) in item.dropdownNav"
                                    :key="subHeadingIndex"
                                >
                                    <VsMegaNavList>
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
                                            v-if="subHeading.href && subHeading.cta"
                                            :href="subHeading.href"
                                            subheading-link
                                            slot="navHeadingCtaLink"
                                        >
                                            {{ subHeading.cta }}
                                        </VsMegaNavListItem>
                                    </VsMegaNavList>
                                    <template
                                        slot="navFeaturedItems"
                                        v-if="item.title === 'Accommodation'
                                            || item.title === 'Inspiration'"
                                    >
                                        <VsMegaNavFeaturedItem
                                            link="www.visitscotland.com"
                                            img-url="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                                            img-alt="Alt text"
                                        >
                                            <template slot="vsFeaturedItemHeader">
                                                From our home to yours – see Scotland virtually
                                            </template>

                                            <template slot="vsFeaturedItemContent">
                                                <p>
                                                    Although it’s not possible to come to
                                                    Scotland at the moment, thanks to modern
                                                    technology, you can still see stunning
                                                </p>
                                            </template>

                                            <template slot="vsFeaturedItemLink">
                                                A link to a page
                                            </template>
                                        </VsMegaNavFeaturedItem>
                                    </template>
                                </VsMegaNavAccordionItem>
                            </VsMegaNavAccordionItem>
                        </VsAccordion>
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
