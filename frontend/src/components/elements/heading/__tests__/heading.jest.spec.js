import { shallowMount } from '@vue/test-utils';

import VsHeading from '../Heading';

const defaultSlot = 'Default Slot';
const subHeadingSlot = 'Sub Heading Slot';

let wrapper;
beforeEach(() => {
    wrapper = shallowMount(VsHeading, {
    });
});

describe('<VsHeading />', () => {
    it('should render a <h1 /> by default', () => {
        expect(wrapper.is('h1')).toBe(true);
        expect(wrapper.classes('heading')).toBe(true);
    });

    describe(':props', () => {
        it(':level - should accept and render a `level` props', () => {
            wrapper.setProps({
                level: 2,
            });

            expect(wrapper.is('h2')).toBe(true);
        });

        it(':thin - should accept and render a `thin` props', () => {
            wrapper.setProps({
                thin: true,
            });

            expect(wrapper.classes('heading--thin')).toBe(true);
        });

        it(':thin - should *NOT* render a `heading--thin` class if `thin` is set to false', () => {
            wrapper.setProps({
                thin: false,
            });

            expect(wrapper.classes('heading--thin')).toBe(false);
        });
    });

    describe('slots:', () => {
        it('should render content inserted into `default` slot', () => {
            const modifiedWrapper = shallowMount(VsHeading, {
                slots: {
                    default: defaultSlot,
                },
            });

            expect(modifiedWrapper.text()).toContain(defaultSlot);
        });

        it('should render content inserted into `sub-heading` slot', () => {
            const modifiedWrapper = shallowMount(VsHeading, {
                slots: {
                    'sub-heading': subHeadingSlot,
                },
            });

            expect(modifiedWrapper.text()).toContain(subHeadingSlot);
        });

        it('should *NOT* render a div with the class `heading__sub-heading` if sub-heading slot is not provided', () => {
            const subHeading = wrapper.find('.heading__sub-heading');

            expect(subHeading.exists()).toBe(false);
        });
    });
});
