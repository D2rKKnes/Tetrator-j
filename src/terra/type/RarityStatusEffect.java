package terra.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.Scl;
import mindustry.graphics.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.StatusEffect;

public class RarityStatusEffect extends StatusEffect {

    public Color tintColor = Color.white;
    public Float alpha = 0.6f;
    public boolean drawAura = true;

    public RarityStatusEffect(String name) {
        super(name);
        if (!Core.settings.getBool("unitsquality", true)){hideDatabase = true;}
        else hideDatabase = false;
    }

    @Override
    public void draw(Unit unit, float time) {
        if (!Core.settings.getBool("qualityring", true) || !drawAura) return;

        Draw.z(52f);
        Draw.color(tintColor, alpha);
        /*var sprite = unit.type.shadowRegion;
        float scale = 1.15f;
        float widths = (sprite.width / 4) * scale;
        float heights = (sprite.height / 4) * scale;
        Draw.rect(sprite, unit.x, unit.y, widths, heights, unit.rotation - 90);*/
        float maxDim = Math.max(unit.type.shadowRegion.width, unit.type.shadowRegion.height);
        float size = (maxDim / 4) * 1.25f;
        Draw.rect(Core.atlas.find("terra-rarity-ring"), unit.x, unit.y, size, size);
        Draw.reset();
    }
}
