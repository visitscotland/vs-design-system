```jsx
<template>
    <VsMegaNav
        href="/"
        menu-toggle-alt-text="Toggle Menu"
        search-button-text="Search"
    >
        <template #megaNavTopMenuItems>
            <VsMegaNavTopMenuItem
                v-for="(item, index) in header.mainNav"
                :key="index"
                :href="item.href"
                :cta-text="item.cta"
                :align="item.title === 'Accommodation'
                    ? 'bottom' : 'top'"
            >
                <template #buttonContent>
                    {{ item.title }}
                </template>
                <template #dropdownContent>
                    <VsMegaNavList
                        v-for="(subHeading, subHeadingIndex) in item.dropdownNav"
                        :key="subHeadingIndex"
                        :list-heading="subHeading.title"
                    >
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
                    slot="navFeaturedEvent"
                    v-if="item.title === 'Things to do'"
                >
                    <MegaNavFeaturedEvent
                        source-url="http://172.28.81.65:8089/data/component/cannedsearch?prodtypes=even&locplace=&locprox=&loc=Scotland&size=1"
                    />
                </template>

                <template
                    slot="navFeaturedItem"
                    v-if="item.title === 'Accommodation' || item.title === 'Inspiration'"
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
                                Although it’s not possible to come to Scotland at the moment,
                                thanks to modern technology, you can still see stunning
                            </p>
                        </template>

                        <template slot="vsFeaturedItemLink">
                            A link to a page
                        </template>
                    </VsMegaNavFeaturedItem>
                </template>

                <div class="featured-items">
                    <template
                        slot="navFeaturedItemLeft"
                        v-if="item.title === 'Inspiration'"
                    >
                        <VsMegaNavFeaturedItem
                            link="www.visitscotland.com"
                            img-url="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            img-alt="Alt text"
                            v-if="item.title==='Inspiration'"
                        >
                            <template slot="vsFeaturedItemHeader">
                                Featured item left
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
                </div>
            </VsMegaNavTopMenuItem>
        </template>

        <template #megaNavAccordionItems>
            <VsAccordion>
                <VsMegaNavAccordionItem
                    :title="item.title"
                    level="1"
                    :control-id="mobileItemIndex.toString()"
                    :cta-link="item.href"
                    :cta-text="item.cta"
                    v-for="(item, mobileItemIndex) in header.mainNav"
                    :key="mobileItemIndex"
                    @click.stop.native="$root.$emit('navAccordionClick', item.title)"
                >
                    <VsMegaNavAccordionItem
                        :title="subHeading.title"
                        level="2"
                        :control-id="subHeadingIndex.toString()"
                        v-for="(subHeading, subHeadingIndex) in item.dropdownNav"
                        :key="subHeadingIndex"
                        @click.stop.native="$root.$emit('navAccordionClick', subHeading.title)"
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
                                v-if="subHeading.href"
                                :href="subHeading.href"
                                subheading-link
                                slot="navHeadingCtaLink"
                            >
                                {{ subHeading.cta }}
                            </VsMegaNavListItem>
                        </VsMegaNavList>
                    </VsMegaNavAccordionItem>
                    <div class="featured-items">
                        <template
                            v-if="item.title === 'Things to do'"
                        >
                            <MegaNavFeaturedEvent
                                source-url="http://172.28.81.65:8089/data/component/cannedsearch?prodtypes=even&locplace=&locprox=&loc=Scotland&size=1"
                            />
                        </template>
                        <template
                            v-if="item.title === 'Accommodation' || item.title === 'Inspiration'"
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
                                        Scotland at the moment.
                                    </p>
                                </template>

                                <template slot="vsFeaturedItemLink">
                                    A link to a page
                                </template>
                            </VsMegaNavFeaturedItem>
                        </template>
                        <template
                            v-if="item.title === 'Inspiration'"
                        >
                            <VsMegaNavFeaturedItem
                                link="www.visitscotland.com"
                                img-url="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                                img-alt="Alt text"
                                v-if="item.title==='Inspiration'"
                            >
                                <template slot="vsFeaturedItemHeader">
                                    Featured item left
                                </template>

                                <template slot="vsFeaturedItemContent">
                                    <p>
                                        Although it’s not possible to come to Scotland
                                        at the moment, thanks to modern technology, you
                                        can still see stunning
                                    </p>
                                </template>

                                <template slot="vsFeaturedItemLink">
                                    A link to a page
                                </template>
                            </VsMegaNavFeaturedItem>
                        </template>
                    </div>
                </VsMegaNavAccordionItem>
            </VsAccordion>
        </template>
    </VsMegaNav>
</template>
```
