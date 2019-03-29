package org.loader;

import org.loader.harold.HaroldLoader;

public class ResourceHandler {
    private static HaroldLoader haroldLoader =new HaroldLoader();
    private static final ImageResource[] level1={};
    private static final ImageResource[] level2={};
    private static final ImageResource[] level3={};

    public static HaroldLoader getHaroldLoader(){
        return haroldLoader;
    }
}
