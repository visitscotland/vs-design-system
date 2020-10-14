import { shallowMount } from '@vue/test-utils';
import VsMegalinks from '../Megalinks';

const factoryShallowMount = () => shallowMount(VsMegalinks);

describe('VsMegalinks', () => {
    describe(':props', () => {
        it('should only render the intro block if the title is supplied', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('.vs-megalinks__intro').exists()).toBe(true);
        });
    });
});
