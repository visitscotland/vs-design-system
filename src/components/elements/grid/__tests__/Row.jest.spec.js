import { shallowMount } from '@vue/test-utils';

import VsRow from '../Row';

const slotText = 'Row text';

const factoryShallowMount = () => shallowMount(VsRow, {
    slots: {
        default: slotText,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsButton', () => {
    it('should render a div', () => {
        expect(wrapper.element.tagName).toBe('DIV');
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            expect(wrapper.text()).toContain(slotText);
        });
    });
});
