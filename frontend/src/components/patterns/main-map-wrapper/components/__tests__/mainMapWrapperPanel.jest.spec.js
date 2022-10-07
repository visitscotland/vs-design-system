import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperPanel from '../MainMapWrapperPanel';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperPanel, {
    slots: {
        closeSidePanelText: 'Close panel',
        backBtnText: 'Back',
    },
});

describe('VsMainMapWrapperPanel', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper-panel`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper-panel');
    });

    describe(':methods', () => {
        it('should emit `close-panel` when the close button is clicked', async() => {
            const wrapper = factoryShallowMount();
            const closeBtn = wrapper.find('[data-test="vs-main-map-wrapper-panel--btn-close"]');

            await closeBtn.trigger('click');

            expect(wrapper.emitted('close-panel')).toBeTruthy();
        });

        it('should move the stage back one when the back button is clicked', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                currentStage: 1,
            });
            await wrapper.vm.$nextTick();
            const backBtn = wrapper.find('[data-test="vs-main-map-wrapper-panel--btn-back"]');

            backBtn.trigger('click');

            expect(wrapper.vm.currentStage).toBe(0);
        });

        it('should move the stage forward when the `setCategory` method is called', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                currentStage: 1,
            });
            wrapper.vm.setCategory('cities');

            expect(wrapper.vm.currentStage).toBe(2);
        });

        it('should move the update the `selectedCategory` value when the `setCategory` method is called', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                currentStage: 1,
            });
            wrapper.vm.setCategory('cities');

            expect(wrapper.vm.selectedCategory).toBe('cities');
        });
    });

    describe(':props', () => {
        it('should display a header with the `categoryHeading` prop value', async() => {
            const wrapper = factoryShallowMount();
            await wrapper.setProps({
                categoryHeading: 'This is a heading',
            });
            const panelHeading = wrapper.find('[data-test="vs-main-map-categories__heading"]');

            expect(panelHeading.text()).toBe('This is a heading');
        });

        it('should not display a header if the `categoryHeading` prop is blank', async() => {
            const wrapper = factoryShallowMount();
            const panelHeading = wrapper.find('[data-test="vs-main-map-categories__heading"]');

            expect(panelHeading.exists()).toBe(false);
        });
    });

    describe(':slots', () => {
        it('should display a button with the `closePanelText` prop value', async() => {
            const wrapper = factoryShallowMount();
            const closeBtn = wrapper.find('[data-test="vs-main-map-wrapper-panel--btn-close"]');

            expect(closeBtn.text()).toBe('Close panel');
        });

        it('should display a button with the `backBtnText` prop value', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                currentStage: 1,
            });
            await wrapper.vm.$nextTick();
            const backBtn = wrapper.find('[data-test="vs-main-map-wrapper-panel--btn-back"]');

            expect(backBtn.text()).toBe('Back');
        });
    });
});
