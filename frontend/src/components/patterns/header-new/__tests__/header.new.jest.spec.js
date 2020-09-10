import { shallowMount } from '@vue/test-utils';
import VsHeaderNew from '../HeaderNew';

const factoryShallowMount = () => shallowMount(VsHeaderNew, {
    slots: {
        globalNav: '<div class="global-nav"></div>',
        megaNav: '<div class="mega-nav"></div>',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsHeaderNew', () => {
    it('should render a component with the class `.vs-header`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-header');
    });

    describe(':slots', () => {
        it('renders content inserted in a globalNav slot', () => {
            expect(wrapper.findAll('.global-nav').length).toBe(1);
        });

        it('renders content inserted in a megaNav slot', () => {
            expect(wrapper.findAll('.mega-nav').length).toBe(1);
        });
    });
});
