import { shallowMount, mount } from '@vue/test-utils';
import VsMainMapWrapperSubcategory from '../MainMapWrapperSubcategory';
import subCategoriesData from './data/filters-subcategories.json';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperSubcategory, {
    propsData: {
        data: subCategoriesData,
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
});
