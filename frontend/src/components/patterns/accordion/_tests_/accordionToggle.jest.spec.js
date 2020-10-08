import { shallowMount } from '@vue/test-utils';

import VsAccordionToggle from '../AccordionToggle';

const factoryShallowMount = (propsData) => shallowMount(VsAccordionToggle, {
    propsData: {
        ...propsData,
    },
});

describe('<VsAccordionToggle />', () => {
    it('should render accordionToggle', () => {
        const wrapper = factoryShallowMount();

        const AccordionToggle = wrapper.find('vsbutton-stub.vs-accordion-toggle');

        expect(AccordionToggle.exists()).toBe(true);
    });
});
