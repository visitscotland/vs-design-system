import { shallowMount } from '@vue/test-utils';
import VsBlogDetails from '../BlogDetails';

const factoryShallowMount = (propsData) => shallowMount(VsBlogDetails, {
    propsData: {
        ...propsData,
        blogAuthor: 'Maria Garcia Tena',
        blogPublishDate: 'May 10, 2023',
        blogReadTime: 'Reading time: 2 minutes',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsBlogDetails', () => {
    it('should render a component with the data-test attribute `vs-blog-details`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-blog-details');
    });

    describe(':props', () => {
        it('should render an author when passed `blogAuthor` prop', () => {
            const blogDetails = wrapper.find('[data-test=vs-blog-details]').find('.vs-blog-details__author');
            expect(blogDetails.text()).toContain('Maria Garcia Tena');
        });

        it('should render a date when passed `blogPublishDate` prop', () => {
            const blogDetails = wrapper.find('[data-test=vs-blog-details]').find('.vs-blog-details__date');
            expect(blogDetails.text()).toContain('May 10, 2023');
        });

        it('should render a read time when passed `blogReadTime` prop', () => {
            const blogDetails = wrapper.find('[data-test=vs-blog-details]').find('.vs-blog-details__read-time');
            expect(blogDetails.text()).toContain('Reading time: 2 minutes');
        });
    });
});
