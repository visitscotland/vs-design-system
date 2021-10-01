import { mount } from '@vue/test-utils';
import VsCannedSearchAddress from '../CannedSearchAddress';

const address = 'Callander, Loch Lomond';

const factoryShallowMount = (propsData) => mount(VsCannedSearchAddress, {
    propsData: {
        address,
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsCannedSearchAddress', () => {
    describe(':props', () => {
        it('should render the content of the `address` property', () => {
            expect(wrapper.html()).toContain(address);
        });
    });
});
