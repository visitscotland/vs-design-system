import { shallowMount, mount } from '@vue/test-utils';
import VsGlobalMenuLanguageItem from '../GlobalMenuLanguageItem';

const factoryShallowMount = (propsData) => shallowMount(VsGlobalMenuLanguageItem, {
    slots: {
        default: 'Button text',
    },
    propsData: {
        ...propsData,
    },
});

describe('VsGlobalMenuLanguageItem renders', () => {
    it('should render a dropdownitem', () => {
        const wrapper = factoryShallowMount();

        expect(wrapper.element.tagName).toBe('VSDROPDOWNITEM-STUB');
    });
});

describe(':props', () => {
    it('should render language name', () => {
        const wrapper = factoryShallowMount({
            languageName: 'Spanish',
        });

        expect(wrapper.find('vsdropdownitem-stub').text()).toEqual('Spanish');
    });

    it('should have href props language link', () => {
        const wrapper = mount(VsGlobalMenuLanguageItem, {
            propsData: {
                languageLink: '/site/es',
            },
        });

        expect(wrapper.attributes('href')).toBe('/site/es');
    });
});
