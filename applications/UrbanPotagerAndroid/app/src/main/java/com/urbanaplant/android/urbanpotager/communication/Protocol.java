package com.urbanaplant.android.urbanpotager.communication;

/**
 * Created by Tatiana Grange on 07/04/2015.
 */
public class Protocol {
    public static final String BM_BLUETOOTH = "UP_CONNECT";
    public static final int BM_CONNECT_SUCCESS = 0;
    public static final int BM_CONNECT_FAIL = 1;

    public enum ProtoWrite{
        MANAGE_MODE_ON,
        MANAGE_MODE_OFF,
        LIGHT_ON,
        LIGHT_OFF,
        PUMP_ON,
        PUMP_OFF,
        CLEAN_ON,
        CLEAN_OFF,
        UPDATE_INFORMATIONS
    }
}
