import { shallowMount } from '@vue/test-utils';
import VsVideo from '../Video';

const videoId = 'C0DPdy98e4c';
const singleMinuteDescriptor = '%s minute';
const pluralMinuteDescriptor = '%s minutos';

const factoryShallowMount = (propsData, compData) => shallowMount(VsVideo, {
    propsData: {
        videoId,
        showDuration: true,
        singleMinuteDescriptor,
        pluralMinuteDescriptor,
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
        ...compData,
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
        it('should not show the video duration if showDuration is set to false', async() => {
            const wrapper = factoryShallowMount();

            await wrapper.setData({
                showDuration: false,
            });

            expect(wrapper.find('[data-test="vs-video-duration"]').exists()).toBe(true);
        });

        it('should show a roundedDuration that rounds up, if the duration is 0 minutes and < 30 seconds', async() => {
            // a 25 second video, which should round down to 1 minute
            const wrapper = factoryShallowMount(null, {
                player() {
                    return {
                        getDuration() {
                            return Promise.resolve(25);
                        },
                    };
                },
            });

            await wrapper.setData({
                showDuration: true,
            });

            expect(wrapper.find('[data-test="vs-video-rounded-duration"]').text()).toContain('1');
        });

        it('should show a roundedDuration that rounds down, if the duration is x minutes and < 30 seconds', async() => {
            // a 1 minute 20 second video, which should round down to 1 minute
            const wrapper = factoryShallowMount(null, {
                player() {
                    return {
                        getDuration() {
                            return Promise.resolve(80);
                        },
                    };
                },
            });

            await wrapper.setData({
                showDuration: true,
            });

            expect(wrapper.find('[data-test="vs-video-rounded-duration"]').text()).toContain('1');
        });

        it('should show a roundedDuration that rounds up, if the duration is x minutes and >= 30 seconds', async() => {
            // a 1 minute 30 second video, which should round up to 2 minutes
            const wrapper = factoryShallowMount(null, {
                player() {
                    return {
                        getDuration() {
                            return Promise.resolve(90);
                        },
                    };
                },
            });

            await wrapper.setData({
                showDuration: true,
            });

            expect(wrapper.find('[data-test="vs-video-rounded-duration"]').text()).toContain('2');
        });

        it('should show render the singleMinuteDescriptor for a 1 minute video', async() => {
            // a 1 minute 20 second video, which should round down to 1 minute
            const wrapper = factoryShallowMount(null, {
                player() {
                    return {
                        getDuration() {
                            return Promise.resolve(80);
                        },
                    };
                },
            });

            await wrapper.setData({
                showDuration: true,
            });

            expect(wrapper.find('[data-test="vs-video-rounded-duration"]').text())
                .toContain(singleMinuteDescriptor.replace('%s', '1'));
        });

        it('should show render the pluralMinuteDiscriptor for a multi minute video', async() => {
            // a 3 minute 40 second video, which should round up to 4 minute
            const wrapper = factoryShallowMount(null, {
                player() {
                    return {
                        getDuration() {
                            return Promise.resolve(220);
                        },
                    };
                },
            });

            await wrapper.setData({
                showDuration: true,
            });

            expect(wrapper.find('[data-test="vs-video-rounded-duration"]').text())
                .toContain(pluralMinuteDescriptor.replace('%s', '4'));
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
            const wrapper = factoryShallowMount(null, {
                player() {
                    return {
                        getDuration() {
                            return Promise.resolve(0);
                        },
                    };
                },
            });

            expect(wrapper.find('p[data-test="vs-video-duration"]').exists()).toBe(false);
        });
    });
});
