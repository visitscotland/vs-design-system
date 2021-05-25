import { shallowMount } from '@vue/test-utils';
import VsArticleSidebar from '../ArticleSidebar';

const quoteSlotText = 'Article sidebar quote';
const imgSlotText = 'Article sidebar img';

const factoryShallowMount = (propsData) => shallowMount(VsArticleSidebar, {
    propsData: {
        ...propsData,
    },
    slots: {
        vsArticleSidebarQuote: quoteSlotText,
        vsArticleSidebarImg: imgSlotText,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsArticleSidebar', () => {
    it('should render a component with the data-test attribute `vs-article-sidebar`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-article-sidebar');
    });

    describe(':props', () => {
        it(':sidebarAlign - Should add the correct alignment class for `left` sidebar', () => {
            wrapper = factoryShallowMount({
                sidebarAlign: 'left',
            });

            const sidebar = wrapper.find('[data-test="vs-article-sidebar"');
            expect(sidebar.classes()).toContain('vs-article-sidebar--left');
        });

        it(':sidebarAlign - Should add the correct alignment class for `right` sidebar', () => {
            wrapper = factoryShallowMount({
                sidebarAlign: 'right',
            });

            const sidebar = wrapper.find('[data-test="vs-article-sidebar"');
            expect(sidebar.classes()).toContain('vs-article-sidebar--right');
        });
    });

    describe(':slots', () => {
        it('renders content inserted in a `vsArticleSidebarQuote` slot', () => {
            expect(wrapper.text()).toContain(quoteSlotText);
        });

        it('renders content inserted in a `imgSlotText` slot', () => {
            expect(wrapper.text()).toContain(imgSlotText);
        });
    });
});
