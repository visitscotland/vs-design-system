import { shallowMount } from '@vue/test-utils';

import VsMap from '../Map';

beforeEach(() => {
    window.URL.createObjectURL = jest.fn();
});

describe('VsMap', () => {
    it('should render a map component with the data-test attribute `vs-map`', () => {
        const wrapper = shallowMount(VsMap, {
            methods: {
                initialiseMapComponent: jest.fn(),
            },
        });

        expect(wrapper.find('[data-test="vs-map"]').exists()).toBe(true);
    });
});
