import { mount } from '@vue/test-utils';
import VsMegaNavFeaturedEvent from '../MegaNavFeaturedEvent';

const link = 'http://www.visitscotland.com';
const imgUrl = 'https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm';
const imgAlt = 'Test alt text';
const title = 'Autism Friendly Ancient Egyptian Family Workshop Day';
const location = 'South Nutshill Industrial Park';
const datesLabel = 'Dates from';
const dates = '4 Dec 2020';

const factoryMount = (slotData) => mount(VsMegaNavFeaturedEvent, {
    propsData: {
        link,
        imgUrl,
        imgAlt,
        title,
        location,
        datesLabel,
        dates,
    },
    ...slotData,
});

describe('VsMegaNavFeaturedEvent', () => {
    it('should render a component with the data-test attribute `vs-mega-nav-featured-event`', async() => {
        const wrapper = factoryMount();
        expect(wrapper.find('[data-test="vs-mega-nav-featured-event"]').exists()).toBe(true);
    });

    describe(':props', () => {
        it('should render a link with the link prop value as href', async() => {
            const wrapper = factoryMount({
                slots: {
                    vsFeaturedItemLink: 'Test featured item link text',
                },
            });
            expect(wrapper.find('a[href="http://www.visitscotland.com"]').exists()).toBe(true);
        });

        it('should render an image using the imgUrl prop', async() => {
            const wrapper = factoryMount();
            expect(wrapper.find('img').attributes('src')).toBe('https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm');
        });

        it('should render image alt text using the imgAlt prop', async() => {
            const wrapper = factoryMount();
            expect(wrapper.find('img').attributes('alt')).toBe(imgAlt);
        });

        it('should render the content of the `title` prop', () => {
            const wrapper = factoryMount();

            expect(wrapper.text()).toContain(title);
        });

        it('should render the content of the `location` prop', () => {
            const wrapper = factoryMount();

            expect(wrapper.text()).toContain(location);
        });

        it('should render the content of the `datesLabel` prop', () => {
            const wrapper = factoryMount();

            expect(wrapper.text()).toContain(datesLabel);
        });

        it('should render the content of the `dates` prop', () => {
            const wrapper = factoryMount();

            expect(wrapper.text()).toContain(dates);
        });
    });

    describe(':slots', () => {
        it('should render a link with the vsFeaturedEventLink value passed', async() => {
            const wrapper = factoryMount({
                slots: {
                    vsFeaturedEventLink: 'Test featured event link text',
                },
            });
            expect(wrapper.text()).toContain('Test featured event link text');
        });
    });
});
