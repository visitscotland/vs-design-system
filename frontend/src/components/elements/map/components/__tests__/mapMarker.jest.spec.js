import { shallowMount } from '@vue/test-utils';

import VsMapMarker from '../MapMarker';
import markerExample from './data/markerData.json';

describe('VsMapMarker', () => {
    it('should render a map component with the data-test attribute `vs-map`', () => {
        const wrapper = shallowMount(VsMapMarker, {
            propsData: {
                feature: markerExample,
            },
        });

        expect(wrapper.attributes('data-test')).toBe('vs-map-marker');
    });
});
