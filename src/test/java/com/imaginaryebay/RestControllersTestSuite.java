package com.imaginaryebay;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Chloe on 6/30/16.
 * To package run : "mvn package -Dmaven.test.skip=true"
 * it won't build unless tests run, and tests won't work without deployment
 * so running the above builds while ignoring tests, then you can deploy
 * and run the tests. You can run them all from here.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ItemEndpointsTest.class,
//        MessageEndpointsTest.class,
        UserrEndpointsTest.class,

})
public class RestControllersTestSuite {

}