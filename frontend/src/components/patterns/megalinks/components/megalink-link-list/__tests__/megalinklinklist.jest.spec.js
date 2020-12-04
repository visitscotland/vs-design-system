import { shallowMount } from '@vue/test-utils';
import VsMegalinkLinkList from '../MegalinkLinkList';

const factoryShallowMount = () => shallowMount(VsMegalinkLinkList, {
    propsData: {
        featured: true,
        imgSrc: 'test',
        linkType: 'external',
        theme: 'dark',
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
    describe(':props', () => {
        it('renders the correct theme class', () => {
            expect(wrapper.find('.megalink-link-list--dark').exists()).toBe(true);
        });
    });
    describe(':slots', () => {
        it('renders content inserted in a vsLinkListHeading slot', () => {
            expect(wrapper.find('[data-test="megalink-link-list__title"]').text()).toBe('Multi-image heading');
        });

        it('renders content inserted in a vsLinkListContent slot', () => {
            expect(wrapper.find('[data-test="megalink-link-list__content"]').html()).toContain('<p>Multi-image content</p>');
        });
    });
});
