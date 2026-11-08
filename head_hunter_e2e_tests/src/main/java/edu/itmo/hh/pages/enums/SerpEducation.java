package edu.itmo.hh.pages.enums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum SerpEducation {

    HIGHER("higher", "Высшее"),
    SPECIAL_SECONDARY("special_secondary", "Профессиональное"),
    NOT_REQUIRED_OR_NOT_SPECIFIED("not_required_or_not_specified", "Не требуется");

    private final String urlValue;
    private final String sidebarLabelFragment;

    SerpEducation(String urlValue, String sidebarLabelFragment) {
        this.urlValue = urlValue;
        this.sidebarLabelFragment = sidebarLabelFragment;
    }

}
