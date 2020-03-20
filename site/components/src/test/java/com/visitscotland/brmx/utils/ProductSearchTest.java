package com.visitscotland.brmx.utils;

import static com.visitscotland.brmx.utils.ProductSearchBuilder.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class ProductSearchTest {

    private final static String DEFAULT_TYPE = "cate";

    //TODO when LocationLoader is not available

    @Test(expected = Exception.class)
    public void noProductType() {
        new ProductSearchBuilder().build();
        Assert.fail("An exception is expected when no query is defined");
    }

    @Test
    public void basic() {
        String url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .build();

        Assert.assertTrue("A basic URL couldn't be build", url != null && !url.isEmpty());
        validate(url);
    }

    @Test()
    public void productSearch() {

        String url = new ProductSearchBuilder().productTypes("cate").build();

        Assert.assertTrue(
                String.format("The Generated URL is expected to contain " + PRODUCT_SEARCH + " (%s) ", Properties.VS_DMS_SERVICE, PATH_FOOD_DRINK, url),
                url.contains(String.format(PRODUCT_SEARCH, Properties.VS_DMS_SERVICE, PATH_FOOD_DRINK)));
        validate(url);
    }

    @Test()
    public void productSearchAccommodation() {
        String url = new ProductSearchBuilder().productTypes("acco").build();

        Assert.assertTrue(
                String.format("The Generated URL is expected to contain the parameter %s (%s) ", AVAILABILITY, url),
                url.contains(AVAILABILITY));
        validate(url);
    }

    @Test
    public void productSearchUndefinedType() {
        final String TYPE = "test";
        String url =  new ProductSearchBuilder().productTypes(TYPE).build();

        Assert.assertTrue(
                String.format("The Generated URL is expected to contain the url path %s ", PATH_DEFAULT),
                url.contains(PATH_DEFAULT));
    }

    private void checkLocationAvailability() {
        final String LOCATION = "Edinburgh";

        if (LocationLoader.getLocation(LOCATION, null) == null) {
            Assert.fail("LocationLoader might not be working or the location " + LOCATION + " no longer exists");
        }
    }

    @Test
    public void location() {
        final String LOCATION = "Edinburgh";

        checkLocationAvailability();

        String url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        String url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        String url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .category("cat1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=cat1 (%s) ", CATEGORY, url),
                url.contains(CATEGORY + "=cat1"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .category("cat1")
                .category("cat2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two categories (%s) ", url),
                url.contains(CATEGORY + "=cat1") && url.contains(CATEGORY + "=cat2"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .award("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", AWARD, url),
                url.contains(AWARD + "=1"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .award("1")
                .award("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two awards (%s) ", url),
                url.contains(AWARD + "=1") && url.contains(AWARD + "=2"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .facility("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", FACILITY, url),
                url.contains(FACILITY + "=1"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .facility("1")
                .facility("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two facilities(%s) ", url),
                url.contains(FACILITY + "=1") && url.contains(FACILITY + "=2"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .rating("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", RATING, url),
                url.contains(RATING + "=1"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .rating("1")
                .rating("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two facilities(%s) ", url),
                url.contains(RATING + "=1") && url.contains(RATING + "=2"));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
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

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .sortBy("DISTANCE")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to have an order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .sortBy("NONE")
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .sortBy("SOMETHING THAT DOES NOT EXISTS")
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .sortBy(null)
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);


    }

    @Test
    public void proximity() {
        final String LOCATION = "Edinburgh";
        final Integer PROXIMITY = 6;


        String url;
        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .location(LOCATION)
                .proximity(PROXIMITY)
                .sortBy(null)
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(PROXIMITY_LOCATION + "=" + PROXIMITY));

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .location(LOCATION)
                .proximity(null)
                .sortBy(null)
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(PROXIMITY_LOCATION + "=" + DEFAULT_PROXIMITY));

        url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .location(LOCATION)
                .proximity(0)
                .sortBy(null)
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(PROXIMITY_LOCATION + "=" + DEFAULT_PROXIMITY));

        validate(url);
    }

    @Test
    public void coordinates() {
        String url = new ProductSearchBuilder().productTypes(DEFAULT_TYPE)
                .sortBy(null)
                .build();
        Assert.assertFalse(
                String.format("The Generated URL is expected to have no order (%s) ", url),
                url.contains(ORDER + "="));
        validate(url);
    }

    @Test
    public void fromProductSearch() {
        //TODO Mock ProductSearchObject
    }

    //TODO Create test-utils?
    private void validate(String url) {
        //The following is true just for Product Search
        //At least one question mark
        Assert.assertTrue(String.format("The url should not contain spaces (%s)", url),
                url.contains("?")
        );
        //No more than one question mark
        Assert.assertEquals(String.format("Only one question mark is expected (%s)", url),
                1, url.chars().filter(ch -> ch == '?').count()
        );
        //Starts with http, https (fully qualified) or  / (domain related)
        Assert.assertTrue(String.format("The url should starts with http[s] or / (%s)", url),
                url.startsWith("/") || url.startsWith("http")
        );
        //No space character in it
        Assert.assertFalse(String.format("The url should not contain spaces (%s)", url),
                url.contains(" ")
        );

    }
}
