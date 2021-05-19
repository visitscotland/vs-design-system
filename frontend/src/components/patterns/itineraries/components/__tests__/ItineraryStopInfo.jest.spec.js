import { shallowMount } from '@vue/test-utils';
import VsItineraryStopInfo from '../ItineraryStopInfo';
import openingTimesData from './data/openingTimesData.json';
import openingTimesDataClosed from './data/openingTimesDataClosed.json';

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
const dayDataMultiple = [
    {
        key: 'thursday',
        day: 'Thursdays',
        startTime: '09:30',
        endTime: '12:00',
        state: 'open',
        provisional: false,
    },
    {
        key: 'thursday',
        day: 'Thursdays',
        startTime: '12:30',
        endTime: '18:00',
        state: 'open',
        provisional: false,
    },
];

const factoryShallowMount = () => shallowMount(VsItineraryStopInfo, {
    propsData: {
        openingTime: '09:00',
        closingTime: '17:00',
        openingTimesLink: 'http://www.visitscotland.com',
        openingHours: openingTimesData.openingHours,
        closedText: 'Closed',
        closingSoonText: 'Closing soon',
        openText: 'Open',
        usualText: 'Usually',
        provisionalText: 'Provisionally',
        temporarilyClosedText: 'Temporarily closed',
        toText: 'To text',
        andText: 'And text',
    },
    slots: {
        'stop-link-text': 'All opening times',
        'stop-charge-text': 'Free admission',
    },
});

let wrapper;
beforeEach(async() => {
    wrapper = factoryShallowMount();

    await wrapper.setData({
        currentDayData,
        dayDataIndex: 0,
        openingMessage: 'open',
        isCurrentTimeframe: false,
        parsedHours: openingTimesData.openingHours,
    });

    await wrapper.vm.isActiveDate();
});

describe('VsItineraryStopInfo', () => {
    it('should render a component with the data-test attribute `vs-itinerary-stop-info`', async() => {
        expect(wrapper.find('[data-test="vs-itinerary-stop-info"]').exists()).toBe(true);
    });

    describe(':props', () => {
        it('renders content inserted into the slot when the stop is closed, open or closing soon', async() => {
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Open');

            await wrapper.setData({
                openingMessage: 'closed',
            });

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closed');

            await wrapper.setData({
                openingMessage: 'closing soon',
            });
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closing soon');
        });

        it('renders the correct provisional/usual text', async() => {
            await wrapper.setData({
                openingMessage: 'closed',
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
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Temporarily closed');
        });

        it('renders the to text prop correctly', async() => {
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('To text');
        });

        it('renders the and text prop correctly', async() => {
            await wrapper.setData({
                currentDayData: dayDataMultiple,
            });

            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('And text');
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
            expect(wrapper.find('[data-test="vs-stop-charge-text"]').text()).toBe('Free admission');
        });
    });

    describe(':methods', () => {
        it('correctly defines whether or not todays date is within the active date parameters', async() => {
            expect(wrapper.vm.isCurrentTimeframe).toBe(true);
        });

        it('test', async() => {
            await wrapper.setProps({
                openingHours: openingTimesDataClosed.openingHours,
            });
            await wrapper.setData({
                parsedHours: openingTimesDataClosed.openingHours,
            });

            await wrapper.vm.isActiveDate();
            expect(wrapper.vm.isCurrentTimeframe).toBe(false);
        });

        it('shows the correct opening message depending on time', async() => {
            await wrapper.setData({
                currentTime: {
                    day: 4,
                    hours: 18,
                    minutes: 30,
                },
            });

            wrapper.vm.getCurrentHoursInfo();

            expect(wrapper.vm.openingMessage).toBe('closed');

            await wrapper.setData({
                currentTime: {
                    day: 4,
                    hours: 12,
                    minutes: 30,
                },
            });

            wrapper.vm.getCurrentHoursInfo();

            expect(wrapper.vm.openingMessage).toBe('open');

            await wrapper.setData({
                currentTime: {
                    day: 4,
                    hours: 17,
                    minutes: 40,
                },
            });

            wrapper.vm.getCurrentHoursInfo();

            expect(wrapper.vm.openingMessage).toBe('closing soon');
        });
    });
});
