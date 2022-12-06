import { shallowMount, mount } from '@vue/test-utils';
import VsMainMapWrapperSubcategory from '../MainMapWrapperSubcategory';
import subCategoriesData from './data/filters-subcategories.json';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperSubcategory, {
    propsData: {
        data: subCategoriesData,
    },
    provide: {
        clearSelectionText: 'Clear selection',
        applyFiltersText: 'Apply filter',
    },
});

const factoryMount = () => mount(VsMainMapWrapperSubcategory, {
    propsData: {
        data: subCategoriesData,
    },
    provide: {
        clearSelectionText: 'Clear selection',
        applyFiltersText: 'Apply filter',
    },
});

describe('VsMainMapWrapperSubcategory', () => {
    it('should render a component with the data-test attribute `vs-main-map-subcategory`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-subcategory');
    });

    it('should render a checkbox component for each item in the data', () => {
        const wrapper = factoryMount();
        const allCheckboxes = wrapper.findAll('.custom-checkbox');
        expect(allCheckboxes.length).toBe(7);
    });

    it('should display the text from the `clearSelectionText` injected value', () => {
        const wrapper = factoryMount();
        const clearBtn = wrapper.find('[data-test="vs-main-map-subcategory__clear-selection"]');
        expect(clearBtn.text()).toBe('Clear selection');
    });

    it('should display the text from the `applyFilterText` injected value', () => {
        const wrapper = factoryMount();
        const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filters"]');
        expect(applyBtn.text()).toBe('Apply filter');
    });

    it('should render the `apply filters` buttons as disabled by default', () => {
        const wrapper = factoryShallowMount();
        const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filters"]');
        expect(applyBtn.attributes('aria-disabled')).toBe('true');
    });

    it('should remove the `apply filters` buttons disabled status when a filter is selected', async() => {
        const wrapper = factoryShallowMount();
        wrapper.setData({
            selected: ['acco'],
        });

        await wrapper.vm.$nextTick();
        const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filters"]');
        expect(applyBtn.attributes('aria-disabled')).toBe(undefined);
    });

    describe(':methods', () => {
        it('should clear all selected items when the clear selection button is clicked', async() => {
            const wrapper = factoryShallowMount();
            const clearBtn = wrapper.find('[data-test="vs-main-map-subcategory__clear-selection"]');
            await clearBtn.trigger('click');

            expect(wrapper.vm.selected.length).toBe(0);
        });
    });
});
