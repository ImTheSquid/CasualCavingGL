package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    private boolean direction=true;
    void setDirection(boolean dir){
        direction=dir;
    }
    private ImageResource harold=new ImageResource("/src/main/resources/Casual Caving/Entities/Harold/Harold.png");
    public ImageResource getHarold(){
        return harold;
    }
}
