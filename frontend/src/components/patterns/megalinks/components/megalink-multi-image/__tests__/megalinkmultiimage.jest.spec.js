import { shallowMount } from '@vue/test-utils';
import VsMegalinkMultiImage from '../MegalinkMultiImage';

const factoryShallowMount = () => shallowMount(VsMegalinkMultiImage, {
    propsData: {
        featured: true,
        imgSrc: 'test',
        linkType: 'external',
    },
});

describe('VsMegalinkMultiImage', () => {
    it('if the featured prop is true, it should render an element with class "megalink-multi-image--featured"', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.find('.megalink-multi-image--featured').exists()).toBe(true);
    });
});
