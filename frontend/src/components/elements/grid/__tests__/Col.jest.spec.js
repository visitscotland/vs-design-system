import { shallowMount } from '@vue/test-utils';

import VsCol from '../Col';

const slotText = 'Col text';

const factoryShallowMount = () => shallowMount(VsCol, {
    slots: {
        default: slotText,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsCol', () => {
    it('should render a bcol-stub', () => {
        expect(wrapper.element.tagName).toBe('BCOL-STUB');
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            expect(wrapper.text()).toContain(slotText);
        });
    });
});
