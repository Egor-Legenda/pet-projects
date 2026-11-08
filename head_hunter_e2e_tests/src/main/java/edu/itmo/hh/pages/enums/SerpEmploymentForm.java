package edu.itmo.hh.pages.enums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum SerpEmploymentForm {

    FULL("FULL", "Полная занятость"),
    PART("PART", "Частичная занятость"),
    PROJECT("PROJECT", "Подработка"),
    FLY_IN_FLY_OUT("FLY_IN_FLY_OUT", "Вахта");

    private final String urlValue;
    private final String sidebarLabelFragment;

    SerpEmploymentForm(String urlValue, String sidebarLabelFragment) {
        this.urlValue = urlValue;
        this.sidebarLabelFragment = sidebarLabelFragment;
    }

    public static SerpEmploymentForm fromUrlValue(String raw) {
        if (raw == null) {
            return null;
        }
        for (SerpEmploymentForm e : values()) {
            if (e.urlValue().equals(raw)) {
                return e;
            }
        }
        return null;
    }
}
