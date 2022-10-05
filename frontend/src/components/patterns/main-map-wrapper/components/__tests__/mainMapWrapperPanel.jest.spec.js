import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperPanel from '../MainMapWrapperPanel';

describe('VsMainMapWrapperPanel', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper-panel`', () => {
        const wrapper = shallowMount(VsMainMapWrapperPanel);
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper-panel');
    });

    it('should emit `close-panel` when the close button is clicked', async() => {
        const wrapper = shallowMount(VsMainMapWrapperPanel);
        const closeBtn = wrapper.find('[data-test="vs-main-map-wrapper-panel--btn"]');

        await closeBtn.trigger('click');

        expect(wrapper.emitted('close-panel')).toBeTruthy();
    });

    it('should display a header with the `categoryHeading` prop value', async() => {
        const wrapper = shallowMount(VsMainMapWrapperPanel);
        await wrapper.setProps({
            categoryHeading: 'This is a heading',
        });
        const panelHeading = wrapper.find('[data-test="vs-main-map-categories__heading"]');

        expect(panelHeading.text()).toBe('This is a heading');
    });

    it('should not display a header if the `categoryHeading` prop is blank', async() => {
        const wrapper = shallowMount(VsMainMapWrapperPanel);
        const panelHeading = wrapper.find('[data-test="vs-main-map-categories__heading"]');

        expect(panelHeading.exists()).toBe(false);
    });
});
