package taylor.com.googleimagesearcher.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rtteal on 2/13/2015.
 */
public class Settings implements Parcelable {
    public final String size, color, type, site;

    public Settings(String size, String color, String type, String site) {
        this.size = size;
        this.color = color;
        this.type = type;
        this.site = site;
    }

    private Settings(Parcel in) {
        this.size = in.readString();
        this.color = in.readString();
        this.type = in.readString();
        this.site = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(size);
        out.writeString(color);
        out.writeString(type);
        out.writeString(site);
    }

    public static final Parcelable.Creator<Settings> CREATOR
            = new Parcelable.Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    @Override
    public String toString(){
        return String.format("{size: %s, color: %s, type: %s, site: %s}", size, color, type, site);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
