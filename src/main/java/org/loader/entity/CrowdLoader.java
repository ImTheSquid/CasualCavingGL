package org.loader.entity;

import org.loader.ImageResource;

public class CrowdLoader {
    private ImageResource[] crowd={new ImageResource("/CasualCaving/Levels/Level1/Sprites/CrowdReversed.png"),
    new ImageResource("/CasualCaving/Levels/Level1/Sprites/CrowdReversedW1.png"),
    new ImageResource("/CasualCaving/Levels/Level1/Sprites/CrowdReversedW2.png"),
    new ImageResource("/CasualCaving/Levels/Level1/Sprites/CrowdReversedW3.png")};
    private ImageResource cart=new ImageResource("/CasualCaving/Levels/Level1/Sprites/Cart.png");

    public ImageResource[] getCrowdWalk() {
        return crowd;
    }

    public ImageResource getCrowd(){
        return crowd[0];
    }

    public ImageResource getCart() {
        return cart;
    }
}
