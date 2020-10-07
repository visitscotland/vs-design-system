import { shallowMount } from '@vue/test-utils';

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

        const AccordionItem = wrapper.find('bcard-stub.vs-accordion__item');
        const AccordionItemHeader = wrapper.find('bcardheader-stub.vs-accordion__item__card-header');
        const AccordionItemTitle = wrapper.find('h4.vs-accordion__item__title');
        const AccordionItemBody = wrapper.find('.vs-accordion__item__panel');

        expect(AccordionItem.exists()).toBe(true);
        expect(AccordionItemHeader.exists()).toBe(true);
        expect(AccordionItemTitle.exists()).toBe(true);
        expect(AccordionItemBody.exists()).toBe(true);
    });

    describe(':props', () => {
        it(':controlId should be assigned to panel id', () => {
            const wrapper = factoryShallowMount({
                controlId: '1234',
            });

            const AccordionItemBody = wrapper.find('.vs-accordion__item__panel');

            expect(AccordionItemBody.attributes('id')).toBe('1234');
        });

        it(':itemBreakPoint assigns accordion class name', () => {
            const wrapper = factoryShallowMount({
                itemBreakPoint: 'sm',
                controlId: '1234',
            });

            const AccordionToggle = wrapper.find('vsaccordiontoggle-stub');

            expect(AccordionToggle.classes()).toContain('d-sm-none');
        });

        it(':openByDefault - to show accordion open or closed by default', () => {
            const wrapper = factoryShallowMount({
                openByDefault: 'false',
                controlId: '1234',
            });

            const AccordionItemBody = wrapper.find('vsaccordiontoggle-stub');
            expect(AccordionItemBody.attributes('visible')).toBe('false');
        });

        it(':variant assigns variant to VsAccordionToggle', () => {
            const wrapper = factoryShallowMount({
                variant: 'secondary',
                controlId: '1234',
            });

            const AccordionItemBody = wrapper.find('vsaccordiontoggle-stub');
            expect(AccordionItemBody.attributes('variant')).toBe('secondary');
        });
    });
});
