package org.lab6.udp;

import java.io.Serializable;

public enum ServerCommandType implements Serializable {
    CLEAR, FILTER_BY_TYPE, GROUP_COUNTING_BY_TYPE, GET_INFO, GET_MAX_DISTANCE, REMOVE_BY_ID, REMOVE_GREATER, REMOVE_LAST, REORDER, SHOW, ADD, GET, UPDATE, ERROR
}
