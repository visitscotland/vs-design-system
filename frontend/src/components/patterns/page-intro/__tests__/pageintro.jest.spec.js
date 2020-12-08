import { shallowMount } from '@vue/test-utils';
import VsPageIntro from '../PageIntro';

const factoryShallowMount = () => shallowMount(VsPageIntro, {
    propsData: {
        background: 'dark',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsPageIntro', () => {
    describe(':props', () => {
        it('should render the correct class for the supplied background prop', () => {
            expect(wrapper.find('[data-test="vs-page-intro"]').classes()).toContain('vs-page-intro--dark');
        });
    });
});
