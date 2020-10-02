import { shallowMount, mount } from '@vue/test-utils';
import VsMegaNav from '../MegaNav';
import VsMegaNavDropdown from '../MegaNavDropdown';

const factoryShallowMount = () => shallowMount(VsMegaNav, {
    propsData: {
        href: 'https://www.visitscotland.com',
    },
    slots: {
        megaNavTopMenuItems: '<div class="mega-nav-top-menu-items"></div>',
    },
});

describe('VsMegaNav', () => {
    it('should render a component with the data-test attribute `.vs-mega-nav`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-mega-nav');
    });

    it('should render a button with an `icon-bars-mobile-menu` icon', () => {
        const wrapper = mount(VsMegaNav);
        const dropdownToggle = wrapper.find('[data-test="vs-mega-nav__dropdown"]').find('.dropdown-toggle');

        expect(dropdownToggle.exists()).toBe(true);
        expect(dropdownToggle.html()).toContain('icon-bars-mobile-menu');
    });

    describe(':props', () => {
        it('should display the URL `https://www.visitscotland.com` on the logo link when passed as `href` prop', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-mega-nav__logo"]').attributes().href).toBe('https://www.visitscotland.com');
        });
    });

    describe(':events', () => {
        it('displays a button with an `icon-close` icon when menuToggled event is emitted', () => {
            const wrapper = mount(VsMegaNav);
            const dropdownToggle = wrapper.find('[data-test="vs-mega-nav__dropdown"]').find('.dropdown-toggle');
            wrapper.find(VsMegaNavDropdown).vm.$emit('menuToggled');

            expect(dropdownToggle.html()).toContain('icon-close');
        });
    });

    describe(':slots', () => {
        it('renders content inserted in a megaNavTopMenuItems slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.findAll('.mega-nav-top-menu-items').length).toBe(1);
        });
    });
});
