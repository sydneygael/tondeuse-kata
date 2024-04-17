package org.domain.enums;

import lombok.extern.slf4j.Slf4j;
import org.domain.exceptions.CommandNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public enum CommandEnum {
    ADVANCE("A"),
    RIGHT("D"),
    LEFT("G");

    private static final Map<String, CommandEnum> BY_CODE = new HashMap<>();

    static {
        for (CommandEnum e : values()) {
            BY_CODE.put(e.code, e);
        }
    }

    private final String code;

    CommandEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CommandEnum of(String code) throws CommandNotFoundException {
        var commandEnum = BY_CODE.get(code);
        if (commandEnum == null) {
            throw new CommandNotFoundException("Fail to convert to CommandEnum");
        }
        return commandEnum;
    }
}

