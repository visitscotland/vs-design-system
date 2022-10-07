import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapper from '../MainMapWrapper';

const factoryShallowMount = () => shallowMount(VsMainMapWrapper, {
    slots: {
        openSidePanelText: 'Open panel',
    },
});

describe('VsMainMapWrapper', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper');
    });

    describe(':methods', () => {
        it('should hide the side panel after the `closePanel` method is fired', async() => {
            const wrapper = factoryShallowMount();
            const panel = wrapper.find('[data-test="vs-main-map-wrapper__side-panel"]');

            wrapper.vm.closePanel();

            await wrapper.vm.$nextTick();

            expect(panel.classes('d-none')).toBe(true);
        });

        it('should show the side panel after the `openPanel` method is fired', async() => {
            const wrapper = factoryShallowMount();
            const panel = wrapper.find('[data-test="vs-main-map-wrapper__side-panel"]');

            wrapper.vm.closePanel();

            await wrapper.vm.$nextTick();

            wrapper.vm.openPanel();

            await wrapper.vm.$nextTick();

            expect(panel.classes('d-none')).toBe(false);
        });
    });

    describe(':slots', () => {
        it('should display the `openMapText` slot', () => {
            const wrapper = factoryShallowMount();
            const openMapBtn = wrapper.find('[data-test="vs-main-map-wrapper__map-toggle"]');

            expect(openMapBtn.text()).toBe('Open panel');
        });
    });
});
