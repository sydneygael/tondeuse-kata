package org.domain.enums;

import lombok.extern.slf4j.Slf4j;
import org.domain.exceptions.OrientationNotFoundException;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public enum OrientationEnum {

    NORTH("N"),
    EST("E"),
    WEST("W"),

    SOUTH("S");

    private final String code;

    private OrientationEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static OrientationEnum of(String orientation) throws OrientationNotFoundException {

        Optional<OrientationEnum> orientationEnumOptional = Arrays.stream(OrientationEnum.values())
                .filter(commandEnum -> commandEnum.code.equals(orientation))
                .findFirst();

        if (orientationEnumOptional.isPresent()) {
            return orientationEnumOptional.get();
        } else {
            log.error("This orientation ({}) doesn't exist please use : N,E,W,S", orientation);
            throw new OrientationNotFoundException("Fail to convert to OrientationEnum");
        }
    }

}


