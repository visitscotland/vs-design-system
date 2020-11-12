import { shallowMount } from '@vue/test-utils';
import VsStretchedLinkCard from '../StretchedLinkCard';

const factoryShallowMount = () => shallowMount(VsStretchedLinkCard, {
    propsData: {
        link: 'https://www.visitscotland.com/',
        type: 'external',
    },
});

describe('VsStretchedLinkCard', () => {
    it('should render an element with class stretched-link', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.find('[data-test="stretched-link"]').exists()).toBe(true);
    });

    describe(':props', () => {
        it('should render the component with the link prop passed as the stretched link href', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.find('[data-test="stretched-link"]').attributes().href).toBe('https://www.visitscotland.com/');
        });
    });
});
