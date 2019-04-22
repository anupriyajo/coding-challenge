package io.bankbridge.handler;

import io.bankbridge.model.BankModel;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BanksCacheBasedTest {

    private CacheManager cacheManager;

    @Before
    public void setUp(){
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().withCache("banks", CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, BankModel.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
        Cache cache = cacheManager.getCache("banks", String.class, BankModel.class);
        BankModel bankModel = new BankModel();
        bankModel.setBic("1234");
        bankModel.setAuth("OAUTH");
        bankModel.setName("Royal Bank of Boredom");
        bankModel.setCountryCode("GB");
        cache.put("Royal Bank of Boredom", bankModel);
    }

    @Test
    public void testhandle(){
        assertNotNull(cacheManager);
        BanksCacheBased.setCacheManager(cacheManager);
        Request request = null;
        Response response = null;
        String actual = BanksCacheBased.handle(request, response);
        String expected = "[{\"1234\":{\"bic\":\"1234\",\"name\":\"Royal Bank of Boredom\",\"countryCode\":\"GB\",\"auth\":\"OAUTH\"}}]";
        assertEquals(expected, actual);
    }

    @After
    public void tearDown(){
        cacheManager.close();
    }

}
