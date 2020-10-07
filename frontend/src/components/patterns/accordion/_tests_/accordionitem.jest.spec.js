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

        console.log(wrapper.html());

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
    });
});
