import { shallowMount, mount } from '@vue/test-utils';

import VsIcentre from '../Icentre';

// const headingSlot = 'Heading Slot';
// const textSlot = 'Text Slot';
// const ctaSlot = 'CTA Slot';

const inputSrc = 'https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm';
const inputAlt = 'This is the alt text';

const factoryShallowMount = (slotsData) => shallowMount(VsIcentre, {
    propsData: {
        imgSrc: inputSrc,
        imgAlt: inputAlt,
    },
    slots: {
        ...slotsData,
    },
});

const factoryMount = (slotsData) => mount(VsIcentre, {
    propsData: {
        imgSrc: inputSrc,
        imgAlt: inputAlt,
    },
    slots: {
        ...slotsData,
    },
});

describe('VsIknowParterItem', () => {
    it('should render a component with `vs-icentre` data-test attribute', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toContain('vs-icentre');
    });

    it('should render a `VsImageWithCaption` component', () => {
        const wrapper = factoryShallowMount();
        const img = wrapper.find('vsimagewithcaption-stub');

        expect(img.exists()).toBe(true);
    });

    describe(':slots', () => {
        it('renders content inserted in the `icentreHeading` slot', () => {
            const headingContent = 'Icentre test heading';
            const wrapper = factoryShallowMount({
                icentreHeading: headingContent,
            });

            expect(wrapper.text()).toContain(headingContent);
        });

        it('renders content inserted in the `icentreLinks` slot', () => {
            const linksContent = 'Icentre test links';
            const wrapper = factoryShallowMount({
                icentreLinks: linksContent,
            });

            expect(wrapper.text()).toContain(linksContent);
        });

        it('renders content inserted in the `icentreQuote` slot', () => {
            const quoteContent = 'Icentre test quote';
            const wrapper = factoryShallowMount({
                icentreQuote: quoteContent,
            });

            expect(wrapper.text()).toContain(quoteContent);
        });

        it('renders the `icentreLinks` slot within `vs-icentre__standalone-links` if no quote is set', () => {
            const linksContent = 'Icentre test links';
            const wrapper = factoryShallowMount({
                icentreLinks: linksContent,
            });

            const standalone = wrapper.find('[data-test="vs-icentre__standalone-links"]');
            const embedded = wrapper.find('[data-test="vs-icentre__embedded-links"]');

            expect(standalone.exists()).toBe(true);
            expect(embedded.exists()).toBe(false);
            expect(standalone.text()).toContain(linksContent);
        });

        it('renders the `icentreLinks` slot within `vs-icentre__embedded-links` if a quote is set', () => {
            const linksContent = 'Icentre test links';
            const quoteContent = 'Icentre test quote';
            const wrapper = factoryShallowMount({
                icentreLinks: linksContent,
                icentreQuote: quoteContent,
            });

            const standalone = wrapper.find('[data-test="vs-icentre__standalone-links"]');
            const embedded = wrapper.find('[data-test="vs-icentre__embedded-links"]');

            expect(standalone.exists()).toBe(false);
            expect(embedded.exists()).toBe(true);
            expect(embedded.text()).toContain(linksContent);
        });

        it('renders content inserted in the `icentreImageCaption` slot', () => {
            const captionContent = 'Icentre test caption';
            const wrapper = factoryMount({
                icentreImageCaption: captionContent,
            });

            expect(wrapper.text()).toContain(captionContent);
        });

        it('renders content inserted in the `icentreImageCredit` slot', () => {
            const creditContent = 'Icentre test credit';
            const wrapper = factoryMount({
                icentreImageCredit: creditContent,
            });

            expect(wrapper.text()).toContain(creditContent);
        });
    });
});
