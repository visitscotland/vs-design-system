import { shallowMount } from '@vue/test-utils';

import VsDescriptionListTerm from '../DescriptionListTerm';

const TestComponent = {
    template: '<div>Test Component</div>',
};

const factoryShallowMount = (propsData) => shallowMount(VsDescriptionListTerm, {
    slots: {
        default: TestComponent,
    },
    propsData: {
        ...propsData,
    },
    provide: {
        inline: true,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('<VsDescriptionListTerm />', () => {
    it('should render a <vscol-stub /> with a `vs-description-list__term` class', () => {
        expect(wrapper.is('vscol-stub')).toBe(true);
        expect(wrapper.classes('vs-description-list__term')).toBe(true);
    });

    describe(':props', () => {
        it(':inline - should accept an `inline` property injected by the parent component', () => {
            /* eslint-disable no-underscore-dangle */
            expect(wrapper.vm._provided.inline).toBe(true);
        });

        it(':inline - render a `list-inline-item` class when `inline` is injected as `true`', () => {
            expect(wrapper.classes('list-inline-item')).toBe(true);
        });

        it(':inline - render a `vs-description-list__term--styled` class when `inline` is injected as `false`', () => {
            const modifiedWrapper = shallowMount(VsDescriptionListTerm, {
                slots: {
                    default: TestComponent,
                },
                provide: {
                    inline: false,
                },
            });

            /* eslint-disable no-underscore-dangle */
            expect(modifiedWrapper.vm._provided.inline).toBe(false);
            expect(modifiedWrapper.classes('vs-description-list__term--styled')).toBe(true);
        });
    });

    it(':slots - renders content inserted into default `slot`', () => {
        const modifiedWrapper = factoryShallowMount();

        expect(modifiedWrapper.text()).toContain('Test Component');
    });
});
