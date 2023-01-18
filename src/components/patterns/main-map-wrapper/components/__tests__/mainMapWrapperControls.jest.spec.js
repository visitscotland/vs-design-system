import { shallowMount, createWrapper } from '@vue/test-utils';
import VsMainMapWrapperControls from '../MainMapWrapperControls';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperControls, {
    provide: {
        clearSelectionText: 'Clear selection',
        applyFiltersText: 'Apply filter',
    },
});

describe('VsMainMapWrapperControls', () => {
    it('should render a component with the data-test attribute "vs-main-map-controls"', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-main-map-controls');
    });

    it('should display the text from the `clearSelectionText` injected value', () => {
        const wrapper = factoryShallowMount();
        const clearBtn = wrapper.find('[data-test="vs-main-map-subcategory__clear-selection"]');
        expect(clearBtn.text()).toBe('Clear selection');
    });

    it('should display the text from the `applyFilterText` injected value', () => {
        const wrapper = factoryShallowMount();
        const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filters"]');
        expect(applyBtn.text()).toBe('Apply filter');
    });

    describe(':methods', () => {
        it('should set `isDisabled` to true when clear all button is clicked', async() => {
            const wrapper = factoryShallowMount();
            const clearBtn = wrapper.find('[data-test="vs-main-map-subcategory__clear-selection"]');
            await clearBtn.trigger('click');

            expect(wrapper.vm.isDisabled).toBe(true);
        });

        it('should ensure that the submit button is disabled by default', async() => {
            const wrapper = factoryShallowMount();
            const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filters"]');
            expect(applyBtn.attributes('aria-disabled')).toBe('true');
        });

        it('should ensure that the submit button is enabled when the data attribute is changed', async() => {
            const wrapper = factoryShallowMount();
            wrapper.setData({
                isDisabled: false,
            });
    
            await wrapper.vm.$nextTick();
            const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filters"]');
            expect(applyBtn.attributes('aria-disabled')).toBe(undefined);
        });
    });
});
