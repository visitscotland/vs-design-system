import { shallowMount } from '@vue/test-utils';
import VsMainMapWrapperListItem from '../MainMapWrapperListItem';

const factoryShallowMount = () => shallowMount(VsMainMapWrapperListItem, {
    slots: {
        default: 'Button text',
    },
    propsData: {
        itemName: 'Aberdeen',
        itemId: 'aberdeen',
    },
});

describe('VsMainMapWrapperListItem', () => {
    it('should render a component with the data-test attribute `vs-main-map-wrapper-list-item`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.attributes('data-test')).toBe('vs-main-map-wrapper-list-item');
    });

    describe(':slots', () => {
        it('should render the default slot as text', () => {
            const wrapper = factoryShallowMount();
            expect(wrapper.text()).toBe('Button text');
        });
    });

    describe(':methods', () => {
        it('should emit `show-item-detail` with a value of the `itemId` prop when the button is clicked', async() => {
            const wrapper = factoryShallowMount();
            const mockMethod = jest.spyOn(wrapper.vm, 'showItemDetail');
            await wrapper.trigger('click');

            expect(mockMethod).toHaveBeenCalledWith('aberdeen');
        });
    });
});
