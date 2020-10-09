import { shallowMount } from '@vue/test-utils';

import VsButtonSquareSocial from '../ButtonSquareSocial';

const factoryShallowMount = (propsData) => shallowMount(VsButtonSquareSocial, {
    propsData: {
        ...propsData,
    },
});

describe('<VsButtonSquareSocial />', () => {
    it('should render ButtonSquareSocial', () => {
        const wrapper = factoryShallowMount();

        const ButtonSquareSocial = wrapper.find('vslink-stub.button-square-social');

        expect(ButtonSquareSocial.exists()).toBe(true);
    });
});
