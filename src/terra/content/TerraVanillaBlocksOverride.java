package terra.content;

import mindustry.*;
import mindustry.io.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.type.unit.*;  
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.meta.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.gen.*;
import arc.struct.*;
import arc.util.*;
import static mindustry.type.ItemStack.with;

public class TerraVanillaBlocksOverride {
    public static void load() {
        ((Liquid) Liquids.gallium).hidden = false;
    
        ((Item) Items.fissileMatter).hidden = false;
        ((Item) Items.dormantCyst).hidden = false;
    
        ((Weather) Weathers.suspendParticles).hidden = false;
    
        ((Thruster) Blocks.thruster).buildVisibility = BuildVisibility.shown;
        ((LaunchPad) Blocks.launchPad).buildVisibility = BuildVisibility.sandboxOnly;
        ((HeatProducer) Blocks.heatReactor).buildVisibility = BuildVisibility.shown;
        ((GenericCrafter) Blocks.slagCentrifuge).buildVisibility = BuildVisibility.shown;
        ((BaseShield) Blocks.shieldProjector).buildVisibility = BuildVisibility.shown;
        ((BaseShield) Blocks.shieldProjector).requirements = with(Items.graphite, 6200, Items.thorium, 3000, Items.silicon, 5000, Items.phaseFabric, 3100, Items.surgeAlloy, 2200, Items.beryllium, 3800, Items.tungsten, 3800, Items.oxide, 2500, Items.carbide, 2200, Items.fissileMatter, 1200);
        ((BaseShield) Blocks.shieldProjector).researchCostMultiplier = 0.01f;
        ((BaseShield) Blocks.largeShieldProjector).buildVisibility = BuildVisibility.shown;
        ((BaseShield) Blocks.largeShieldProjector).requirements = with(Items.graphite, 6200 * 3, Items.thorium, 3000 * 3, Items.silicon, 5000 * 3, Items.phaseFabric, 3100 * 3, Items.surgeAlloy, 2200 * 3, Items.beryllium, 3800 * 3, Items.tungsten, 3800 * 3, Items.oxide, 2500 * 3, Items.carbide, 2200 * 3, Items.fissileMatter, 1200 * 3);
        ((BaseShield) Blocks.largeShieldProjector).researchCostMultiplier = 0.01f;
        ((PowerNode) Blocks.powerNode).requirements = with(Items.graphite, 2, Items.lead, 6);
    
        ((Floor) Blocks.slag).supportsOverlay = true;
        ((Floor) Blocks.tar).supportsOverlay = true;
        ((Floor) Blocks.arkyciteFloor).supportsOverlay = true;
    
        ((NeoplasmUnitType) UnitTypes.renale).hidden = false;
        ((NeoplasmUnitType) UnitTypes.latum).hidden = false;
        ((ErekirUnitType) UnitTypes.manifold).hidden = false;
        ((ErekirUnitType) UnitTypes.assemblyDrone).hidden = false;
        ((ErekirUnitType) UnitTypes.evoke).controller = u -> u.team.isAI() ? new BuilderAI(true, 500f) : new CommandAI();
        ((ErekirUnitType) UnitTypes.incite).controller = u -> u.team.isAI() ? new BuilderAI(true, 500f) : new CommandAI();
        ((ErekirUnitType) UnitTypes.emanate).controller = u -> u.team.isAI() ? new BuilderAI(true, 500f) : new CommandAI();
        Vars.content.unit("anthicus-missile").hidden = false;
        Vars.content.unit("quell-missile").hidden = false;
        Vars.content.unit("disrupt-missile").hidden = false;
        Vars.content.unit("scathe-missile").hidden = false;
        Vars.content.unit("scathe-missile-phase").hidden = false;
        Vars.content.unit("scathe-missile-surge").hidden = false;
        Vars.content.unit("scathe-missile-surge-split").hidden = false;
    }
}
