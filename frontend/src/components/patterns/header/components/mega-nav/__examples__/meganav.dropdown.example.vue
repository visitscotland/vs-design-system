<template>
    <div class="vs-mega-nav bg-white">
        <VsContainer>
            <VsRow class="align-items-center justify-content-sm-end">
                <VsCol
                    cols="8"
                    md="4"
                    lg="3"
                >
                    <VsSvgLink
                        class="vs-mega-nav__logo"
                        link-alt-text="VisitScotland Home"
                        href="/"
                        svg-fill="700e57"
                        svg-path="visitscotland"
                    />
                </VsCol>

                <!-- Desktop Top Menu Toggles -->
                <VsCol
                    cols="4"
                    md="8"
                    lg="9"
                    class="vs-mega-nav__menu d-none d-lg-block"
                >
                    <VsMegaNavTopMenu>
                        <li
                            class="vs-mega-nav__item"
                            v-for="(item, index) in header.mainNav"
                            :key="index"
                            :href="item.href"
                            :cta-text="item.cta"
                        >
                            <VsMegaNavDropdown
                                menu-toggle-alt-text="Toggle Menu"
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
                            </VsMegaNavDropdown>
                        </li>
                    </VsMegaNavTopMenu>
                </VsCol>

                <!-- Mobile Toggle and Menu -->
                <VsCol
                    cols="4"
                    md="8"
                    lg="9"
                    class="d-flex d-lg-none justify-content-end position-static"
                >
                    <VsMegaNavDropdown
                        menu-toggle-alt-text="Toggle Menu"
                    >
                        <template #buttonContent>
                            <span class="sr-only">
                                Toggle Menu
                            </span>
                            <VsIcon
                                name="bars-mobile-menu"
                                size="md"
                                variant="dark"
                            />
                        </template>

                        <template #dropdownContent>
                            <VsMegaNavMobileToggle
                                v-for="(item, index) in header.mainNav"
                                :key="index"
                                :href="item.href"
                                :cta-text="item.cta"
                            >
                                {{ item.title }}
                            </VsMegaNavMobileToggle>
                        </template>
                    </VsMegaNavDropdown>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>
