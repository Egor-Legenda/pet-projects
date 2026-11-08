package edu.itmo.hh.pages.enums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum SerpWorkFormat {

    HYBRID(
            "HYBRID",
            "Гибрид",
            "Сочетание работы в офисе и удалённо"),
    REMOTE(
            "REMOTE",
            "Удалённо",
            "Без обязательного посещения офиса работодателя"),
    ON_SITE(
            "ON_SITE",
            "На месте работодателя",
            "Только офис / локация работодателя");

    private final String urlValue;
    private final String shortLabelRu;
    private final String descriptionRu;

    SerpWorkFormat(String urlValue, String shortLabelRu, String descriptionRu) {
        this.urlValue = urlValue;
        this.shortLabelRu = shortLabelRu;
        this.descriptionRu = descriptionRu;
    }
}
