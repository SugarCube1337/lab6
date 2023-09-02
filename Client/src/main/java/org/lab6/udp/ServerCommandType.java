package org.lab6.udp;

import java.io.Serializable;

public enum ServerCommandType implements Serializable {
    CLEAR, FILTER_BY_TYPE, PRINT_UNIQUE_DISTANCE, GROUP_COUNTING_BY_TO, COUNT_GREATER_THAN_DISTANCE,  GROUP_COUNTING_BY_TYPE, GET_INFO, REMOVE_GREATER, REMOVE_LOWER, SHOW, ADD, GET, UPDATE, ERROR;
}
