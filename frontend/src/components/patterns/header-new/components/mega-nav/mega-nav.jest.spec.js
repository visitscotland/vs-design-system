import { shallowMount, mount } from '@vue/test-utils';
import VsMegaNav from './MegaNav';
import VsMegaNavDropdown from './MegaNavDropdown';

const factoryShallowMount = () => shallowMount(VsMegaNav, {
    slots: {
        megaNavTopMenuItems: '<div class="mega-nav-top-menu-items"></div>',
    },
});

describe('VsMegaNav', () => {
    it('should render a component with the class `.vs-mega-nav`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.classes()).toContain('vs-mega-nav');
    });

    it('should render a button with an `icon-bars-mobile-menu` icon', () => {
        const wrapper = mount(VsMegaNav);
        const dropdownToggle = wrapper.find('.dropdown-toggle');

        expect(dropdownToggle.exists()).toBe(true);
        expect(dropdownToggle.html()).toContain('icon-bars-mobile-menu');
    });

    describe(':events', () => {
        it('displays a button with an `icon-close` icon when menu-toggled event is emitted', () => {
            const wrapper = mount(VsMegaNav);
            const dropdownToggle = wrapper.find('.dropdown-toggle');
            wrapper.find(VsMegaNavDropdown).vm.$emit('menu-toggled');

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
