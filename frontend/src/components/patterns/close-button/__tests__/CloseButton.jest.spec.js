import { mount } from '@vue/test-utils';

import VsCloseButton from '../CloseButton';

const slotContent = 'SR Only Content';

const factoryMount = (propsData) => mount(VsCloseButton, {
    propsData: {
        ...propsData,
    },
    slots: {
        default: slotContent,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryMount();
});

describe('VSCloseButton', () => {
    it('should render a vsbutton-stub', () => {
        expect(wrapper.find('[data-test="vs-close-button"]').exists()).toBe(true);
    });

    it('should contain a vs-icon', () => {
        expect(wrapper.find('[data-test="vs-icon"]').exists()).toBe(true);
    });

    describe(':slots', () => {
        it('renders content inserted into the default `slot`', () => {
            expect(wrapper.text()).toContain(slotContent);
        });
    });
});
