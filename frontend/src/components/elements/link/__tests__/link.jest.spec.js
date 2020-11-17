import { shallowMount } from '@vue/test-utils';
import VsLink from '../Link';
import VsIcon from '../../icon/Icon';

const slotContent = 'Link text';

const factoryShallowMount = (propsData) => shallowMount(VsLink, {
    propsData,
    slots: {
        default: slotContent,
    },
});

describe('VsLink', () => {
    it('should render a b-link with class vs-link', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.classes()).toContain('vs-link');
        expect(wrapper.element.tagName).toBe('BLINK-STUB');
    });

    describe(':slots', () => {
        it(':default - should render the slot content', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.text()).toBe(slotContent);
        });
    });

    describe(':props', () => {
        it(':variant - should render the primary variant by default', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.classes()).toContain('primary');
        });

        it(':variant - should render the dark variant', () => {
            const wrapper = factoryShallowMount({
                variant: 'dark',
            });

            expect(wrapper.classes()).toContain('dark');
            expect(wrapper.classes()).not.toContain('primary');
        });

        it(':href - should apply the supplied href', () => {
            const href = 'http://google.com';
            const wrapper = factoryShallowMount({
                href,
            });

            expect(wrapper.attributes('href')).toBe(href);
        });

        it(':external - should not render an external link by default', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.classes()).not.toContain('vs-link--external');
            expect(wrapper.attributes('target')).toBe('_self');
            expect(wrapper.findComponent(VsIcon).exists()).toBe(false);
        });

        it(':external - should render an external link', () => {
            const wrapper = factoryShallowMount({
                external: true,
            });

            expect(wrapper.classes()).toContain('vs-link--external');
            expect(wrapper.attributes('target')).toBe('_blank');
            expect(wrapper.findComponent(VsIcon).exists()).toBe(true);
        });
    });
});
