import { shallowMount } from '@vue/test-utils';

import VsCol from '../Col';

const slotText = 'Col text';

const factoryShallowMount = (propsData) => shallowMount(VsCol, {
    slots: {
        default: slotText,
    },
    attrs: {
        class: "cols-6 sm-12"
    }
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsCol', () => {
    it('should render a bcol-stub', () => {
        expect(wrapper.is('bcol-stub')).toBe(true);
    });

    describe(':attrs', () => {
        it('should accept and render `class` attributes', () => {
            expect([
                wrapper.classes('cols-6'),
                wrapper.classes('sm-12')
            ]).toEqual([
                true, true
            ]);
        });
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            expect(wrapper.text()).toContain(slotText);
        });
    });
});