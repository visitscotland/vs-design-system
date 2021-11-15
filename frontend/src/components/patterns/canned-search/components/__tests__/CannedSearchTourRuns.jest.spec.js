import { shallowMount } from '@vue/test-utils';
import VsCannedSearchTourRuns from '../CannedSearchTourRuns';

const label = 'Departs';
const startDay = 'June';
const endDay = 'September';

const factoryShallowMount = () => shallowMount(VsCannedSearchTourRuns, {
    propsData: {
        label,
        startDay,
        endDay,
    },
});

describe('VsCannedSearchTourRuns', () => {
    describe(':props', () => {
        it('should render the `label` property', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(label);
        });

        it('should render the `startDay` property', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(startDay);
        });

        it('should render the `endDay` property', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(endDay);
        });
    });
});
