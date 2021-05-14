import { shallowMount } from '@vue/test-utils';
import VsItineraryStopInfo from '../ItineraryStopInfo';
import openingTimesData from './data/openingTimesData.json';

const currentDayData = [{
    key: 'thursday',
    day: 'Thursdays',
    startTime: '09:30',
    endTime: '18:00',
    state: 'open',
    provisional: true,
}];
const currentDayDataUsual = [{
    key: 'thursday',
    day: 'Thursdays',
    startTime: '09:30',
    endTime: '18:00',
    state: 'open',
    provisional: false,
}];

const factoryShallowMount = () => shallowMount(VsItineraryStopInfo, {
    propsData: {
        openingTime: '09:00',
        closingTime: '17:00',
        openingTimesLink: 'http://www.visitscotland.com',
        openingHours: openingTimesData,
        closedText: 'Closed',
        closingSoonText: 'Closing soon',
        openText: 'Open',
        usualText: 'Usually',
        provisionalText: 'Provisionally',
        temporarilyClosedText: 'Temporarily closed',
        toText: 'to',
        andText: 'and',
    },
    slots: {
        'stop-link-text': 'All opening times',
        'stop-charge-text': 'Free admission',
    },
});

let wrapper;
beforeEach(async() => {
    wrapper = factoryShallowMount();
});

describe('VsItineraryStopInfo', () => {
    it('should render a component with the data-test attribute `vs-itinerary-stop-info`', async() => {
        expect(wrapper.find('[data-test="vs-itinerary-stop-info"]').exists()).toBe(true);
    });

    describe(':props', () => {
        it('renders content inserted into the slot when the stop is closed, open or closing soon', async() => {
            await wrapper.setData({
                openingMessage: 'closed',
                currentDayData,
            });

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closed');

            await wrapper.setData({
                openingMessage: 'open',
            });
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Open');

            await wrapper.setData({
                openingMessage: 'closing soon',
            });
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closing soon');
        });

        it('renders the correct provisional/usual text', async() => {
            await wrapper.setData({
                openingMessage: 'closed',
                currentDayData,
            });

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Provisionally');

            await wrapper.setData({
                currentDayData: currentDayDataUsual,
            });

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Usually');
        });

        it('renders the correct text if it is long term closed', async() => {
            wrapper.setData({
                closedLongTerm: true,
                openingMessage: 'closed',
                currentDayData,
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Temporarily closed');
        });
    });

    describe(':slots', () => {
        it('renders content inserted into the vs-stop-link-text slot', async() => {
            await wrapper.setData({
                openingMessage: 'closing soon',
            });
            await wrapper.vm.$nextTick();
            expect(wrapper.find('[data-test="vs-stop-link"]').html()).toContain('All opening times');
        });

        it('renders content inserted into the vs-stop-charge-text slot', async() => {
            await wrapper.setData({
                openingMessage: 'open',
            });
            await wrapper.vm.$nextTick();
            expect(wrapper.find('[data-test="vs-stop-charge-text"]').text()).toBe('Free admission');
        });
    });
});
