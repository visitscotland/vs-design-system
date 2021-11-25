import { shallowMount } from '@vue/test-utils';
import VsImageWithCaption from '../ToggleButton';

const toggleIdValue = 'toggle-id';

const defaultSlotText = 'Click here to toggle caption';
const toggleIconSlot = 'Toggle icon';

const factoryShallowMount = (propsData) => shallowMount(VsImageWithCaption, {
    propsData: {
        toggleId: toggleIdValue,
        ...propsData,
    },
    slots: {
        'toggle-icon': toggleIconSlot,
        default: defaultSlotText,
    },
});

describe('VsImageWithCaption', () => {
    it('should render a component with the data-test attribute `vs-image-with-caption-toggle`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-image-with-caption-toggle');
    });

    describe(':props', () => {
        it('should set correct ID for aria controls with `toggleId` prop', () => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('.vs-image-with-caption-toggle-caption-btn');

            expect(toggleCaptionBtn.attributes('aria-controls')).toBe(`image_${toggleIdValue}`);
        });
    });

    describe(':slots', () => {
        it('renders content in the `toggle-icon` slot', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(toggleIconSlot);
        });

        it('renders content in the `default` slot', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.html()).toContain(defaultSlotText);
        });
    });

    describe(':methods', () => {
        it('emits `toggleImage` when clicked', async() => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('.vs-image-with-caption-toggle-caption-btn');

            toggleCaptionBtn.trigger('click');
            await wrapper.vm.$nextTick();

            expect(wrapper.emitted().toggleImage).toBeTruthy();
            expect(wrapper.vm.showCaption).toBe(true);
        });

        it(':toggleCaption - icon is updated when the caption is toggled on', async() => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('.vs-image-with-caption-toggle-caption-btn');
            await toggleCaptionBtn.trigger('click');

            expect(wrapper.find('vsicon-stub').attributes('name')).toBe('close-circle');
        });

        it(':toggleCaption - caption hide icon when toggled twice', async() => {
            const wrapper = factoryShallowMount();
            wrapper.vm.toggleCaption();
            wrapper.vm.toggleCaption();
            await wrapper.vm.$nextTick();

            expect(wrapper.html()).toContain(toggleIconSlot);
        });
    });
});
