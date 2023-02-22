import { shallowMount } from '@vue/test-utils';

import VsLoadingSpinner from '../LoadingSpinner';

const factoryShallowMount = () => shallowMount(VsLoadingSpinner);

describe('VsLoadingSpinner', () => {
    it('should render loading spinner', () => {
        const wrapper = factoryShallowMount();

        const LoadingSpinner = wrapper.find('div[data-test=vs-loading-spinner]');

        expect(LoadingSpinner.exists()).toBe(true);
    });
});
