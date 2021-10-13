import { shallowMount } from '@vue/test-utils';

import VSPsr from '../Psr';

const factoryShallowMount = () => shallowMount(VSPsr, {
    propsData: {
        prefilled: [{
            loc: 'Edinburgh',
        }],
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VSPsr', () => {
    it('should render a div with the class `vs-psr`', () => {
        wrapper = factoryShallowMount();

        expect(wrapper.element.tagName).toBe('DIV');
        expect(wrapper.classes('vs-psr')).toBe(true);
    });

    describe(':props', () => {
        it('should render data attributes defined in props', async() => {
            await wrapper.vm.$nextTick();
            expect(wrapper.attributes('data-loc')).toBe('Edinburgh');
        });
    });
});
