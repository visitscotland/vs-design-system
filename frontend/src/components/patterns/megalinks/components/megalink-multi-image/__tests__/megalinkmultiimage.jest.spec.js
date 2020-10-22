import { shallowMount } from '@vue/test-utils';
import VsMegalinkMultiImage from '../MegalinkMultiImage';

const factoryShallowMount = () => shallowMount(VsMegalinkMultiImage, {
    propsData: {
        featured: true,
        imgSrc: 'test',
        linkType: 'external',
    },
    slots: {
        vsMultiImageHeading: 'Multi-image heading',
        vsMultiImageContent: '<p>Multi-image content</p>',
    },
});

describe('VsMegalinkMultiImage', () => {
    describe(':props', () => {
        it('if the featured prop is true, it should render an element with class "megalink-multi-image--featured"', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('.megalink-multi-image--featured').exists()).toBe(true);
        });
    });

    describe(':slots', () => {
        it('renders content inserted in a vsMultiImageHeading slot', () => {
            const wrapper = factoryShallowMount()
            expect(wrapper.find('.megalink-multi-image__title').text()).toBe('Multi-image heading');
        });

        it('renders content inserted in a vsMultiImageContent slot', () => {
            const wrapper = factoryShallowMount()
            expect(wrapper.find('.megalink-multi-image__content').html()).toContain('<p>Multi-image content</p>');
        });
    });
});
