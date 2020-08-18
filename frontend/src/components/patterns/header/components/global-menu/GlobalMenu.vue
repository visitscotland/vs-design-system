<template>
    <div class="vs-global-menu">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    class="vs-global-menu__wrapper"
                >
                    <!-- Mobile Devices Menu -->
                    <VsDropdown
                        text="Our Websites"
                        class="vs-global-menu__websites d-lg-none"
                    >
                        <VsDropdownItem
                            v-for="site in ourWebsites"
                            :key="site.siteName"
                            :href="site.siteUrl"
                        >
                            {{ site.siteName }}
                        </VsDropdownItem>
                    </VsDropdown>

                    <!-- Tablets and Desktops Menu -->
                    <VsList
                        unstyled
                        inline
                        class="vs-global-menu__websites d-none d-lg-flex"
                    >
                        <li
                            v-for="site in ourWebsites"
                            :key="site.siteName"
                            class="vs-global-menu__websites__item"
                            :class="[
                                (site.siteUrl == activeSite)
                                    ? 'vs-global-menu__websites__item--active'
                                    : ''
                            ]"
                        >
                            <a
                                target="_blank"
                                :href="site.siteUrl"
                            >{{ site.siteName }}</a>
                        </li>
                    </VsList>

                    <!-- Login Temporary "Component" -->
                    <div class="vs-global-menu__login">
                        <VsIcon
                            name="user"
                            variant="light"
                            size="xs"
                        />
                        Hi Alexis...
                        <a href="#">
                            (Not you?)
                        </a>
                    </div>

                    <!-- Language Changer Component -->
                    <VsDropdown
                        class="vs-global-menu__languages"
                    >
                        <template
                            v-slot:button-content
                        >
                            <VsIcon
                                class="d-none d-lg-block"
                                name="information"
                                variant="light"
                                size="xs"
                            />
                            <div class="d-lg-none">
                                {{ selectedLanguage }}
                            </div>
                            <!-- Tablet/Desktop -->
                            <div class="d-none d-lg-block">
                                Language: {{ selectedLanguage }}
                            </div>
                        </template>
                        <VsDropdownItem
                            v-for="lang in websiteLanguages"
                            :key="lang.languageTag"
                        >
                            <!-- Mobile Text -->
                            <div class="d-lg-none">
                                {{ lang.languageTag }}
                            </div>
                            <!-- Tablet/Desktop -->
                            <div class="d-none d-lg-block">
                                {{ lang.languageName }}
                            </div>
                        </VsDropdownItem>
                    </VsDropdown>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon';
import VsList from '@components/elements/list';
import {
    VsContainer, VsRow, VsCol,
} from '@components/elements/layout';
import VsDropdown from '../../../dropdown/Dropdown';
import VsDropdownItem from '../../../dropdown/DropdownItem';

export default {
    name: 'VsGlobalMenu',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsIcon,
        VsList,
        VsContainer,
        VsCol,
        VsRow,
        VsDropdown,
        VsDropdownItem,
    },
    props: {
        /**
         * Determines the active website
         */
        activeSite: {
            type: String,
            default: '',
            required: true,
        },
    },
    data() {
        return {
            selectedLanguage: 'EN',
            websiteLanguages: [
                {
                    languageTag: 'EN',
                    languageName: 'English',
                },
                {
                    languageTag: 'DE',
                    languageName: 'Deutsch',
                },
                {
                    languageTag: 'ES',
                    languageName: 'Español',
                },
                {
                    languageTag: 'FR',
                    languageName: 'Français',
                },
                {
                    languageTag: 'IT',
                    languageName: 'Italiano',
                },
                {
                    languageTag: 'NE',
                    languageName: 'Nederlands',
                },
            ],
            ourWebsites: [
                {
                    siteName: 'VisitScotland',
                    siteUrl: 'https://www.visitscotland.com/',
                },
                {
                    siteName: 'Business Events',
                    siteUrl: 'https://businessevents.visitscotland.com/',
                },
                {
                    siteName: 'Travel Trade',
                    siteUrl: 'https://traveltrade.visitscotland.org/',
                },
                {
                    siteName: 'Media Centre',
                    siteUrl: 'https://media.visitscotland.org/',
                },
                {
                    siteName: 'Corporate',
                    siteUrl: 'https://www.visitscotland.org/',
                },
            ],
        };
    },
};
</script>

<style lang="scss">
.vs-global-menu {
    width: 100%;
    background: $color-purple;
    color: white;
    position: relative;
    font-size: 14px;

    .container {
        @media (max-width: 1000px) {
            padding: 0;
            margin: 0;
            max-width: 100%;
        }
    }

    &__wrapper{
        position: initial;
        display: flex;
        align-items: center;
        @media (max-width: 1000px) {
            width: 100%;
            padding: 0;
            margin: 0;
        }
    }

    &__login {
        margin-right: 1rem;
    }

    .dropdown-toggle {
        display: flex;
        align-items: center;
    }

    .icon:not(.icon-chevron-down):not(.icon-chevron-up) {
        margin-right: 0.5rem;
    }
}

.vs-global-menu__websites,
.vs-global-menu__websites .btn,
.vs-global-menu__languages,
.vs-global-menu__languages .btn {
    padding: 0;
    background: transparent;
    border: none;
}

.vs-global-menu__websites,
.vs-global-menu__languages {
    position: initial;

    a {
        color: white;
        text-decoration: none;
        &:hover {
            background: $color-purple-shade-2;
        }
    }

    & .btn {
        padding: 0.5rem;
        font-size: 14px;
        &-secondary:not(:disabled):not(.disabled):active {
            background: $color-purple-shade-2;
        }
        &:focus {
            outline: 3px solid rgba(235,191,219,1);
            outline-offset: -3px;
            box-shadow: none;
        }
    }

    &.show .btn,
    &.show .btn:active,
    &.show .btn:focus {
        background: $color-purple-shade-2;
    }

    ul:focus {
        outline: 3px solid rgba(235,191,219,1);
        outline-offset: -3px;
    }

    .dropdown-menu {
        min-width: auto;
        width: 100%;
        background: $color-purple;
        &.show {
            transform: translate3d(0px, 35px, 0px) !important;
            border: none;
            padding: 0;
            li {
                border-bottom: 1px solid white;

                &:last-of-type {
                    border: none;
                }

                a {
                    padding: 1rem;
                    color: white;
                    text-decoration: none;
                    &:hover, &:focus {
                        background: $color-purple-shade-2;
                    }
                    &:focus {
                        outline: 3px solid   rgba(235,191,219,1);
                        outline-offset: -3px;
                    }
                }
            }
        }
    }
}

.vs-global-menu__websites {
    flex: auto;

    &__item {
        a {
            padding: 0.6rem 1rem;
            &:focus {
                outline: 3px solid rgba(235,191,219,1);
                outline-offset: -3px;
            }
            &:active {
                background: white;
                color: $color-purple-shade-2;
                outline: none;
            }
        }
        &--active {
            a {
            background: $color-white;
            color: $color-purple-shade-2;
                &:hover {
                    color: white;
                }
            }
        }
    }
}

.vs-global-menu__languages {
    @media (min-width: 1000px) {
        position: relative;
    }
}
</style>

<docs>
  ```
    <vs-global-menu active-site="https://www.visitscotland.com/"></vs-global-menu>
  ```
</docs>
