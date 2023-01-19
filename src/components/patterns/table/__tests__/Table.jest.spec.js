import { shallowMount } from '@vue/test-utils';

import VsTable from '../Table';

const slotContent = 'A table goes here';

const factoryShallowMount = (propsData) => shallowMount(VsTable, {
    propsData: {
        ...propsData,
        tableCaption: 'A table with information',
    },
    slots: {
        default: slotContent,
    },
});

describe('VsTable', () => {
    it('should render a btablesimple-stub', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.element.tagName).toBe('BTABLESIMPLE-STUB');
    });

    describe(':props', () => {
        it('should display a caption for the table', () => {
            const wrapper = factoryShallowMount();
            const caption = wrapper.find('caption[data-test=vs-table__caption]');
            expect(caption.text()).toContain('A table with information');
        });

        it('sets the `tableType` to responsive by default', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.attributes('responsive')).toBe('true');
        });

        it('sets the `tableType` to stacked when passed in as a prop', () => {
            const wrapper = factoryShallowMount({
                tableType: 'stacked',
            });
            expect(wrapper.attributes('responsive')).toBe(undefined);
            expect(wrapper.attributes('stacked')).toBe('true');
        });
    });

    describe(':slots', () => {
        const wrapper = factoryShallowMount();
        it('renders content inserted into the default `slot`', () => {
            expect(wrapper.text()).toContain(slotContent);
        });
    });
});
