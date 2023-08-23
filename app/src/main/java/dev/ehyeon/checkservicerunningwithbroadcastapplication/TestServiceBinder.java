package dev.ehyeon.checkservicerunningwithbroadcastapplication;

import android.os.Binder;

public class TestServiceBinder extends Binder {

    private final TestService testService;

    public TestServiceBinder(TestService testService) {
        this.testService = testService;
    }

    public TestService getTestService() {
        return testService;
    }
}
