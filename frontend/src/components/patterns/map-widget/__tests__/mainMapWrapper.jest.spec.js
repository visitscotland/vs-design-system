import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapper from '../MainMapWrapper';

describe('VsMainMapWrapper', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper`', () => {
        const wrapper = shallowMount(VsMainMapWrapper);
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper');
    });
});
