import { shallowMount } from '@vue/test-utils';
import VsVideo from '../Video';

const videoId = 'C0DPdy98e4c';

const factoryShallowMount = (propsData) => shallowMount(VsVideo, {
    propsData: {
        videoId,
        showDuration: true,
        ...propsData,
    },
    computed: {
        player() {
            return {
                getDuration() {
                    // return fake video lenth of 3 mins 30 secs
                    return Promise.resolve(210);
                },
            };
        },
    },
});

describe('VsVideo', () => {
    it('should render a div with the class `vs-video`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.find('div[data-test=vs-video]').exists()).toBe(true);
    });

    describe(':props', () => {
        it('should pass a videoId prop to the youtube component', () => {
            const wrapper = factoryShallowMount();

            expect(wrapper.find('youtube-stub').attributes('videoid')).toBe(videoId);
        });
    });

    describe(':data', () => {
        it('should not show the video duration if showDuration is set to false', () => {
            const wrapper = factoryShallowMount();

            wrapper.setData({
                showDuration: false,
            });

            expect(wrapper.find('div[data-test="vs-video-duration"]').exists()).toBe(false);
        });
    });

    describe(':methods', () => {
        it('should call the playVideo method when receiving emitted event', () => {
            const wrapper = factoryShallowMount();
            const mockPlayMethod = jest.fn();
            wrapper.vm.playVideo = mockPlayMethod;

            wrapper.vm.$root.$emit('video-controls', 'play');

            expect(mockPlayMethod).toHaveBeenCalled();
        });

        it('should call the pauseVideo method when receiving emitted event', () => {
            const wrapper = factoryShallowMount();
            const mockPauseMethod = jest.fn();
            wrapper.vm.pauseVideo = mockPauseMethod;

            wrapper.vm.$root.$emit('video-controls', 'pause');

            expect(mockPauseMethod).toHaveBeenCalled();
        });

        it('should return the formatted time in minutes and seconds', async() => {
            const wrapper = factoryShallowMount();
            await wrapper.vm.player.getDuration();

            expect(wrapper.vm.duration.minutes).toBe(3);
            expect(wrapper.vm.duration.seconds).toBe(30);
        });

        it('should not render the video duration if video duration is 0', () => {
            const wrapper = factoryShallowMount({
                computed: {
                    player() {
                        return {
                            getDuration() {
                                return Promise.resolve(0);
                            },
                        };
                    },
                },
            });

            expect(wrapper.find('p[data-test="vs-video-duration"]').exists()).toBe(false);
        });
    });
});
