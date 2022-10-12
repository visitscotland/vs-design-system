import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperDetail from '../MainMapWrapperDetail';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperDetail, {
    propsData: {
        contentData: {
            properties: {
                category: {
                    id: 'cities',
                    label: 'Cities',
                },
                title: 'Aberdeen',
                id: 'aberdeen',
            },
        },
    },
});

describe('VsMainMapWrapperCategory', () => {
    it('should render an element with the `vs-main-map-wrapper-detail` data-test attribute', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper-detail');
    });

    describe(':props', () => {

    });
});
