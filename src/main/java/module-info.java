// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.lib.demofx {

    // Direct dependencies modules
    requires javafx.graphics;
    requires javafx.media;
    requires webfx.kit.launcher;
    requires webfx.platform.console;
    requires webfx.platform.resource;

    // Exported packages
    exports com.chrisnewland.demofx;
    exports com.chrisnewland.demofx.effect;
    exports com.chrisnewland.demofx.effect.addon;
    exports com.chrisnewland.demofx.effect.effectfactory;
    exports com.chrisnewland.demofx.effect.fake3d;
    exports com.chrisnewland.demofx.effect.fractal;
    exports com.chrisnewland.demofx.effect.pixel;
    exports com.chrisnewland.demofx.effect.shape;
    exports com.chrisnewland.demofx.effect.spectral;
    exports com.chrisnewland.demofx.effect.sprite;
    exports com.chrisnewland.demofx.effect.text;
    exports com.chrisnewland.demofx.util;

    // Resources packages
    opens com.chrisnewland.demofx.images;
    opens com.chrisnewland.demofx.images.letters;
    opens com.chrisnewland.demofx.text;

}