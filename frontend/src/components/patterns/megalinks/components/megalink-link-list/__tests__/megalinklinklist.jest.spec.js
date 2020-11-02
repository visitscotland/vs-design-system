import { shallowMount } from '@vue/test-utils';
import VsMegalinkLinkList from '../MegalinkLinkList';

const factoryShallowMount = () => shallowMount(VsMegalinkLinkList, {
    propsData: {
        featured: true,
        imgSrc: 'test',
        linkType: 'external',
    },
    slots: {
        vsLinkListHeading: 'Multi-image heading',
        vsLinkListContent: '<p>Multi-image content</p>',
    },
});

describe('VsMegalinkLinkList', () => {
    describe(':slots', () => {
        it('renders content inserted in a vsLinkListHeading slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('.megalink-link-list__title').text()).toBe('Multi-image heading');
        });

        it('renders content inserted in a vsLinkListContent slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('.megalink-link-list__content').html()).toContain('<p>Multi-image content</p>');
        });
    });
});
