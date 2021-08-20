import { mount } from '@vue/test-utils';
import VsCannedSearchProductCard from '../CannedSearchProductCard';

const imgSrc = 'https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm';
const imgAlt = 'Arden House';
const title = 'Arden House';
const location = 'Callander, Loch Lomond, The Trossachs';
const description = 'Test product description';
const detailLink = {
    link: '/info/accommodation/arden-house-p572561',
    label: 'View Details',
    type: 'internal',
};
const websiteLink = {
    link: 'http://www.ardenhouse.org.uk',
    label: 'Visit website',
    type: 'external',
};
const priceIntro = 'Price from';
const price = '£110';
const priceOutro = 'Per room per night (breakfast inc.)';
const badgeOne = 'B and B';
const badgeTwo = 'Offer';
const badgeThree = 'We are open';

const slideIndex = '0';

const starSlotContent = 'This is a star rating';
const catSlotContent = 'This is a list of categories';
const logoSlotContent = 'This is a list of logos';

const factoryMount = (propsData) => mount(VsCannedSearchProductCard, {
    propsData: {
        imgSrc,
        imgAlt,
        title,
        location,
        description,
        detailLink,
        websiteLink,
        priceIntro,
        price,
        priceOutro,
        badgeOne,
        badgeTwo,
        badgeThree,
        slideIndex,
        ...propsData,
    },
    slots: {
        vsCannedSearchStarRating: starSlotContent,
        vsCannedSearchCategories: catSlotContent,
        vsCannedSearchLogos: logoSlotContent,
    },
    provide: () => ({
        slideCols: {
            xs: '12',
            sm: '6',
            md: '4',
            lg: '3',
        },
        visibleSlides: [4, 5, 6],
    }),
});

let wrapper;
beforeEach(() => {
    wrapper = factoryMount();
});

describe('VsCannedSearchProductCard', () => {
    describe(':props', () => {
        it('should render a component with the data-test attribute `vs-product-card`', () => {
            expect(wrapper.find('[data-test="vs-product-card"]').exists()).toBe(true);
        });

        describe(':img', () => {
            it('should render a `vs-img` with the `src` provided in `imgSrc`', () => {
                const mainProdImg = wrapper.find('[data-test="vs-product-card__img"]');

                expect(mainProdImg.props().src).toBe(imgSrc);
            });

            it('should render a `vs-img` with the `alt` provided in `imgAlt`', () => {
                const mainProdImg = wrapper.find('[data-test="vs-product-card__img"]');

                expect(mainProdImg.props().alt).toBe(imgAlt);
            });
        });

        it('should render the content of the `title` property as a vs-heading', () => {
            const prodTitle = wrapper.find('[data-test="vs-product-card__title"]');

            expect(prodTitle.html()).toContain(title);
        });

        describe(':link', () => {
            it('should render a `vs-link` with the `href` provided in `detailLink.link`', () => {
                const prodLink = wrapper.find('[data-test="vs-product-card__link"]');

                expect(prodLink.props().href).toBe(detailLink.link);
            });

            it('should render a `vs-link` with the `type` provided in `detailLink.type`', () => {
                const prodLink = wrapper.find('[data-test="vs-product-card__link"]');

                expect(prodLink.props().type).toBe(detailLink.type);
            });

            it('should render the content of the `detailLink.label` property', () => {
                expect(wrapper.html()).toContain(detailLink.label);
            });
        });

        it('should render the content of the `location` property as a vs-heading', () => {
            const prodTitle = wrapper.find('[data-test="vs-product-card__location"]');

            expect(prodTitle.html()).toContain(location);
        });

        it('should render the content of the `description` property', () => {
            expect(wrapper.html()).toContain(description);
        });

        describe(':websiteLink', () => {
            it('should render a `vs-link` with the `href` provided in `websiteLink.link`', () => {
                const prodLink = wrapper.find('[data-test="vs-product-card__website-link"]');

                expect(prodLink.props().href).toBe(websiteLink.link);
            });

            it('should render a `vs-link` with the `type` provided in `websiteLink.type`', () => {
                const prodLink = wrapper.find('[data-test="vs-product-card__website-link"]');

                expect(prodLink.props().type).toBe(websiteLink.type);
            });

            it('should render the content of the `detailLink.label` property', () => {
                expect(wrapper.html()).toContain(websiteLink.label);
            });
        });

        it('should render the content of the `priceIntro` property', () => {
            expect(wrapper.html()).toContain(priceIntro);
        });

        it('should render the content of the `price` property', () => {
            expect(wrapper.html()).toContain(price);
        });

        it('should render the content of the `priceOutro` property', () => {
            expect(wrapper.html()).toContain(priceOutro);
        });

        it('should render the content of the `badgeOne` property', () => {
            expect(wrapper.html()).toContain(badgeOne);
        });

        it('should render the content of the `badgeTwo` property', () => {
            expect(wrapper.html()).toContain(badgeTwo);
        });

        it('should render the content of the `badgeThree` property', () => {
            expect(wrapper.html()).toContain(badgeThree);
        });
    });

    describe(':slots', () => {
        it('should render the content of the `vsCannedSearchStarRating` slot', () => {
            expect(wrapper.html()).toContain(starSlotContent);
        });

        it('should render the content of the `vsCannedSearchCategories` slot', () => {
            expect(wrapper.html()).toContain(catSlotContent);
        });

        it('should render the content of the `vsCannedSearchLogos` slot', () => {
            expect(wrapper.html()).toContain(logoSlotContent);
        });
    });
});
