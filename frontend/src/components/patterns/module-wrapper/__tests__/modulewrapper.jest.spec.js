import { shallowMount } from '@vue/test-utils';
import VsModuleWrapper from '../ModuleWrapper';

const factoryShallowMount = () => shallowMount(VsModuleWrapper, {
    slots: {
        moduleWrapperHeading: 'Module wrapper heading',
        moduleWrapperIntro: '<p>Module wrapper content</p>',
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsModuleWrapper', () => {
    describe(':slots', () => {
        it('renders content inserted in a vsModuleWrapperHeading slot', () => {
            expect(wrapper.find('[data-test="vs-module-wrapper__heading"]').text()).toBe('Module wrapper heading');
        });

        it('renders content inserted in a vsModuleWrapperIntro slot', () => {
            expect(wrapper.find('[data-test="vs-module-wrapper__intro"]').html()).toContain('<p>Module wrapper content</p>');
        });
    });
});
