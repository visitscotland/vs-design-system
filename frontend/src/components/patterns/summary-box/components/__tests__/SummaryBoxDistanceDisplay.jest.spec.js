import { shallowMount } from '@vue/test-utils';

import VsSummaryBoxDistanceDisplay from '../SummaryBoxDistanceDisplay';

const factoryShallowMount = (computed) => shallowMount(VsSummaryBoxDistanceDisplay, {
    propsData: {
        miles: '10',
        milesLabel: 'miles',
        kilometres: '20',
        kilometresLabel: 'km',
    },
    computed: {
        ...computed,
    },
});

describe('VsSummaryBoxDistanceDisplay', () => {
    it('should render a component with the data-test attribute `vs-summary-box-distance-display`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-summary-box-distance-display');
    });

    describe(':props', () => {
        it('if `showMiles` is true in the store, it should accept and render a `miles` property', () => {
            const wrapper = factoryShallowMount({
                isShowingMiles: () => true,
            });

            expect(wrapper.text()).toContain('10');
        });

        it('if `showMiles` is false in the store, it should accept and render a `kilometres` property', () => {
            const wrapper = factoryShallowMount({
                isShowingMiles: () => false,
            });

            expect(wrapper.text()).toContain('20');
        });

        it('if `showMiles` is true in the store, it should accept and render a `milesLabel` property', () => {
            const wrapper = factoryShallowMount({
                isShowingMiles: () => true,
            });

            expect(wrapper.text()).toContain('miles');
        });

        it('if `showMiles` is false in the store, it should accept and render a `kilometresLabel` property', () => {
            const wrapper = factoryShallowMount({
                isShowingMiles: () => false,
            });

            expect(wrapper.text()).toContain('km');
        });
    });
});
