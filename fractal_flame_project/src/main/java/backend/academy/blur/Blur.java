package backend.academy.blur;

import backend.academy.utilits.Pixel;

public interface Blur {
    Pixel[][] applyBlur(Pixel[][] pixels);
}
