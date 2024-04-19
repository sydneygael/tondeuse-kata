package org.domain.enums;

import lombok.extern.slf4j.Slf4j;
import org.domain.exceptions.OrientationNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public enum OrientationEnum {

    NORTH("N"),
    EAST("E"),
    WEST("W"),

    SOUTH("S");

    private static final Map<String, OrientationEnum> BY_CODE = new HashMap<>();

    static {
        for (OrientationEnum e : values()) {
            BY_CODE.put(e.code, e);
        }
    }

    private final String code;

    private OrientationEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static OrientationEnum of(String orientation) throws OrientationNotFoundException {

        var orientationEnum = BY_CODE.get(orientation);

        if (orientationEnum == null) {
            log.error("This orientation ({}) doesn't exist please use : N,E,W,S", orientation);
            throw new OrientationNotFoundException("Fail to convert to OrientationEnum");
        }
        return orientationEnum;
    }

}


