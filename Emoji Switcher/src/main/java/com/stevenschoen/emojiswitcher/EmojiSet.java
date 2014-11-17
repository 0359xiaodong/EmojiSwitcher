package com.stevenschoen.emojiswitcher;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmojiSet implements Parcelable {
    private String friendlyName;
    private File path;
    private String hash;

    public EmojiSet(File path) {
        this.path = path;

        friendlyName = FilenameUtils.removeExtension(path.getName());
        if (filenamesToFriendlyNames.containsKey(friendlyName)) {
            friendlyName = filenamesToFriendlyNames.get(friendlyName);
        }
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public File getPath() {
        return path;
    }

    public String getHash() throws IOException {
        if (hash == null || hash.length() ==  0) {
            hash = Files.hash(getPath(), Hashing.md5()).toString();
        }

        return hash;
    }

    @Override
    public String toString() {
        return getFriendlyName();
    }

    private static Map<String, String> filenamesToFriendlyNames = new HashMap<>();
    static {
        filenamesToFriendlyNames.put("GoogleLollipop", "Google (Lollipop)");
        filenamesToFriendlyNames.put("GoogleKitkat", "Google (KitKat)");
        filenamesToFriendlyNames.put("HTCM8", "HTC M8");
        filenamesToFriendlyNames.put("LGG3", "LG G3");
        filenamesToFriendlyNames.put("SamsungS4", "Samsung S4");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.friendlyName);
        dest.writeSerializable(this.path);
        dest.writeString(this.hash);
    }

    private EmojiSet(Parcel in) {
        this.friendlyName = in.readString();
        this.path = (File) in.readSerializable();
        this.hash = in.readString();
    }

    public static final Parcelable.Creator<EmojiSet> CREATOR = new Parcelable.Creator<EmojiSet>() {
        public EmojiSet createFromParcel(Parcel source) {
            return new EmojiSet(source);
        }

        public EmojiSet[] newArray(int size) {
            return new EmojiSet[size];
        }
    };
}