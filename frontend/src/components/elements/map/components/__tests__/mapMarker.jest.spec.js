import { shallowMount } from '@vue/test-utils';

import VsMapMarker from '../MapMarker';
import markerExample from './data/markerData.json';

describe('VsMapMarker', () => {
    it('should render a map component with the data-test attribute `vs-map-marker`', () => {
        const wrapper = shallowMount(VsMapMarker, {
            propsData: {
                feature: markerExample,
            },
            computed: {
                isActive() {
                    return true;
                },
                highlightedPlace() {
                    return 'highlighted-id';
                },
            },
        });

        expect(wrapper.attributes('data-test')).toBe('vs-map-marker');
    });

    describe(':methods', () => {
        it('should fire the `handleClick` method when clicked', async() => {
            const mockMethod = jest.fn();
            const wrapper = shallowMount(VsMapMarker, {
                propsData: {
                    feature: markerExample,
                },
                computed: {
                    isActive() {
                        return true;
                    },
                    highlightedPlace() {
                        return 'highlighted-id';
                    },
                },
                methods: {
                    handleClick: mockMethod,
                },
            });

            await wrapper.trigger('click');
            expect(mockMethod).toHaveBeenCalled();
        });
    });
});
