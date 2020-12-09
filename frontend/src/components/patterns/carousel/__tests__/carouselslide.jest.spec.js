import { shallowMount } from '@vue/test-utils';
import VsCarouselSlide from '../components/CarouselSlide';

const imgSrc = 'https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm';

const factoryShallowMount = () => shallowMount(VsCarouselSlide, {
    propsData: {
        imgSrc,
        imgAlt: 'Img alt',
        linkType: 'internal',
        linkUrl: 'http://www.visitscotland.com',
    },
    slots: {
        vsCarouselSlideCategory: 'Slide category',
        vsCarouselSlideHeading: 'Slide heading',
    },
});

describe('VsCarouselSlide', () => {
    describe(':slots', () => {
        it('renders default vsCarouselSlideHeading slot content', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-carousel-slide__title"]').text()).toContain('Slide heading');
        });
    });
});
