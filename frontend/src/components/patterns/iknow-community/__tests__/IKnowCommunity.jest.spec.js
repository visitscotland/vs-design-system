import { shallowMount } from '@vue/test-utils';
import VsIknowCommunity from '../IknowCommunity';

const textContent = 'Some test';
const linksContent = 'Some links';
const tagsContent = 'Some tags';

const factoryShallowMount = () => shallowMount(VsIknowCommunity, {
    slots: {
        iknowCommunityText: textContent,
        iknowCommunityLink: linksContent,
        iknowCommunityTags: tagsContent,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsIknowCommunity', () => {
    it('should render a component with the data-test attribute `.vs-iknow-community`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-iknow-community');
    });

    describe(':slots', () => {
        it('renders content inserted into the `iknowCommunityText` slot', () => {
            expect(wrapper.text()).toContain(textContent);
        });

        it('renders content inserted into the `iknowCommunityLinks` slot', () => {
            expect(wrapper.text()).toContain(linksContent);
        });

        it('renders content inserted into the `iknowCommunityTags` slot', () => {
            expect(wrapper.text()).toContain(tagsContent);
        });
    });
});
