import { shallowMount } from '@vue/test-utils';
import VsVideoCaption from '../VideoCaption';

const videoTitleSlot = 'Video title';
const videoDurationSlot = '3 minute video';
const buttonText = 'Button text';

const factoryShallowMount = () => shallowMount(VsVideoCaption, {
    slots: {
        'video-title': videoTitleSlot,
        'video-duration': videoDurationSlot,
    },
    propsData: {
        videoBtnText: buttonText,
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
    });

    describe(':props', () => {
        it('should populate button text with the `videoBtnText` prop', () => {
            const wrapper = factoryShallowMount();
            const playButton = wrapper.find('.vs-video-caption__button');

            expect(playButton.text()).toBe(buttonText);
        });
    });
});
