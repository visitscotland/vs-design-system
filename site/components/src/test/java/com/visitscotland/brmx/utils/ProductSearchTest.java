package com.visitscotland.brmx.utils;

import static com.visitscotland.brmx.utils.ProductSearchBuilder.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class ProductSearchTest {

    private final static String DEFAULT_LOCATION = "Edinburgh";

    //TODO when LocationLoader is not available

    @Test(expected = Exception.class)
    public void noQuery() {
        new ProductSearchBuilder().build();
        Assert.fail("An exception is expected when no query is defined");
    }

    @Test
    public void basic() {
        String url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .build();

        Assert.assertTrue("A basic URL couldn't be build", url != null && !url.isEmpty());
        validate(url);
    }

    @Test()
    public void productSearch() {
        final ProductType TYPE = ProductType.ACCOMMODATION;
        String url = new ProductSearchBuilder().productType(TYPE.type).build();

        Assert.assertTrue(
                String.format("The Generated URL is expected to contain " + PRODUCT_SEARCH + " (%s) ", TYPE.path, TYPE.type, url),
                url.contains(String.format(PRODUCT_SEARCH, TYPE.path, TYPE.type)));
        validate(url);
    }

    @Test()
    public void productSearchAccommodation() {
        final ProductType TYPE = ProductType.ACCOMMODATION;
        String url = new ProductSearchBuilder().productType(TYPE.type).build();

        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s (%s) ", NO_AVAILABILITY_PARAM, url),
                url.contains(NO_AVAILABILITY_PARAM));
        validate(url);
    }

    @Test(expected = Exception.class)
    public void productSearchWrongType() {
        final String TYPE = "test";
        new ProductSearchBuilder().productType(TYPE).build();

        Assert.fail(String.format("An invalid type (%s) it is not supposed to work", TYPE));
    }

    private void checkLocationAvailability() {
        if (LocationLoader.getLocation(DEFAULT_LOCATION, null) == null) {
            Assert.fail("LocationLoader might not be working or the location " + DEFAULT_LOCATION + " no longer exists");
        }
    }

    @Test
    public void location() {
        final String LOCATION = "Edinburgh";

        checkLocationAvailability();

        String url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .location(LOCATION)
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=%s and %s={some id} (%s) ", LOCATION_NAME, LOCATION, LOCATION_PLACE, url),
                url.contains(LOCATION_NAME + "=" + LOCATION) && url.contains(LOCATION_PLACE + "="));
        validate(url);
    }

    @Test
    public void locationPolygon() {
        final String LOCATION = "The Highlands";

        checkLocationAvailability();

        String url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .location(LOCATION)
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain the parameters '%s' and '%s' (%s) ", LOCATION_NAME, LOCATION_POLYGON, url),
                url.contains(LOCATION_NAME + "=") && url.contains(LOCATION_POLYGON + "="));
        validate(url);
    }

    @Test
    //TODO localtion by languageTag
    public void locationTranslation() {
        final String LOCATION = "Edinburgh";

        checkLocationAvailability();

        String url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .location(LOCATION)
                .locale(Locale.forLanguageTag("es-es"))
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=Edimburgo (%s) ", LOCATION_NAME, url),
                url.contains(LOCATION_NAME + "=Edimburgo"));
        validate(url);
    }

    @Test
    public void category() {
        String url;

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .category("cat1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=cat1 (%s) ", CATEGORY, url),
                url.contains(CATEGORY + "=cat1"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .category("cat1")
                .category("cat2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two categories (%s) ", url),
                url.contains(CATEGORY + "=cat1") && url.contains(CATEGORY + "=cat2"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .category("")
                .category(null)
                .category(" \t \n ")
                .category("cat1&cat2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no categories (%s) ", url),
                !url.contains(CATEGORY + "="));
        validate(url);
    }

    @Test
    public void awards() {
        String url;

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .award("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", AWARD, url),
                url.contains(AWARD + "=1"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .award("1")
                .award("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two awards (%s) ", url),
                url.contains(AWARD + "=1") && url.contains(AWARD + "=2"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .award("")
                .award(null)
                .award(" \t \n ")
                .award("1&2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no awards (%s) ", url),
                !url.contains(AWARD + "="));
        validate(url);

    }

    @Test
    public void facilities() {
        String url;

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .facility("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", FACILITY, url),
                url.contains(FACILITY + "=1"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .facility("1")
                .facility("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two facilities(%s) ", url),
                url.contains(FACILITY + "=1") && url.contains(FACILITY + "=2"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .facility("")
                .facility(null)
                .facility(" \t \n ")
                .facility("1&2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no facilities (%s) ", url),
                !url.contains(FACILITY + "="));
        validate(url);

    }

    @Test
    public void rating() {
        String url;

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .rating("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", RATING, url),
                url.contains(RATING + "=1"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .rating("1")
                .rating("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two facilities(%s) ", url),
                url.contains(RATING + "=1") && url.contains(RATING + "=2"));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .rating("")
                .rating(null)
                .rating(" \t \n ")
                .rating("1&2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no facilities (%s) ", url),
                !url.contains(RATING + "="));
        validate(url);

    }

    @Test
    public void order() {
        String url;

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .sortBy("DISTANCE")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to have an order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .sortBy("NONE")
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .sortBy("SOMETHING THAT DOES NOT EXISTS")
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);

        url = new ProductSearchBuilder().productType(ProductType.ACCOMMODATION.type)
                .sortBy(null)
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);


    }

    @Test
    public void proximity() {
        //TODO
    }

    @Test
    public void coordinates() {
        //TODO
    }

    @Test
    public void fromProductSearch() {
        //TODO Mock ProductSearchObject
    }

    //TODO Create test-utils?
    private void validate(String url) {
        Assert.assertEquals(String.format("Only one question mark is expected (%s)", url),
                1, url.chars().filter(ch -> ch == '?').count()
        );
        Assert.assertTrue(String.format("The url should starts with http[s] or / (%s)", url),
                url.startsWith("/") || url.startsWith("http")
        );
        Assert.assertFalse(String.format("The url should not contain spaces (%s)", url),
                url.contains(" ")
        );
    }
}
