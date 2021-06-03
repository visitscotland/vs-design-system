import { shallowMount } from '@vue/test-utils';
import VsCol from '@components/elements/layout/Col';
import VsArticle from '../Article';

const defaultSlotText = 'Article content';
const titleSlotText = 'Article title';
const introSlotText = 'Article intro';
const imgSlotText = 'Article img';

const factoryShallowMount = (propsData) => shallowMount(VsArticle, {
    propsData: {
        ...propsData,
    },
    slots: {
        default: defaultSlotText,
        vsArticleImg: imgSlotText,
        vsArticleTitle: titleSlotText,
        vsArticleIntro: introSlotText,
    },
});

let wrapper;
beforeEach(() => {
    wrapper = factoryShallowMount();
});

describe('VsArticle', () => {
    it('should render a component with the data-test attribute `vs-article`', () => {
        expect(wrapper.attributes('data-test')).toBe('vs-article');
    });

    describe(':props', () => {
        it('Should render correct column class when isStandardPage is true', () => {
            wrapper = factoryShallowMount({
                isStandardPage: true,
            });
            const col = wrapper.findComponent(VsCol);
            expect(col.classes()).toContain('col-xxl-10', 'offset-xxl-1');
        });
    });

    describe(':slots', () => {
        it('renders content inserted in a vsArticleImg slot', () => {
            expect(wrapper.text()).toContain(imgSlotText);
        });

        it('renders content inserted in a vsArticleTitle slot', () => {
            expect(wrapper.text()).toContain(titleSlotText);
        });

        it('renders content inserted in a vsArticleIntro slot', () => {
            expect(wrapper.text()).toContain(introSlotText);
        });

        it('renders content inserted in the default slot', () => {
            expect(wrapper.text()).toContain(defaultSlotText);
        });
    });
});
