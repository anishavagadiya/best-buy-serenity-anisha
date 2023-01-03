package com.bestbuy.crudtest;

import com.bestbuy.playgroundinfo.StoresSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Objects;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCrudTest extends TestBase {
    static String name = "Anya" + TestUtils.getRandomValue();
    static String type = "BigBox" + TestUtils.getRandomValue();
    static String address = "25 Oxford Street";
    static String address2 = "";
    static String city = "London";
    static String state = "London";
    static String zip = "W4 5HS";
    static int lat = 25;
    static int lng = 30;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeID;
    @Steps
    StoresSteps storesSteps;

    @Title("This will create a new store")
    @Test
    public void test001() {
        HashMap<Object, Object> servicesData = new HashMap<>();
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours, servicesData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
    }
    @Title("Verify store was added ")
    @Test
    public void test002() {
        HashMap<String, Object> storeMap = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storeMap, hasValue(name));

    }
    @Title("This test will Update the services information")
    @Test
    public void test003() {
        name = name + "_updated";
        storesSteps.updateStore(storeID, name);
        HashMap<String, ?> storesList = storesSteps.getStoreInfoByName(storeID);
        Assert.assertThat(storesList, hasValue(name));
        System.out.println(storesList);
    }

    @Title("Delete the store by ID")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200).log().all();
    }





}
