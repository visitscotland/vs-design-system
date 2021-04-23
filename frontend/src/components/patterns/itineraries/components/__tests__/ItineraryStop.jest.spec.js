import { shallowMount } from '@vue/test-utils';

import VsItineraryStop from '../ItineraryStop';

const earlyTime = {
    hours: 8,
    mins: 30,
    day: 4,
}

const midTime = {
    hours: 14,
    mins: 59,
    day: 4,
}

const lateTime = {
    hours: 18,
    mins: 1,
    day: 4,
}

const opening = '09:00AM';
const closing = '17:00PM';

const factoryShallowMount = (propsData) => shallowMount(VsItineraryStop, {
    propsData: {
        stopLabel: 'Test stop label',
        stopNumber: '0',
        stopTitle: 'Test stop title',
        openingTime: opening,
        closingTime: closing,
        openingTimesLink: 'http://www.visitscotland.com',
        dayName: 'Thursday',
    },
    slots: {
        'stop-image': '<img src="test" alt="">',
        'stop-description': 'Test stop description',
        'stop-address': '<p>address line one<br>address line two</p>',
        'stop-tips': 'Test stop tips',
        'stop-closed': 'Closed',
        'stop-open': 'Open',
        'stop-closing-soon': 'Closing soon',
        'stop-opening-text': 'Usually open today',
        'stop-link-text': 'All opening times',
        'stop-charge-text': 'Free admission',
        'stop-facilities': 'Test facilities content',
        'stop-button': 'Test button text',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsItineraryStop', () => {
    it('should render a component with the data-test attribute `vs-itinerary-stop`', () => {
        expect(wrapper.find('[data-test="vs-itinerary-stop"]').exists()).toBe(true);
    });

    describe(':props', () => {
        it('should render the stopLabel prop', () => {
            expect(wrapper.find('[data-test="vs-itinerary-stop-heading"]').html()).toContain('Test stop label');
        });

        it('should render the stopNumber prop', () => {
            expect(wrapper.find('[data-test="vs-itinerary-stop-marker"]').text()).toBe('0');
        });
    });

    describe(':slots', () => {
        it('renders content inserted into the stop-image slot', () => {
            expect(wrapper.html()).toContain('<img src="test" alt="">');
        });

        it('renders content inserted into the stop-description slot', () => {
            expect(wrapper.html()).toContain('Test stop description');
        });

        it('renders content inserted into the stop-address slot', () => {
            expect(wrapper.find('[data-test="vs-itinerary-stop-address"]').html()).toContain('<p>address line one<br>address line two</p>');
        });

        it('renders content inserted into the stop-tips slot', () => {
            expect(wrapper.html()).toContain('Test stop tips');
        });

        it('renders content inserted into the slot when the stop is closed, open or closing soon', async() => {
            await wrapper.setData({ openingMessage: 'closed' });
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closed');

            await wrapper.setData({ openingMessage: 'open' });
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Open');

            await wrapper.setData({ openingMessage: 'closing soon' });
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closing soon');
        });

        it('renders content inserted into the stop-opening-text slot', () => {
            expect(wrapper.find('[data-test="vs-itinerary-stop-opening"]').text()).toContain('Usually open today');
        });

        it('renders content inserted into the vs-stop-link-text slot', () => {
            expect(wrapper.find('[data-test="vs-stop-link"]').html()).toContain('All opening times');
        });

        it('renders content inserted into the vs-stop-charge-text slot', () => {
            expect(wrapper.find('[data-test="vs-stop-charge-text"]').text()).toBe('Free admission');
        });

        it('renders content inserted into the vs-stop-facilities slot', () => {
            expect(wrapper.html()).toContain('Test facilities content');
        });

        it('renders content inserted into the vs-stop-button slot', () => {
            expect(wrapper.html()).toContain('Test button text');
        });
    });

    describe(':methods', () => {
        it('should display closed text if the current time is before the opening time', async() => {
            await wrapper.setData({ currentTime: earlyTime });
            await wrapper.vm.compareTimes(earlyTime, opening, closing);
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closed');
        });

        it('should display closed text if the current time is after the closing time', async() => {
            await wrapper.setData({ currentTime: lateTime });
            await wrapper.vm.compareTimes(lateTime, opening, closing);
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Closed');
        });

        it('should display open text if the current time is after the opening time and before the closed time', async() => {
            await wrapper.setData({ currentTime: midTime });
            await wrapper.vm.compareTimes(midTime, opening, closing);
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').text()).toContain('Open');
        });

        it('should not render the times slot if the dayName prop does not match current day', async() => {
            await wrapper.setProps({dayName: 'asdf'});
            await wrapper.vm.compareTimes(midTime, opening, closing);
            expect(wrapper.find('[data-test="vs-itinerary-stop-times"]').exists()).toBe(false);
        });
    });
});
