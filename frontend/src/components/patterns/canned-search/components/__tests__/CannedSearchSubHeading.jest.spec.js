import { mount } from '@vue/test-utils';
import VsCannedSearchSubHeading from '../CannedSearchSubHeading';

const subHeading = 'Callander, Loch Lomond';

const factoryShallowMount = (propsData) => mount(VsCannedSearchSubHeading, {
    propsData: {
        subHeading,
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsCannedSearchSubHeading', () => {
    describe(':props', () => {
        it('should render the content of the `subHeading` property', () => {
            expect(wrapper.html()).toContain(subHeading);
        });
    });
});
