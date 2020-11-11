import { shallowMount } from '@vue/test-utils';

import VsIcon from '../Icon';

const factoryShallowMount = (propsData) => shallowMount(VsIcon, {
    propsData: {
        ...propsData,
        name: 'accessparkdrop',
    }
});

describe('VsIcon', () => {
    it('should render a vs-svg stub', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.is('vssvg-stub')).toBe(true);
    });

    describe(':props', () => {
        it(':name - should render the VsIcon with the class `facility-accessparkdrop`', () => {
            expect(true).toBe(false);
        });

        it(':name - should render the VsIcon with the path `icons/facility-accessparkdrop`', () => {
            expect(true).toBe(false);
        });

        it(':variant - should accept and render variants as props', () => {
            const testVariant = 'success';
            const wrapper = factoryShallowMount({
                variant: testVariant,
            });

            expect(wrapper.classes(`icon-${testVariant}`)).toBe(true);
        });

        it(':orientation - should accept and render orientations as props', () => {
            const testOrientation = 'down';
            const wrapper = factoryShallowMount({
                orientation: testOrientation,
            });

            expect(wrapper.classes(`icon--${testOrientation}`)).toBe(true);
        });

        it(':size - should accept and render sizes as props', () => {
            const testSize = 'lg';
            const wrapper = factoryShallowMount({
                size: testSize,
            });

            expect(wrapper.classes(`icon-${testSize}`)).toBe(true);
        });
    });
});