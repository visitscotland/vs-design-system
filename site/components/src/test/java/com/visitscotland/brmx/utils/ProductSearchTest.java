package com.visitscotland.brmx.utils;

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
        String url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION).build();

        Assert.assertTrue("A basic URL couldn't be build",url != null && !url.isEmpty());
        validate(url);
    }

    @Test()
    public void productSearch() {
        final ProductSearchBuilder.ProductType TYPE = ProductSearchBuilder.ProductType.ACCOMMODATION;
        String url = new ProductSearchBuilder().createProductSearch(TYPE.type).location(DEFAULT_LOCATION).build();

        Assert.assertTrue(
                String.format("The Generated URL is expected to contain "+ProductSearchBuilder.PRODUCT_SEARCH+" (%s) ", TYPE.path, TYPE.type, url),
                url.contains(String.format(ProductSearchBuilder.PRODUCT_SEARCH, TYPE.path, TYPE.type)));
        validate(url);
    }

    @Test(expected = Exception.class)
    public void productSearchWrongType() {
        final String TYPE = "test";
        new ProductSearchBuilder().createProductSearch(TYPE).location(DEFAULT_LOCATION).build();

        Assert.fail(String.format("An invalid type (%s) it is not supposed to work", TYPE));
    }

    @Test
    //TODO localtion by languageTag
    public void location() {
        if (LocationLoader.getLocation("Edinburgh", null) == null){
            Assert.fail("LocationLoader might not be ");
        }

        String url;

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=Edinburgh (%s) ", ProductSearchBuilder.LOCATION, url),
                url.contains(ProductSearchBuilder.LOCATION+"=Edinburgh"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .locale(Locale.forLanguageTag("es-es"))
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=Edimburgo (%s) ", ProductSearchBuilder.LOCATION, url),
                url.contains(ProductSearchBuilder.LOCATION+"=Edimburgo"));
        validate(url);
    }

    @Test
    public void category(){
        String url;

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                        .category("cat1")
                        .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=cat1 (%s) ", ProductSearchBuilder.CATEGORY, url),
                url.contains(ProductSearchBuilder.CATEGORY+"=cat1"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .category("cat1")
                .category("cat2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two categories (%s) ", url),
                url.contains(ProductSearchBuilder.CATEGORY+"=cat1") && url.contains(ProductSearchBuilder.CATEGORY+"=cat2"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .category("")
                .category(null)
                .category(" \t \n ")
                .category("cat1&cat2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no categories (%s) ", url),
                !url.contains(ProductSearchBuilder.CATEGORY+"="));
        validate(url);
    }

    @Test
    public void awards(){
        String url;

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .award("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", ProductSearchBuilder.AWARD, url),
                url.contains(ProductSearchBuilder.AWARD+"=1"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .award("1")
                .award("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two awards (%s) ", url),
                url.contains(ProductSearchBuilder.AWARD+"=1") && url.contains(ProductSearchBuilder.AWARD+"=2"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .award("")
                .award(null)
                .award(" \t \n ")
                .award("1&2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no awards (%s) ", url),
                !url.contains(ProductSearchBuilder.AWARD+"="));
        validate(url);

    }

    @Test
    public void facilities(){
        String url;

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .facility("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", ProductSearchBuilder.FACILITY, url),
                url.contains(ProductSearchBuilder.FACILITY + "=1"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .facility("1")
                .facility("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two facilities(%s) ", url),
                url.contains(ProductSearchBuilder.FACILITY + "=1") && url.contains(ProductSearchBuilder.FACILITY + "=2"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .facility("")
                .facility(null)
                .facility(" \t \n ")
                .facility("1&2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no facilities (%s) ", url),
                !url.contains(ProductSearchBuilder.FACILITY + "="));
        validate(url);

    }

    @Test
    public void rating(){
        String url;

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .rating("1")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain %s=1 (%s) ", ProductSearchBuilder.RATING, url),
                url.contains(ProductSearchBuilder.RATING + "=1"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .rating("1")
                .rating("2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain two facilities(%s) ", url),
                url.contains(ProductSearchBuilder.RATING + "=1") && url.contains(ProductSearchBuilder.RATING + "=2"));
        validate(url);

        url = new ProductSearchBuilder().createProductSearch(ProductSearchBuilder.ProductType.ACCOMMODATION.type)
                .location(DEFAULT_LOCATION)
                .rating("")
                .rating(null)
                .rating(" \t \n ")
                .rating("1&2")
                .build();
        Assert.assertTrue(
                String.format("The Generated URL is expected to contain no facilities (%s) ", url),
                !url.contains(ProductSearchBuilder.RATING + "="));
        validate(url);

    }

    @Test
    public void proximity(){
        //TODO
    }

    @Test
    public void coordinates(){
        //TODO
    }

    @Test
    public void fromProductSearch(){
        //TODO Mock ProductSearchObject
    }

    //TODO Create test-utils?
    private void validate(String url){
        Assert.assertEquals(String.format("Only one question mark is expected (%s)", url),
                1, url.chars().filter(ch -> ch == '?').count()
        );
        Assert.assertTrue( String.format("The url should starts with http[s] or / (%s)", url),
                url.startsWith("/") || url.startsWith("http")
        );
    }
}
