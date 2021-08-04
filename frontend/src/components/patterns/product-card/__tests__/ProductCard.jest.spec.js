import { mount } from '@vue/test-utils';
import VsProductCard from '../ProductCard';

const imgSrc = 'https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm',
    imgAlt = 'Arden House',
    title = 'Arden House',
    location = 'Callander, Loch Lomond, The Trossachs',
    stars = {
        min: 4,
        max: 4,
        gold: false
    },
    categories = ["Rural", "Village Location", "Mountains area"],
    description = "Test product description",
    detailLink = {
        link: '/info/accommodation/arden-house-p572561',
        label: 'View Details',
        type: 'internal'
    },
    websiteLink = {
        link: "http://www.ardenhouse.org.uk",
        label: 'Visit website',
        type: 'external'
    },
    priceIntro = "Price from",
    price = "£110",
    priceOutro = "Per room per night (breakfast inc.)",
    badgeOne = "B and B",
    badgeTwo = "Offer",
    badgeThree = "We are open",
    goodToGoLogo = "Good to go",
    safeTravelsLogo = "Safe travels",
    awards = [
        {
           "id":"qatasteourbest",
           "name":"Taste Our Best",
           "image":"http://visitscotlandpreview.nmdemo.net/wsimgs/awards/TOB-updated-white-back_286148508_714060445.jpg"
        },
        {
          "id": "qag2s",
          "name": "Green Tourism Silver",
          "image": "http://visitscotlandpreview.nmdemo.net/wsimgs/awards/GT_silver_200x200_275519812.png",
          "type": "GREEN_TOURISM"
        }
    ];

const factoryMount = (propsData) => mount(VsProductCard, {
    propsData: {
        imgSrc: imgSrc,
        imgAlt: imgAlt,
        title: title,
        location: location, 
        stars: stars,
        categories: categories,
        description: description,
        detailLink: detailLink,
        websiteLink: websiteLink,
        priceIntro: priceIntro,
        price: price,
        priceOutro: priceOutro,
        badgeOne: badgeOne,
        badgeTwo: badgeTwo,
        badgeThree: badgeThree,
        goodToGoLogo: goodToGoLogo,
        safeTravelsLogo: safeTravelsLogo,
        awards: awards,
        ...propsData
    },
});

const originalWarn = console.warn.bind(console.warn)

beforeAll(() => {
    console.warn = (msg) => 
        !msg.toString().includes('tooltip - The provided target is no valid HTML element.') && originalWarn(msg)
})

afterAll(() => {
  console.warn = originalWarn
})

let wrapper;
beforeEach(() => {
    wrapper = factoryMount();
});

describe('VsProductCard', () => {
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

        describe(':stars', () => {
            it('should render a number of stars equal to `stars.min` if `stars.min` and `stars.max` are the same', async() => {
                const testStars = {
                    min: 4,
                    max: 4,
                    gold: false
                };

                wrapper.setProps({
                    stars: testStars,
                });
    
                await wrapper.vm.$nextTick();

                expect(wrapper.findAll('.vs-product-card__star').length).toBe(4);
            });

            it('should render a one star if `stars.min` and `stars.max` are different', async() => {
                const testStars = {
                    min: 2,
                    max: 4,
                    gold: false
                };

                wrapper.setProps({
                    stars: testStars,
                });
    
                await wrapper.vm.$nextTick();

                expect(wrapper.findAll('.vs-product-card__star').length).toBe(1);
            });

            it('should say `min-max` if `stars.min` and `stars.max` are different', async() => {
                const testStars = {
                    min: 2,
                    max: 4,
                    gold: false
                };

                wrapper.setProps({
                    stars: testStars,
                });
    
                await wrapper.vm.$nextTick();

                expect(wrapper.find('.vs-product-card__stars').html()).toContain('2-4');
            });

            it('should render purple stars if `stars.gold` is falsy', async() => {
                const testStars = {
                    min: 4,
                    max: 4,
                    gold: false
                };

                wrapper.setProps({
                    stars: testStars,
                });
    
                await wrapper.vm.$nextTick();

                const star = wrapper.find('.vs-product-card__star');

                expect(star.classes('vs-product-card__star--gold')).toBe(false);
            });

            it('should render gold stars if `stars.gold` is true', async() => {
                const testStars = {
                    min: 4,
                    max: 4,
                    gold: true
                };

                wrapper.setProps({
                    stars: testStars,
                });
    
                await wrapper.vm.$nextTick();

                const star = wrapper.find('.vs-product-card__star');

                expect(star.classes('vs-product-card__star--gold')).toBe(true);
            });
        });

        describe(':categories', () => {
            it('should render each item within the `categories` property', () => {
                const cardCategories = wrapper.find('[data-test="vs-product-card__categories"]'); 

                for (let x = 0; x < categories.length; x++) {
                    expect(cardCategories.html()).toContain(categories[x]);
                }
            });

            it('should not render additional `categories` beyond three', async() => {
                const testCats = ["a", "b", "c", "d", "e"];

                wrapper.setProps({
                    categories: testCats,
                });
    
                await wrapper.vm.$nextTick();

                const cardCategories = wrapper.find('[data-test="vs-product-card__categories"]'); 

                for (let x = 3; x < categories.length; x++) {
                    expect(cardCategories.html()).not.toContain(categories[x]);
                }
            });
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

        describe(':logos', () => {
            it('should render a vs-tooltip with the title provided in the `goodToGoLogo` property', () => {
                const tooltip = wrapper.find('[data-test="vs-product-card__good-to-go"]');

                expect(tooltip.props('title')).toBe(goodToGoLogo);
            });

            it('should not render the good to go vs-tooltip if no `goodToGoLogo` property is provided', async() => {
                const testGtG = "";;

                wrapper.setProps({
                    goodToGoLogo: testGtG,
                });
    
                await wrapper.vm.$nextTick();

                const tooltip = wrapper.find('[data-test="vs-product-card__good-to-go"]');

                expect(tooltip.exists()).toBe(false);
            });

            it('should render a vs-tooltip with the title provided in the `safeTravelsLogo` property', () => {
                const tooltip = wrapper.find('[data-test="vs-product-card__safe-travels"]');

                expect(tooltip.props('title')).toBe(safeTravelsLogo);
            });

            it('should not render the safe travels vs-tooltip if no `safeTravelsLogo` property is provided', async() => {
                const testSt = "";

                wrapper.setProps({
                    safeTravelsLogo: testSt,
                });
    
                await wrapper.vm.$nextTick();

                const tooltip = wrapper.find('[data-test="vs-product-card__safe-travels"]');

                expect(tooltip.exists()).toBe(false);
            });

            it('should render a logo for each entry in the `awards` property', () => {
                expect(wrapper.findAll('[data-test="vs-product-card__award-logo"]').length).toBe(awards.length);
            });
        });
    });
});
