import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperCategories from '../MainMapWrapperCategories';

describe('VsMainMapWrapperCategories', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper-panel`', () => {
        const wrapper = shallowMount(VsMainMapWrapperCategories);
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-categories');
    });
});
