import { shallowMount } from '@vue/test-utils';
import VsMegaNavTopMenu from './MegaNavTopMenu';

const factoryShallowMount = () => shallowMount(VsMegaNavTopMenu, {
    slots: {
        default: '<li class="top-menu-item"></li>',
    },
});

describe('VsMegaNavTopMenu', () => {
    it('should render a component with the class `.vs-mega-nav__top-menu`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.classes()).toContain('vs-mega-nav__top-menu');
    });

    describe(':slots', () => {
        it('renders a list item in the default slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.findAll('.top-menu-item').length).toBe(1);
        });
    });
});
