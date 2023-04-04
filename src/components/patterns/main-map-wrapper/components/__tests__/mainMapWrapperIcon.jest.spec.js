import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperIcon from '../MainMapWrapperIcon';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperIcon, {
    propsData: {
        id: 'cities',
    },
});

describe('VsMainMapWrapperIcon', () => {
    it('should render a component with the data-test attribute `vs-main-map-marker-icon`', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-main-map-marker-icon');
    });

    describe(':props', () => {
        it('should render a makrer icon with a class corresponding to the id prop', () => {
            const wrapper = factoryShallowMount();
            const markerStub = wrapper.findAll('vsicon-stub').at(0);

            expect(markerStub.classes()).toContain('vs-main-map-marker-icon__marker--cities');
        });

        it('should add an outline class to the marker if the `isHovered` prop is true', async() => {
            const wrapper = factoryShallowMount();
            const markerStub = wrapper.findAll('vsicon-stub').at(0);

            wrapper.setProps({
                isHovered: true,
            });

            await wrapper.vm.$nextTick();

            expect(markerStub.classes()).toContain('vs-main-map-marker-icon__marker--outline');
        });

        it('should add a map marker class if the `isMapMarker` prop is true', async() => {
            const wrapper = factoryShallowMount();

            wrapper.setProps({
                isMapMarker: true,
            });

            await wrapper.vm.$nextTick();

            expect(wrapper.classes()).toContain('vs-main-map-marker-icon--map-marker');
        });
    });
});
