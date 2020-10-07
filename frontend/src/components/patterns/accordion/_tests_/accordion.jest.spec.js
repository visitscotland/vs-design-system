import { shallowMount } from '@vue/test-utils';

import VsAccordion from '../Accordion';

const factoryShallowMount = (propsData) => shallowMount(VsAccordion, {
    propsData: {
        ...propsData,
    },
});

describe('<VsAccordion />', () => {
    it('should render accordion', () => {
        const wrapper = factoryShallowMount();

        const Accordion = wrapper.find('div.vs-accordion');

        expect(Accordion.exists()).toBe(true);
    });
});
