//package com.jemmerl.jemsgeology.init.blockinit;
//
//import com.jemmerl.jemsgeology.data.enums.GeologyType;
//import com.jemmerl.jemsgeology.data.enums.ore.OreType;
//import com.jemmerl.jemsgeology.init.ModBlocks;
//import com.jemmerl.jemsgeology.init.ModItems;
//import com.jemmerl.jemsgeology.util.lists.GeoListWrapper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class OreBlockRegistry1 {
//
//    private final OreBlockRegistry apatiteGradeRegistry;
//    private final OreBlockRegistry azuriteGradeRegistry;
//    private final OreBlockRegistry baryteGradeRegistry;
//    private final OreBlockRegistry berylGradeRegistry;
//    private final OreBlockRegistry bismuthiniteGradeRegistry;
//    private final OreBlockRegistry carnotiteGradeRegistry;
//    private final OreBlockRegistry cassiteriteGradeRegistry;
//    private final OreBlockRegistry celestineGradeRegistry;
//    private final OreBlockRegistry chalcopyriteGradeRegistry;
//    private final OreBlockRegistry chromiteGradeRegistry;
//    private final OreBlockRegistry cinnabarGradeRegistry;
//    private final OreBlockRegistry cobaltiteGradeRegistry;
//    private final OreBlockRegistry cryoliteGradeRegistry;
//    private final OreBlockRegistry diamondGradeRegistry;
//    private final OreBlockRegistry electrumGradeRegistry;
//    private final OreBlockRegistry fluoriteGradeRegistry;
//    private final OreBlockRegistry galenaGradeRegistry;
//    private final OreBlockRegistry goethiteGradeRegistry;
//    private final OreBlockRegistry graphiteGradeRegistry;
//    private final OreBlockRegistry hematiteGradeRegistry;
//    private final OreBlockRegistry ilmeniteGradeRegistry;
//    private final OreBlockRegistry lepidoliteGradeRegistry;
//    private final OreBlockRegistry limoniteGradeRegistry;
//    private final OreBlockRegistry magnesiteGradeRegistry;
//    private final OreBlockRegistry magnetiteGradeRegistry;
//    private final OreBlockRegistry malachiteGradeRegistry;
//    private final OreBlockRegistry molybdeniteGradeRegistry;
//    private final OreBlockRegistry monaziteGradeRegistry;
//    private final OreBlockRegistry native_copperGradeRegistry;
//    private final OreBlockRegistry native_goldGradeRegistry;
//    private final OreBlockRegistry native_sulfurGradeRegistry;
//    private final OreBlockRegistry pentlanditeGradeRegistry;
//    private final OreBlockRegistry peridotiteGradeRegistry;
//    private final OreBlockRegistry polluciteGradeRegistry;
//    private final OreBlockRegistry psilomelaneGradeRegistry;
//    private final OreBlockRegistry pyriteGradeRegistry;
//    private final OreBlockRegistry pyrochloreGradeRegistry;
//    private final OreBlockRegistry pyrolusiteGradeRegistry;
//    private final OreBlockRegistry rutileGradeRegistry;
//    private final OreBlockRegistry scheeliteGradeRegistry;
//    private final OreBlockRegistry smithsoniteGradeRegistry;
//    private final OreBlockRegistry sphaleriteGradeRegistry;
//    private final OreBlockRegistry spodumeneGradeRegistry;
//    private final OreBlockRegistry stibniteGradeRegistry;
//    private final OreBlockRegistry sylviteGradeRegistry;
//    private final OreBlockRegistry tantaliteGradeRegistry;
//    private final OreBlockRegistry tetrahedriteGradeRegistry;
//    private final OreBlockRegistry tronaGradeRegistry;
//    private final OreBlockRegistry uraniniteGradeRegistry;
//    private final OreBlockRegistry wolframiteGradeRegistry;
//
//    // register blocks
//    // add to respective tags
//    // create list of ores
//    public OreBlockRegistry1(GeologyType geoType) {
//
//        this.apatiteGradeRegistry = new OreBlockRegistry(geoType, OreType.APATITE);
//        this.azuriteGradeRegistry = new OreBlockRegistry(geoType, OreType.AZURITE);
//        this.baryteGradeRegistry = new OreBlockRegistry(geoType, OreType.BARYTE);
//        this.berylGradeRegistry = new OreBlockRegistry(geoType, OreType.BERYL);
//        this.bismuthiniteGradeRegistry = new OreBlockRegistry(geoType, OreType.BISMUTHINITE);
//        this.carnotiteGradeRegistry = new OreBlockRegistry(geoType, OreType.CARNOTITE);
//        this.cassiteriteGradeRegistry = new OreBlockRegistry(geoType, OreType.CASSITERITE);
//        this.celestineGradeRegistry = new OreBlockRegistry(geoType, OreType.CELESTINE);
//        this.chalcopyriteGradeRegistry = new OreBlockRegistry(geoType, OreType.CHALCOPYRITE);
//        this.chromiteGradeRegistry = new OreBlockRegistry(geoType, OreType.CHROMITE);
//        this.cinnabarGradeRegistry = new OreBlockRegistry(geoType, OreType.CINNABAR);
//        this.cobaltiteGradeRegistry = new OreBlockRegistry(geoType, OreType.COBALTITE);
//        this.cryoliteGradeRegistry = new OreBlockRegistry(geoType, OreType.CRYOLITE);
//        this.diamondGradeRegistry = new OreBlockRegistry(geoType, OreType.DIAMOND);
//        this.electrumGradeRegistry = new OreBlockRegistry(geoType, OreType.ELECTRUM);
//        this.fluoriteGradeRegistry = new OreBlockRegistry(geoType, OreType.FLUORITE);
//        this.galenaGradeRegistry = new OreBlockRegistry(geoType, OreType.GALENA);
//        this.goethiteGradeRegistry = new OreBlockRegistry(geoType, OreType.GOETHITE);
//        this.graphiteGradeRegistry = new OreBlockRegistry(geoType, OreType.GRAPHITE);
//        this.hematiteGradeRegistry = new OreBlockRegistry(geoType, OreType.HEMATITE);
//        this.ilmeniteGradeRegistry = new OreBlockRegistry(geoType, OreType.ILMENITE);
//        this.lepidoliteGradeRegistry = new OreBlockRegistry(geoType, OreType.LEPIDOLITE);
//        this.limoniteGradeRegistry = new OreBlockRegistry(geoType, OreType.LIMONITE);
//        this.magnesiteGradeRegistry = new OreBlockRegistry(geoType, OreType.MAGNESITE);
//        this.magnetiteGradeRegistry = new OreBlockRegistry(geoType, OreType.MAGNETITE);
//        this.malachiteGradeRegistry = new OreBlockRegistry(geoType, OreType.MALACHITE);
//        this.molybdeniteGradeRegistry = new OreBlockRegistry(geoType, OreType.MOLYBDENITE);
//        this.monaziteGradeRegistry = new OreBlockRegistry(geoType, OreType.MONAZITE);
//        this.native_copperGradeRegistry = new OreBlockRegistry(geoType, OreType.NATIVE_COPPER);
//        this.native_goldGradeRegistry = new OreBlockRegistry(geoType, OreType.NATIVE_GOLD);
//        this.native_sulfurGradeRegistry = new OreBlockRegistry(geoType, OreType.NATIVE_SULFUR);
//        this.pentlanditeGradeRegistry = new OreBlockRegistry(geoType, OreType.PENTLANDITE);
//        this.peridotiteGradeRegistry = new OreBlockRegistry(geoType, OreType.PERIDOTITE);
//        this.polluciteGradeRegistry = new OreBlockRegistry(geoType, OreType.POLLUCITE);
//        this.psilomelaneGradeRegistry = new OreBlockRegistry(geoType, OreType.PSILOMELANE);
//        this.pyriteGradeRegistry = new OreBlockRegistry(geoType, OreType.PYRITE);
//        this.pyrochloreGradeRegistry = new OreBlockRegistry(geoType, OreType.PYROCHLORE);
//        this.pyrolusiteGradeRegistry = new OreBlockRegistry(geoType, OreType.PYROLUSITE);
//        this.rutileGradeRegistry = new OreBlockRegistry(geoType, OreType.RUTILE);
//        this.scheeliteGradeRegistry = new OreBlockRegistry(geoType, OreType.SCHEELITE);
//        this.smithsoniteGradeRegistry = new OreBlockRegistry(geoType, OreType.SMITHSONITE);
//        this.sphaleriteGradeRegistry = new OreBlockRegistry(geoType, OreType.SPHALERITE);
//        this.spodumeneGradeRegistry = new OreBlockRegistry(geoType, OreType.SPODUMENE);
//        this.stibniteGradeRegistry = new OreBlockRegistry(geoType, OreType.STIBNITE);
//        this.sylviteGradeRegistry = new OreBlockRegistry(geoType, OreType.SYLVITE);
//        this.tantaliteGradeRegistry = new OreBlockRegistry(geoType, OreType.TANTALITE);
//        this.tetrahedriteGradeRegistry = new OreBlockRegistry(geoType, OreType.TETRAHEDRITE);
//        this.tronaGradeRegistry = new OreBlockRegistry(geoType, OreType.TRONA);
//        this.uraniniteGradeRegistry = new OreBlockRegistry(geoType, OreType.URANINITE);
//        this.wolframiteGradeRegistry = new OreBlockRegistry(geoType, OreType.WOLFRAMITE);
//    }
//
//
//
//}
