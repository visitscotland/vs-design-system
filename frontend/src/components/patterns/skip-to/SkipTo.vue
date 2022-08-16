<template>
    <section class="vs-skip-to d-flex">
        <p class="vs-skip-to__label">
            <!-- @slot text for 'skip to' label -->
            <slot name="skipToText" />:
        </p>
        <VsLink @click.native="mainMenuFocus">
            <!-- @slot text for 'Main menu' -->
            <slot name="mainMenuText" />
        </VsLink>
        <VsLink
            href="#main"
            @click.native="mainContentFocus"
        >
            <!-- @slot text for 'Main content' -->
            <slot name="mainContentText" />
        </VsLink>
        <VsLink @click.native="searchFocus">
            <!-- @slot text for 'Searcgt' -->
            <slot name="searchText" />
        </VsLink>
        <VsLink @click.native="footerFocus">
            <!-- @slot text for 'Footer' -->
            <slot name="footerText" />
        </VsLink>
    </section>
</template>

<script>
import VsLink from '@components/elements/link/Link';
// import {
//     isFunction, get, isNumber,
// } from 'lodash';
// import VsSkipToButton from './components/SkipToButton';

/**
 * The SkipTo component provides users of assistive
 * technologies with a focusable control that moves the
 * focus to a provided target element when activated.
 *
 * @displayName Skip To
 */
export default {
    name: 'VsSkipTo',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsLink,
    },
    methods: {
        mainMenuFocus() {
            const mobileMenuBtn = document.getElementsByClassName('vs-mega-nav__menu__mobile')[0];
            const firstMenuItem = document.getElementsByClassName('vs-mega-nav-dropdown')[0];
            let firstMenuBtn = firstMenuItem.getElementsByClassName('btn')[0];

            if (mobileMenuBtn.offsetParent !== null) {
                // if the mobile menu is visible, open it and focus
                // the first link
                mobileMenuBtn.querySelectorAll('.btn.dropdown-toggle')[0].click();
                const firstMobileMenuItem = document.getElementsByClassName('vs-mega-nav-accordion-item--level-1')[0];
                const firstMobileBtn = firstMobileMenuItem.querySelectorAll('.vs-button.vs-accordion-toggle')[0];
                firstMenuBtn = firstMobileBtn;

                // timeout need to ensure menu items to be accessible in DOM
                setTimeout(() => {
                    firstMenuBtn.focus();
                }, 200);
            } else {
                firstMenuBtn.focus();
            }
        },
        searchFocus() {
            const searchBtn = document.getElementsByClassName('vs-site-search')[0];
            const searchInput = document.getElementsByClassName('vs-input--site-search')[0];

            searchBtn.click();
            setTimeout(() => {
                searchInput.focus();
            }, 200);
        },
        mainContentFocus() {
            const mainElement = document.getElementById('main');
            const primaryHeading = mainElement.querySelector('h1');

            primaryHeading.scrollIntoView(true);
            primaryHeading.focus();
        },
        footerFocus() {
            const footerElement = document.getElementsByClassName('vs-footer')[0];
            const firstFooterSection = footerElement.getElementsByClassName('vs-footer-accordion-item')[0];
            const firstFooterLink = firstFooterSection.getElementsByClassName('vs-link')[0];
            const footerMobileToggle = firstFooterSection.getElementsByClassName('vs-accordion-toggle')[0];

            // if mobile footer toggle link is visible click to open
            if (footerMobileToggle.offsetParent !== null) {
                footerMobileToggle.click();
            }

            footerElement.scrollIntoView(true);

            // focus the first footer link - timeout allows it to be
            // accessible in DOM for mobile accordion
            setTimeout(() => {
                firstFooterLink.focus();
            }, 200);
        },
    },
    //     skipTo() {
    //         let element;

    //         if (isFunction(get(this.target, 'focus'))) {
    //             element = this.target;
    //         } else if (isFunction(get(this.target, '$el.focus'))) {
    //             element = this.target.$el;
    //         }

    //         if (element) {
    //             if (!isNumber(element.tabIndex)) {
    //                 element.tabIndex = -1;
    //             }
    //             element.focus();
    //         }
    //     },
    // },
};
</script>

<style lang="scss">
    .vs-skip-to {
        position: absolute;
        transform: translateY(-100%);
        top: 0;
        height: $spacer-9;
        align-items: center;
        padding: 0 $spacer-2;

        &__label {
            margin: 0;
        }

        &:focus-within {
            position: relative;
            transform: translateY(0);
        }

        .vs-link {
            margin-left: $spacer-2;
        }

        @include media-breakpoint-up(lg) {
            height: $spacer-10;
        }
    }
</style>
