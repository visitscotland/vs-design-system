import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperPanel from '../MainMapWrapperPanel';

describe('VsMainMapWrapperPanel', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper-panel`', () => {
        const wrapper = shallowMount(VsMainMapWrapperPanel);
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper-panel');
    });
});
