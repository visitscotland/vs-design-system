import { shallowMount } from '@vue/test-utils';
import VsMegalinkLinkList from '../MegalinkLinkList';

const factoryShallowMount = () => shallowMount(VsMegalinkLinkList, {
    propsData: {
        featured: true,
        imgSrc: 'test',
        linkType: 'external',
        linkUrl: 'www.visitscotland.com',
    },
    slots: {
        vsLinkListHeading: 'Multi-image heading',
        vsLinkListContent: '<p>Multi-image content</p>',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsMegalinkLinkList', () => {
    describe(':slots', () => {
        it('renders content inserted in a vsLinkListHeading slot', () => {
            expect(wrapper.find('[data-test="megalink-link-list__title"]').text()).toBe('Multi-image heading');
        });

        it('renders content inserted in a vsLinkListContent slot', () => {
            expect(wrapper.find('[data-test="megalink-link-list__content"]').html()).toContain('<p>Multi-image content</p>');
        });
    });
});
