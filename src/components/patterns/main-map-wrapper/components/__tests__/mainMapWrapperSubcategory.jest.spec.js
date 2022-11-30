import { shallowMount, mount } from '@vue/test-utils';
import VsMainMapWrapperSubcategory from '../MainMapWrapperSubcategory';
import subCategoriesData from './data/filters-subcategories.json';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperSubcategory, {
    propsData: {
        data: subCategoriesData,
    },
    provide: {
        clearSelectionText: 'Clear selection',
        applyFilterText: 'Apply filter',
    },
});

const factoryMount = () => mount(VsMainMapWrapperSubcategory, {
    propsData: {
        data: subCategoriesData,
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

    it('should display the text from the `clearSelectionText` slot', () => {
        const wrapper = factoryMount();
        console.log(wrapper.html());
        const clearBtn = wrapper.find('[data-test="vs-main-map-subcategory__clear-selection"]');
        expect(clearBtn.text()).toBe('Clear selection');
    });

    it('should display the text from the `applyFilterText` slot', () => {
        const wrapper = factoryMount();
        const applyBtn = wrapper.find('[data-test="vs-main-map-subcategory__apply-filter"]');
        expect(applyBtn.text()).toBe('Apply filter');
    });
});
