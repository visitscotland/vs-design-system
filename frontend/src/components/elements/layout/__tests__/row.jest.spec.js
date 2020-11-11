import { shallowMount } from '@vue/test-utils';

import VsRow from '../Row';

const slotText = 'Row text';

const factoryShallowMount = (propsData) => shallowMount(VsRow, {
    slots: {
        default: slotText,
    }
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsButton', () => {
    it('should render a brow-stub', () => {
        expect(wrapper.is('brow-stub')).toBe(true);
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            expect(wrapper.text()).toContain(slotText);
        });
    });
});