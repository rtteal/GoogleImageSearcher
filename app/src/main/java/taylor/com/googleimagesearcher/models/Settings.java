package taylor.com.googleimagesearcher.models;

import java.io.Serializable;

/**
 * Created by rtteal on 2/13/2015.
 */
public class Settings implements Serializable {
    public final String size, color, type, site;

    public Settings(String size, String color, String type, String site) {
        this.size = size;
        this.color = color;
        this.type = type;
        this.site = site;
    }

    @Override
    public String toString(){
        return String.format("{size: %s, color: %s, type: %s, site: %s}", size, color, type, site);
    }
}
