import { shallowMount } from '@vue/test-utils';
import VsCannedSearchSummaryBox from '../CannedSearchSummaryBox';

const summaryLeftSlotContent = 'This is a price';
const summaryRightSlotContent = 'This is a link';

const factoryShallowMount = () => shallowMount(VsCannedSearchSummaryBox, {
    slots: {
        vsCannedSearchSummaryLeft: summaryLeftSlotContent,
        vsCannedSearchSummaryRight: summaryRightSlotContent,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsCannedSearchSummaryBox', () => {
    describe(':slots', () => {
        it('should render the content of the `vsCannedSearchSummaryLeft` slot', () => {
            expect(wrapper.html()).toContain(summaryLeftSlotContent);
        });

        it('should render the content of the `vsCannedSearchSummaryRight` slot', () => {
            expect(wrapper.html()).toContain(summaryRightSlotContent);
        });
    });
});
