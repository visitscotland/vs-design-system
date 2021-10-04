import { shallowMount } from '@vue/test-utils';
import VsCaption from '../Caption';

const factoryMount = (propsData) => shallowMount(VsCaption, {
    propsData: {
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryMount();
});

describe('VsCaption', () => {
    it('should render a caption element', () => {
        expect(wrapper.find('[data-test="vs-caption"]').exists()).toBe(true);
    });
});
