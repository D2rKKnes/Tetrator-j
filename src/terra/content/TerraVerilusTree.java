package terra.content;

import arc.Core;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.game.Objectives;
import mindustry.type.SectorPreset;

import static terra.content.TerraSectorPresets.*;
import static terra.content.TerraUnitTypes.*;
import static terra.content.TerraBlocks.*;
import static terra.content.TerraItems.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.TechTree.*;
import static mindustry.content.TechTree.nodeProduce;
import static mindustry.content.UnitTypes.*;

public class TerraVerilusTree {
    public static void load() {
        Planets.verilus.techTree = nodeRoot("verilus", coreSolaris, () -> {
            node(graphiteConveyor, () -> {
                node(graphiteJunction, () -> {
                    node(graphiteRouter, () -> {
                        node(container, () -> {
                            node(vault, () -> {});
                            node(unloader, () -> {});
                        });
                    });
                    node(graphiteGate, () -> {});
                });
                node(pulseConduit, () -> {
                    node(liquidJunction, () -> {
                        node(liquidRouter, () -> {
                            node(liquidContainer, () -> {
                                node(liquidTank, () -> {});
                            });
                        });
                    });
                    node(bridgeConduit, () -> {
                        node(phaseConduit, () -> {});
                    });
                });
                node(graphiteBridge, () -> {
                    node(smallDriver, () -> {
                        node(massDriver, () -> {});
                    });
                    node(phaseConduit, () -> {});
                });
            });
            node(graphiteMiner, () -> {
                node(pulseDrill, () -> {
                    node(plasmaDrill, () -> {
                        node(beamMiningFacility, () -> {});
                    });
                    node(sandExtractor, () -> {
                        node(iceMelter, () -> {
                            node(cryofluidMixer, () -> {
                                node(multiMixer, () -> {});
                            });
                        });
                        node(powerNode, () -> {
                            node(powerNodeLarge, () -> {
                                node(batteryLarge, () -> {});
                            });
                            node(solarPanel, () -> {
                                node(largeSolarPanel, () -> {});
                                node(photonPanel, () -> {
                                    node(photonPanelLarge, () -> {});
                                });
                                node(thoriumReactor, () -> {});
                            });
                        });
                        node(mechanicalWell, () -> {
                            node(bisiliconOven, () -> {
                                node(titaniumPress, () -> {
                                    node(diamondCoverer, () -> {});
                                    node(phaseWeaver, () -> {});
                                });
                                node(darkSteelWorkshop, () -> {});
                                node(electricalWell, () -> {});
                            });
                        });
                    });
                });
            });
            node(flight, () -> {
                node(titaniumWall, () -> {
                    node(titaniumWallLarge, () -> {
                        node(thoriumWall, () -> {
                            node(thoriumWallLarge, () -> {
                                node(phaseWall, () -> {
                                    node(phaseWallLarge, () -> {
                                        node(phaseWallHuge, () -> {});
                                    });
                                });
                            });
                            node(darkSteelWall, () -> {
                                node(darkSteelWallLarge, () -> {});
                            });
                        });
                    });
                    node(metaglassWall, () -> {
                        node(metaglassWallLarge, () -> {
                            node(metaglassWallHuge, () -> {});
                        });
                    });
                });
                node(electricShock, () -> {
                    node(parallax, () -> {
                        node(segment, () -> {});
                    });
                });
                node(dynamics, () -> {
                    node(fracture, () -> {});
                });
            });
            node(basicAssembler, () -> {
                node(flare, () -> {
                    node(horizon, () -> {
                        node(zenith, Seq.with(new AtWave(verilus, 15)), () -> {});
                    });
                    node(mono, () -> {
                        node(poly, () -> {
                            node(mega, Seq.with(new AtWave(verilus, 15)), () -> {});
                        });
                    });
                    node(wick, () -> {
                        node(dynamite, () -> {
                            node(incident, Seq.with(new AtWave(verilus, 15)), () -> {});
                        });
                    });
                });
                node(advancedAssembler, () -> {
                    node(antumbra, Seq.with(new AtWave(verilus, 33)), () -> {
                        node(eclipse, Seq.with(new AtWave(verilus, 75)), () -> {});
                        node(quad, Seq.with(new AtWave(verilus, 33)), () -> {
                            node(oct, Seq.with(new AtWave(verilus, 75)), () -> {});
                        });
                        node(catastrophe, Seq.with(new AtWave(verilus, 33)), () -> {
                            node(inevitability, Seq.with(new AtWave(verilus, 75)), () -> {
                                node(eternity, Seq.with(new AtWave(verilus, 180)), () -> {});
                                node(inevitabilityCore, () -> {});
                            });
                        });
                    });
                });
                node(basicAssemblyDrone, () -> {});
            });
            nodeProduce(lead, () -> {
                nodeProduce(graphite, () -> {
                    nodeProduce(sand, () -> {});
                    nodeProduce(silicon, () -> {
                        nodeProduce(titaniumPlate, () -> {});
                    });
                    nodeProduce(metaglass, () -> {
                        nodeProduce(water, () -> {});
                    });
                });
                nodeProduce(carbon, () -> {
                    nodeProduce(diamondDust, () -> {
                        nodeProduce(diamondGlass, () -> {});
                    });
                    nodeProduce(rawThermoxite, () -> {
                        nodeProduce(thermoxite, () -> {});
                    });
                });
                nodeProduce(titanium, () -> {
                    nodeProduce(thorium, () -> {
                        nodeProduce(phaseFabric, () -> {
                            nodeProduce(fissileMatter, () -> {});
                        });
                        nodeProduce(uranium, () -> {
                            nodeProduce(plutonium, () -> {
                                nodeProduce(radium, () -> {});
                            });
                        });
                        nodeProduce(darkSteel, () -> {
                            nodeProduce(tesseract, () -> {});
                        });
                    });
                    nodeProduce(cryofluid, () -> {});
                });
            });
            node(verilus, () -> {});
        });
    }

    public static class AtWave implements Objectives.Objective {
        public SectorPreset sector;
        public int wave;

        public AtWave(SectorPreset sector,int wave) {
            this.sector = sector;
            this.wave = wave;
        }

        @Override public boolean complete() {
            return sector.sector.hasSave() && sector.sector.save.getWave() >= wave;
        }

        @Override public String display() {
            return Core.bundle.format("requirement.omaloon-at-wave", wave, sector.localizedName);
        }
    }
}
