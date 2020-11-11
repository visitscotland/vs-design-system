import { shallowMount } from '@vue/test-utils';

import VsImg from '../Img';

const slotText = 'Image text';

const factoryShallowMount = (propsData) => shallowMount(VsImg, {
    slots: {
        default: slotText,
    },
    propsData: {
        ...propsData,
        src: 'https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm',
        alt: 'Claire standing stones',
    }
});

describe('VsButton', () => {
    it('should render a bimg-stub', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.is('bimg-stub')).toBe(true);
    });

    describe(':props', () => {
        it(':should accept and render a `src` property', () => {
            expect(true).toBe(false);
        });

        it(':should accept and render an `alt` property', () => {
            expect(true).toBe(false);
        });

        it(':should accept and render a `fluid` property', () => {
            expect(true).toBe(false);
        });

        it(':should accept and render a `fluid-grow` property', () => {
            expect(true).toBe(false);
        });
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.text()).toContain(slotText);
        });
    });
});