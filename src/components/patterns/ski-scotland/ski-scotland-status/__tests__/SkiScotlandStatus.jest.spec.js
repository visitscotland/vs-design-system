import { shallowMount } from '@vue/test-utils';
import VsSkiScotlandStatus from '../SkiScotlandStatus';

const centreInformationSlot = 'Centre Information Placeholder';

const factoryShallowMount = (propsData) => shallowMount(VsSkiScotlandStatus, {
    propsData: {
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsSkiScotlandStatus', () => {
    describe(':props', () => {
        it('should render a component with the data-test attribute `vs-ski-scotland-status`', () => {
            expect(wrapper.find('[data-test="vs-ski-scotland-status"]').exists()).toBe(true);
        });
    });

    describe(':slots', () => {
        it('should render content inserted into `centre-information` slot', () => {
            const modifiedWrapper = shallowMount(VsSkiScotlandStatus, {
                slots: {
                    'centre-information': centreInformationSlot,
                },
            });

            expect(modifiedWrapper.text()).toContain(centreInformationSlot);
        });
    });
});
