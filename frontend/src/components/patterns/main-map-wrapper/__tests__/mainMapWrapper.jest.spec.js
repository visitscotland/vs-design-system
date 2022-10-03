import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapper from '../MainMapWrapper';

describe('VsMainMapWrapper', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper`', () => {
        const wrapper = shallowMount(VsMainMapWrapper);
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper');
    });

    describe(':methods', () => {
        it('should hide the side panel after the `closePanel` method is fired', async() => {
            const wrapper = shallowMount(VsMainMapWrapper);
            const panel = wrapper.find('[data-test="vs-main-map-wrapper__side-panel"]');

            wrapper.vm.closePanel();

            await wrapper.vm.$nextTick();

            expect(panel.classes('d-none')).toBe(true);
        });

        it('should show the side panel after the `openPanel` method is fired', async() => {
            const wrapper = shallowMount(VsMainMapWrapper);
            const panel = wrapper.find('[data-test="vs-main-map-wrapper__side-panel"]');

            wrapper.vm.closePanel();

            await wrapper.vm.$nextTick();

            wrapper.vm.openPanel();

            await wrapper.vm.$nextTick();

            expect(panel.classes('d-none')).toBe(false);
        });
    });
});
