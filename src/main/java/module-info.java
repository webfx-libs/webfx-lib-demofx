// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.lib.demofx {

    // Direct dependencies modules
    requires java.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires webfx.extras.imagestore;
    requires webfx.platform.shutdown;

    // Exported packages
    exports com.chrisnewland.demofx;
    exports com.chrisnewland.demofx.effect;
    exports com.chrisnewland.demofx.effect.effectfactory;
    exports com.chrisnewland.demofx.effect.fake3d;
    exports com.chrisnewland.demofx.effect.spectral;
    exports com.chrisnewland.demofx.util;

    // Resources packages
    opens com.chrisnewland.demofx.images;

}