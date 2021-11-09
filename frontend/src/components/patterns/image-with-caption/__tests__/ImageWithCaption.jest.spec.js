import { shallowMount } from '@vue/test-utils';
import VsImageWithCaption from '../ImageWithCaption';

const defaultSlotText = 'Image';
const toggleIconSlot = 'Toggle icon';
const captionSlot = 'Image caption';
const imageSrcValue = 'visitscotland';

const factoryShallowMount = (propsData) => shallowMount(VsImageWithCaption, {
    propsData: {
        imageSrc: imageSrcValue,
        ...propsData,
    },
    slots: {
        'toggle-icon': toggleIconSlot,
        'img-caption': captionSlot,
        default: defaultSlotText,
    },
});

describe('VsImageWithCaption', () => {
    it('should render a component with the data-test attribute `vs-image-with-caption`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-image-with-caption');
    });

    it('hides caption by default', async() => {
        const wrapper = factoryShallowMount();
        const captionWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__caption-wrapper');

        expect(captionWrapper.classes('d-block')).toBe(false);
    });

    describe(':props', () => {
        it('should set correct class if `closedDefaultCaption` is true', () => {
            const wrapper = factoryShallowMount({
                closedDefaultCaption: true,
            });
            const imageWithCaption = wrapper.find('[data-test="vs-image-with-caption"]');

            expect(imageWithCaption.classes('vs-image-with-caption--closed-default')).toBe(true);
        });

        it('should set correct classes if `isHeroImage` is true', () => {
            const wrapper = factoryShallowMount({
                isHeroImage: true,
            });
            const imageWithCaption = wrapper.find('[data-test="vs-image-with-caption"]');
            const captionWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__caption-wrapper');

            expect(imageWithCaption.classes('vs-image-with-caption--hero')).toBe(true);
            expect(captionWrapper.classes('container')).toBe(true);
        });

        it('should set correct classes if `mobileOverlap` is true', () => {
            const wrapper = factoryShallowMount({
                mobileOverlap: true,
            });
            const imageWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__image-wrapper');

            expect(imageWrapper.classes('vs-image-with-caption--overlapped')).toBe(true);
        });

        it('should render correct `toggleButtonText` on the toggle button', () => {
            const wrapper = factoryShallowMount({
                toggleButtonText: 'Toggle caption',
            });
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]')
                .find('.vs-image-with-caption__toggle-caption-btn')
                .find('.sr-only');

            expect(toggleCaptionBtn.text()).toBe('Toggle caption');
        });

        it('should set correct ID for aria controls with `imageSrc`', () => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__toggle-caption-btn');
            const captionWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__caption-wrapper');

            expect(toggleCaptionBtn.attributes('aria-controls')).toBe(`image_${imageSrcValue}`);
            expect(captionWrapper.attributes('id')).toBe(`image_${imageSrcValue}`);
        });
    });

    describe(':slots', () => {
        it('renders content in the `default` slot', () => {
            const wrapper = factoryShallowMount();
            const imageWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__image-wrapper');

            expect(imageWrapper.text()).toContain(defaultSlotText);
        });

        it('renders content in the `toggleIcon` slot', () => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__toggle-caption-btn');

            expect(toggleCaptionBtn.text()).toContain(toggleIconSlot);
        });

        it('renders content in the `imgCaption` slot', () => {
            const wrapper = factoryShallowMount();
            const captionWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__caption-wrapper');

            expect(captionWrapper.text()).toContain(captionSlot);
        });
    });

    describe(':methods', () => {
        it(':toggleCaption - caption is shown when toggle button is click', async() => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__toggle-caption-btn');
            const captionWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__caption-wrapper');
            await toggleCaptionBtn.trigger('click');

            expect(toggleCaptionBtn.attributes('aria-expanded')).toBe('true');
            expect(captionWrapper.classes('d-block')).toBe(true);
        });

        it(':toggleCaption - caption is hidden when toggle button is click again', async() => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__toggle-caption-btn');
            const captionWrapper = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__caption-wrapper');
            await toggleCaptionBtn.trigger('click');
            await toggleCaptionBtn.trigger('click');

            expect(toggleCaptionBtn.attributes('aria-expanded')).toBe('false');
            expect(captionWrapper.classes('d-block')).toBe(false);
        });

        it(':toggleCaption - icon is updated when the caption is toggled on', async() => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__toggle-caption-btn');
            await toggleCaptionBtn.trigger('click');

            expect(toggleCaptionBtn.text()).toContain('');
            expect(wrapper.find('vsicon-stub').attributes('name')).toBe('close-circle');
        });

        it(':toggleCaption - icon is updated when the caption is toggled off', async() => {
            const wrapper = factoryShallowMount();
            const toggleCaptionBtn = wrapper.find('[data-test="vs-image-with-caption"]').find('.vs-image-with-caption__toggle-caption-btn');
            await toggleCaptionBtn.trigger('click');
            await toggleCaptionBtn.trigger('click');

            expect(toggleCaptionBtn.text()).toContain(toggleIconSlot);
            expect(wrapper.find('vsicon-stub').exists()).toBe(false);
        });
    });
});
