package terra.content;

import java.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.func.Cons;
import arc.*;
import terra.ai.*;
import terra.content.*;
import terra.type.*;
import terra.type.bullet.*;
import terra.type.weapons.*;
import terra.type.ability.*;
import terra.type.units.*;
import mindustry.Vars;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;
import blackhole.entities.bullet.*;
import blackhole.entities.part.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class TerraVanillaUnitsOverride {
    public static void load() {
        UnitTypes.omura.weapons.add(
            new ConstructWeapon("flare"){{
                x = -77f / 4f;
                y = -121.5f / 4f;
                shootY = 0f;
                //shootSound = TerraSounds.shootLaunch;
                reload = 180f;
                rotate = true;
                rotateSpeed = 14f;
                mirror = false;
                shootCone = 20f;
                bullet = new BulletType(){{
                    shootEffect = smokeEffect = Fx.none;
                    shake = 1f;
                    speed = 0.001f;
                    keepVelocity = false;

                    spawnUnit = UnitTypes.flare;
                }};
            }}
        );
    }
}
