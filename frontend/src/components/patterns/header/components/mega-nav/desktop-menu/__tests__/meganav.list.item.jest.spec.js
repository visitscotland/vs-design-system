import { shallowMount } from '@vue/test-utils';
import VsMegaNavListItem from '../MegaNavListItem';

const factoryShallowMount = () => shallowMount(VsMegaNavListItem, {

});

describe('VsMegaNavListItem', () => {
    it('should render a component with the data-test attribute `.vs-mega-nav-list-item`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-mega-nav-list-item');
    });
});
