package org.loader

import org.level.Level

class LevelLoader {
    val title = arrayOf(
        ImageResource("/CasualCaving/Levels/Title/LoadScreen.png"),
        ImageResource("/CasualCaving/Levels/Title/TitleScreen.png")
    )
    val titleLogo = ImageResource("/CasualCaving/Levels/Title/LunanLogo.png")
    val level1 = arrayOf(
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg1.png"),
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg2.png"),
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg3.png"),
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg4.png"),
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg5.png"),
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg6.png"),
        ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg6Dusk.png")
    )
    val level1Sprites = arrayOf(
        ImageResource("/CasualCaving/Levels/Level1/Sprites/BossMan.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/Crowd.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/Log.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/Bridge.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/RedTent.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/MintTent.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/LavenderTent.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/AquaTent.png"),
        ImageResource("/CasualCaving/Levels/Level1/Sprites/HaroldTent.png")
    )
    val level2 = arrayOf(
        Level.BiplanarSceneData(
            ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg1.png"),
            ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg1Foreground.png")
        ),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg2.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg3.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg3Anchor.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg4.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg5.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg6.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level2/Sun Stone.png"))
    )
    val level2Sprites = arrayOf(
        ImageResource("/CasualCaving/Levels/Level2/Sprites/Spark.png"),
        ImageResource("/CasualCaving/Levels/Level2/Sprites/Miners1.png"),
        ImageResource("/CasualCaving/Levels/Level2/Sprites/Lavender Savior.png"),
        ImageResource("/CasualCaving/Levels/Level2/Sprites/QESunStone.png")
    )
    val level3 = arrayOf(
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg1.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg2.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg2-5CaveIn.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg3.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg4.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg5.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg6.png"),
        ImageResource("/CasualCaving/Levels/Level3/Lv3Bg7.png")
    )
    val level3Sprites = arrayOf(
        ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Isolsi_Eyes.png"),
        ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Hematus_Eyes.png"),
        ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_1.png"),
        ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_2.png"),
        ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_3.png"),
        ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_4.png")
    )
    val level4 = arrayOf(
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level4/Lv4Bg1.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level4/Lv4Bg2.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level4/Lv4Bg3.png")),
        Level.BiplanarSceneData(ImageResource("/CasualCaving/Levels/Level4/Lv4Bg4.png")),
        Level.BiplanarSceneData(
            ImageResource("/CasualCaving/Levels/Level4/Lv4Bg5.png"),
            ImageResource("/CasualCaving/Levels/Level4/Lv4Bg5_Fascade.png")
        ),
        Level.BiplanarSceneData(
            ImageResource("/CasualCaving/Levels/Level4/Lv4Bg6.png"),
            ImageResource("/CasualCaving/Levels/Level4/Lv4Bg6_Fascade.png")
        ),
        Level.BiplanarSceneData(
            ImageResource("/CasualCaving/Levels/Level4/Lv4Bg7.png"),
            ImageResource("/CasualCaving/Levels/Level4/Lv4Bg7_Fascade.png")
        )
    )
    val level5 = arrayOf(
        ImageResource("/CasualCaving/Levels/Level5/Lv5Bg1.png"),
        ImageResource("/CasualCaving/Levels/Level5/Lv5Bg2.png"),
        ImageResource("/CasualCaving/Levels/Level5/Lv5Bg3_-_The_Larano_Boss_Room.png"),
        ImageResource("/CasualCaving/Levels/Level5/Lv5Bg4.png")
    )
    val level6 = arrayOf(
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg1.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg2.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg3.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg4.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg5.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg8.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg6.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg7.png"),
        ImageResource("/CasualCaving/Levels/Level6/Lv6Bg8.png")
    )
    val level6Town = arrayOf(
        ImageResource("/CasualCaving/Levels/Level6/Model_Town_Destroyed.png"),
        ImageResource("/CasualCaving/Levels/Level6/Model_Town_Intact.png")
    )
}