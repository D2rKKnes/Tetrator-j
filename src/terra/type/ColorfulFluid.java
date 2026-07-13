package terra.type;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.type.Liquid;

import java.util.ArrayList;
import java.util.List;

public class ColorfulFluid extends Liquid {
    private static final List<ColorfulFluid> instances = new ArrayList<>();

    private final Color[] colors;
    private int currentIndex = -1;

    private Color originalColor;
    private TextureRegion originalFullIcon;
    private TextureRegion originalUiIcon;

    public ColorfulFluid(String name, Color baseColor) {
        super(name, baseColor);
        this.colors = colors != null ? colors : new Color[0];
        this.originalColor = baseColor.cpy();
        instances.add(this);
    }

    @Override
    public void loadIcon() {
        super.loadIcon();
        this.originalFullIcon = this.fullIcon;
        this.originalUiIcon = this.uiIcon;
    }

    private void updateAppearance() {
        boolean neonEnabled = Core.settings.getBool("neoncolor", true);
        int index = -1;
        TextureRegion foundIcon = null;
        Color foundColor = null;

        if (neonEnabled && colors.length > 0) {
            index = Mathf.random(colors.length - 1);
            String spriteName = this.name + "-" + index;
            TextureRegion region = Core.atlas.find(spriteName);
            if (region.found()) {
                foundIcon = region;
                foundColor = colors[index];
            } else {
                index = -1;
            }
        }

        if (index >= 0 && foundIcon != null && foundColor != null) {
            this.currentIndex = index;
            this.color.set(foundColor);
            this.fullIcon = foundIcon;
            this.uiIcon = foundIcon;
        } else {
            this.currentIndex = -1;
            this.color.set(originalColor);
            this.fullIcon = originalFullIcon;
            this.uiIcon = originalUiIcon;
        }
    }

    static {
        Events.on(EventType.WorldLoadEvent.class, event -> {
            for (ColorfulFluid fluid : instances) {
                fluid.updateAppearance();
            }
        });
    }
}
