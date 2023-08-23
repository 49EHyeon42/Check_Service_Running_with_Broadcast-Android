package dev.ehyeon.checkservicerunningwithbroadcastapplication;

public enum ApplicationIntentActionEnum {

    IS_SERVICE_RUNNING("dev.ehyeon.checkservicerunningwithbroadcastapplication.ACTION_IS_SERVICE_RUNNING"),
    SERVICE_IS_RUNNING("dev.ehyeon.checkservicerunningwithbroadcastapplication.SERVICE_IS_RUNNING");

    private final String action;

    ApplicationIntentActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
