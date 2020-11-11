import { shallowMount } from '@vue/test-utils';

import VsIcon from '../Icon';

const factoryShallowMount = (propsData) => shallowMount(VsIcon, {
    propsData: {
        ...propsData,
        name: 'accessparkdrop',
    }
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsIcon', () => {
    it('should render a vs-svg stub', () => {
        expect(true).toBe(false);
    });

    describe(':props', () => {
        it(':name - should render the VsIcon with the class `facility-accessparkdrop`', () => {
            expect(true).toBe(false);
        });

        it(':name - should render the VsIcon with the path `icons/facility-accessparkdrop`', () => {
            expect(true).toBe(false);
        });

        it(':variant - should accept and render a `variant` property', () => {
            expect(true).toBe(false);
        });

        it(':orientation - should accept and render a `orientation` property', () => {
            expect(true).toBe(false);
        });

        it(':size - should accept and render a `size` property', () => {
            expect(true).toBe(false);
        });
    });
});