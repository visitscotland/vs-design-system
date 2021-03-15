import { shallowMount } from '@vue/test-utils';
import VsCarousel from '../Carousel';

const factoryShallowMount = (slotsData, propsData) => shallowMount(VsCarousel, {
    propsData: {
        ...propsData,
    },
    data() {
        return {
            currentSlide: 3,
        };
    },
    ...slotsData,
});

/* eslint-disable */
window.matchMedia = window.matchMedia || function() {
    return {
        matches: false,
        addListener: function() {},
        removeListener: function() {}
    };
};
/* eslint-enable */

describe('VsCarousel', () => {
    describe(':slots', () => {
        it('renders the mobile pagination correctly', async() => {
            const wrapper = factoryShallowMount({
                slots: {
                    vsCarouselOf: 'of',
                },
            });

            await wrapper.setData({
                currentPage: 1,
                totalSlides: 10,
                currentWidth: 'lg',
            });

            const pagintationText = wrapper.find('[data-test="vs-carousel__mobile-pagination"]').text();

            const formattedPagination = pagintationText.replace(/\s\s+/g, ' ');

            expect(formattedPagination).toBe('2 of 10');
        });
    });

    it('only renders the previous control if the prop is set to true', async() => {
        const wrapper = factoryShallowMount();

        await wrapper.setData({
            prevDisabled: false,
        });

        expect(wrapper.find('.vs-carousel__control--prev').exists()).toBe(true);

        await wrapper.setData({
            prevDisabled: true,
        });

        expect(wrapper.find('.vs-carousel__control--prev').exists()).toBe(false);
    });

    it('only renders the next control if the prop is set to true', async() => {
        const wrapper = factoryShallowMount();

        await wrapper.setData({
            nextDisabled: false,
        });

        expect(wrapper.find('.vs-carousel__control--next').exists()).toBe(true);

        await wrapper.setData({
            nextDisabled: true,
        });

        expect(wrapper.find('.vs-carousel__control--next').exists()).toBe(false);
    });
});
