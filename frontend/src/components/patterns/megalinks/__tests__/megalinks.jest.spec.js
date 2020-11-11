import { shallowMount } from '@vue/test-utils';
import VsMegalinks from '../Megalinks';

const factoryShallowMount = () => shallowMount(VsMegalinks, {
    propsData: {
        buttonLink: 'http://www.visitscotland.com',
    },
    slots: {
        vsMegalinksHeading: 'Megalinks heading text',
        vsMegalinksIntro: '<p>Megalinks intro text</p>',
        vsMegalinksButton: 'Megalinks button text',
    },
});

describe('VsMegalinks', () => {
    describe(':props', () => {
        it('should only render the button if an href is supplied', async() => {
            const wrapper = factoryShallowMount();

            expect(wrapper.find('[data-test="vs-megalinks__button"]').exists()).toBe(true);

            await wrapper.setProps({
                buttonLink: '',
            });
            expect(wrapper.find('[data-test="vs-megalinks__button"]').exists()).toBe(false);
        });
    });

    describe(':slots', () => {
        const wrapper = factoryShallowMount();

        it('renders content inserted in a vsMegalinksHeading slot', () => {
            expect(wrapper.find('[data-test="vs-megalinks__heading"]').text()).toBe('Megalinks heading text');
        });

        it('renders content inserted in a vsMegalinksIntro slot', () => {
            expect(wrapper.find('[data-test="vs-megalinks__intro"]').html()).toContain('<p>Megalinks intro text</p>');
        });

        it('renders content inserted in a vsMegalinksButton slot', async() => {
            await wrapper.setProps({
                buttonLink: 'http://www.visitscotland.com',
            });

            expect(wrapper.find('[data-test="vs-megalinks__button"]').html()).toContain('Megalinks button text');
        });
    });
});
