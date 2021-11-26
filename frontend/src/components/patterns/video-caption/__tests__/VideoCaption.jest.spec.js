import { shallowMount } from '@vue/test-utils';
import VsVideoCaption from '../VideoCaption';

const videoTitleSlot = 'Video title';
const videoDurationSlot = '3 minute video';
const alertMsgSlot = 'This is a no-js alert';
const buttonText = 'Button text';

const factoryShallowMount = (propsData) => shallowMount(VsVideoCaption, {
    slots: {
        'video-title': videoTitleSlot,
        'video-duration': videoDurationSlot,
        'video-alert': alertMsgSlot,
    },
    propsData: {
        videoBtnText: buttonText,
        withToggleBtn: true,
        ...propsData,
    },
});

describe('VsVideoCaption', () => {
    it('should render a component with the data-test attribute `video-caption`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('video-caption');
    });

    describe(':slots', () => {
        it('should render the video title slot content', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(videoTitleSlot);
        });

        it('should render the video length slot content', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(videoDurationSlot);
        });

        it('should render the video alert message slot content', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.html()).toContain(alertMsgSlot);
        });
    });

    describe(':props', () => {
        it('should populate button text with the `videoBtnText` prop', () => {
            const wrapper = factoryShallowMount();
            const playButton = wrapper.find('.vs-video-caption__button');

            expect(playButton.text()).toBe(buttonText);
        });

        it('should include a toggle button if `withToggleBtn` prop is true', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.find('vstogglebutton-stub').exists()).toBe(true);
        });

        it('should not include a toggle button if `withToggleBtn` prop is false', () => {
            const wrapper = factoryShallowMount();
            wrapper.setProps({
                withToggleBtn: false,
            });

            expect(wrapper.find('vstogglebutton-stub').exists()).toBe(true);
        });
    });

    describe(':methods', () => {
        it('emits `toggleAction` when clicked', async() => {
            const wrapper = factoryShallowMount();
            wrapper.vm.emitToggle();
            await wrapper.vm.$nextTick();

            expect(wrapper.emitted().toggleAction).toBeTruthy();
        });
    });
});
