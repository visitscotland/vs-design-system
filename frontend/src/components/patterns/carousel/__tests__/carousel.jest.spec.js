import { shallowMount } from '@vue/test-utils';
import VsCarousel from '../Carousel';

const splide = {
    index: 8,
    length: 10,
};

const factoryShallowMount = (slotsData) => shallowMount(VsCarousel, {
    data() {
        return {
            currentSlide: 3,
        };
    },
    ...slotsData,
});

describe('VsCarousel', () => {
    it('should change the currentSlide prop when the currentSlide method is called', () => {
        const wrapper = factoryShallowMount();
        wrapper.vm.carouselMoved(splide);
        expect(wrapper.vm.currentSlide).toBe(splide.index + 1);
    });

    it('should change the totalSlides prop when the initialiseCustomOptions is called', () => {
        const wrapper = factoryShallowMount();
        wrapper.vm.initialiseCustomOptions(splide);
        expect(wrapper.vm.totalSlides).toBe(splide.length);
    });

    it('renders the mobile pagination correctly', async() => {
        const wrapper = factoryShallowMount({
            slots: {
                vsCarouselOf: 'of',
            },
        });

        await wrapper.setData({
            currentSlide: 4,
            totalSlides: 10,
        });

        expect(wrapper.find('[data-test="vs-carousel__mobile-pagination"]').text()).toBe('4 of 10');
    });
});
