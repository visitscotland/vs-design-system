import { mount } from '@vue/test-utils';
import { BBreadcrumb } from 'bootstrap-vue';

import VsBreadcrumb from './Breadcrumb';

const wrapper = mount(VsBreadcrumb);

describe('VsBreadcrumb', () => {
    it('should render a <b-breadcrumb />', () => {
        const breadcrumb = wrapper.find(BBreadcrumb);
        expect(breadcrumb.exists()).toBe(true);
    });
});
