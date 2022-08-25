import { shallowMount } from '@vue/test-utils';

import VsContainer from '../Container';

const slotText = 'Container text';

const factoryShallowMount = () => shallowMount(VsContainer, {
    slots: {
        default: slotText,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsContainer', () => {
    it('should render a bcontainer-stub', () => {
        expect(wrapper.element.tagName).toBe('BCONTAINER-STUB');
    });

    describe(':slots', () => {
        it('renders content inserted into default `slot`', () => {
            expect(wrapper.text()).toContain(slotText);
        });
    });
});
