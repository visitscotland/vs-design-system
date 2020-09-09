import { shallowMount, mount } from '@vue/test-utils';
import VsMegaNavDropdown from './MegaNavDropdown';

const factoryShallowMount = () => shallowMount(VsMegaNavDropdown, {
    slots: {
        buttonContent: '<div class="button-content"></div>',
        ctaLink: '<div class="cta-link"></div>',
        dropdownContent: '<div class="dropdown-content"></div>',
    },
});

describe('VsMegaNavDropdown', () => {
    it('should render a component with the class `.vs-mega-nav__dropdown`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.classes()).toContain('vs-mega-nav__dropdown');
    });

    describe(':slots', () => {
        it('renders content inserted in a ctaLink slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.findAll('.cta-link').length).toBe(1);
        });

        it('renders content inserted in a dropdownContent slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.findAll('.dropdown-content').length).toBe(1);
        });
    });

    describe(':events', () => {
        it('emits `menuToggled` event when `bv::dropdown::show` is emitted from root', () => {
            const wrapper = mount(VsMegaNavDropdown);
            wrapper.vm.$root.$emit('bv::dropdown::show');

            expect(wrapper.emitted().menuToggled).toBeTruthy();
        });

        it('emits `menuToggled` event when `bv::dropdown::hide` is emitted from root', () => {
            const wrapper = mount(VsMegaNavDropdown);
            wrapper.vm.$root.$emit('bv::dropdown::hide');

            expect(wrapper.emitted().menuToggled).toBeTruthy();
        });

        it('resize to have been called', () => {
            const onResize = jest.fn();
            window.addEventListener('resize', onResize);
            window.dispatchEvent(new Event('resize'));

            // wrapper.find('.dropdown-toggle').trigger('click');
            // console.log(wrapper.html());

            expect(onResize).toHaveBeenCalled();
        });
    });
});
