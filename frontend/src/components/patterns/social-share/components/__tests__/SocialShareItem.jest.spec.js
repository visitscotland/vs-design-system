import { shallowMount } from '@vue/test-utils';

import VsSocialShareItem from '../SocialShareItem';

const factoryShallowMount = (propsData) => shallowMount(VsSocialShareItem, {
    propsData: {
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsSocialShareItem', () => {
    it('should render a component with the data-test attribute `vs-social-share`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-social-share');
    });
});
