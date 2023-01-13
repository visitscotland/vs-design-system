import { shallowMount } from '@vue/test-utils';

import VsTableDataCell from '../TableDataCell';

const slotContent = 'A table data cell goes here';

const factoryShallowMount = (propsData) => shallowMount(VsTableDataCell, {
    propsData: {
        ...propsData,
    },
    slots: {
        default: slotContent,
    },
});

describe('VsTableDataCell', () => {
    it('should render a BTD-STUB', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.element.tagName).toBe('BTD-STUB');
    });

    describe(':props', () => {
        it('should display a `stackedHeading` for the table cell', () => {
            const wrapper = factoryShallowMount({
                stackedHeading: 'Status',
            });
            expect(wrapper.attributes('stackedheading')).toBe('Status');
        });
    });

    describe(':slots', () => {
        const wrapper = factoryShallowMount();
        it('renders content inserted into the default `slot`', () => {
            expect(wrapper.text()).toContain(slotContent);
        });
    });
});
