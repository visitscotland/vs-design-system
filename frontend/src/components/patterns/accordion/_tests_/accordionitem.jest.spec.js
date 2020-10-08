import { shallowMount, mount } from '@vue/test-utils';

import VsAccordionItem from '../AccordionItem';

const factoryShallowMount = (propsData) => shallowMount(VsAccordionItem, {
    propsData: {
        ...propsData,
    },
});

describe('<VsAccordionItem />', () => {
    it('should render accordionItem', () => {
        const wrapper = factoryShallowMount({
            controlId: '1234',
        });

        const accordionItem = wrapper.find('bcard-stub.vs-accordion__item');
        const accordionItemHeader = wrapper.find('bcardheader-stub.vs-accordion__item__card-header');
        const accordionItemTitle = wrapper.find('h4.vs-accordion__item__title');
        const accordionItemBody = wrapper.find('.vs-accordion__item__panel');

        expect(accordionItem.exists()).toBe(true);
        expect(accordionItemHeader.exists()).toBe(true);
        expect(accordionItemTitle.exists()).toBe(true);
        expect(accordionItemBody.exists()).toBe(true);
    });

    describe(':props', () => {
        it(':controlId should be assigned to panel id', () => {
            const wrapper = factoryShallowMount({
                controlId: '1234',
            });

            const accordionItemBody = wrapper.find('.vs-accordion__item__panel');

            expect(accordionItemBody.attributes('id')).toBe('1234');
        });

        it(':itemBreakPoint assigns accordion class name', () => {
            const wrapper = factoryShallowMount({
                itemBreakPoint: 'sm',
                controlId: '1234',
            });

            const accordionToggle = wrapper.find('vsaccordiontoggle-stub');

            expect(accordionToggle.classes()).toContain('d-sm-none');
        });

        it(':openByDefault - to open or close accordion by default', () => {
            const wrapper = factoryShallowMount({
                openByDefault: false,
                controlId: '1234',
            });

            const accordionItemBody = wrapper.find('vsaccordiontoggle-stub');
            expect(accordionItemBody.classes()).toContain('d-lg-none');
        });

        it(':variant assigns variant to VsAccordionToggle', () => {
            const wrapper = factoryShallowMount({
                variant: 'secondary',
                controlId: '1234',
            });

            const accordionItemBody = wrapper.find('vsaccordiontoggle-stub');
            expect(accordionItemBody.attributes('variant')).toBe('secondary');
        });
    });

    describe(':methods', () => {
        it(':onButtonClick should toggle accordion', async() => {
            const wrapper = mount(VsAccordionItem, {
                propsData: {
                    controlId: '1234',
                    openByDefault: true,
                },
            });

            const accordionToggle = wrapper.find('.vs-accordion-toggle');
            expect(accordionToggle.attributes('aria-expanded')).toBe('true');

            await accordionToggle.trigger('click');

            expect(accordionToggle.attributes('aria-expanded')).toBe('false');
        });
    });
});