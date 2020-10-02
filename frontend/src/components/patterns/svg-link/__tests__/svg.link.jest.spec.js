import { shallowMount } from '@vue/test-utils';
import VsSvgLink from '../SvgLink';

const factoryShallowMount = (values) => shallowMount(VsSvgLink, {
    propsData: {
        logoAltText: 'VisitScotland Home',
        href: 'https://www.visitscotland.com/',
        svgFill: '700e57',
        svgPath: 'visitscotland',
        ...values,
    },
});

describe('VsMegaNavDropdown', () => {
    it('should render a component with the data-test attribute `.vs-svg-link`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-svg-link');
    });

    describe(':props', () => {
        it('should render alt text `VisitScotland Home` when passed as `logoAltText` prop', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="logo-alt-text"]').text()).toBe('VisitScotland Home');
        });

        it('should render url `https://www.visitscotland.com/` when passed as `href` prop', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-svg-link"]').attributes().href).toBe('https://www.visitscotland.com/');
        });

        it('should render colour `700e57` when passed as `svgFill` prop', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-svg-link__svg"]').attributes().fill).toBe('700e57');
        });

        it('should render path `svgPath` when passed as `svgPath` prop', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="vs-svg-link__svg"]').attributes().path).toBe('visitscotland');
        });
    });
});
