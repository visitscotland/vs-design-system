import { shallowMount } from '@vue/test-utils';
import VsSkiScotlandStatus from '../SkiScotlandStatus';

const factoryShallowMount = () => shallowMount(VsSkiScotlandStatus);

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsSkiScotlandStatus', () => {
    describe(':props', () => {
        it('should render an element with the data-attr `vs-ski-scotland-status-wrapper class', () => {
            expect(wrapper.element.tagName).toBe('div');
        });
    });
});
