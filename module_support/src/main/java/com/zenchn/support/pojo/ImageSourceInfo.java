package com.zenchn.support.pojo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;

import com.zenchn.support.annotation.ImageSourceType;
import com.zenchn.support.kit.RegexKit;

import java.io.File;

/**
 * 作    者：hzj on 2018/9/3 11:15
 * 描    述：管理图片资源DO
 * 修订记录：
 */
public class ImageSourceInfo implements Parcelable {

    private int sourceType;

    @IntegerRes
    private int resSource;
    private String pathSource;
    private String urlSource;
    private File fileSource;
    private Uri uriSource;

    private ImageSourceInfo() {
        this.sourceType = ImageSourceType.INVALID;
    }

    public ImageSourceInfo(@IntegerRes int source) {
        this.resSource = source;
        this.sourceType = ImageSourceType.RES_ID;
    }

    public ImageSourceInfo(String source, boolean isOnline) {
        if (isOnline) {
            this.urlSource = source;
            this.sourceType = ImageSourceType.URL;
        } else {
            this.pathSource = source;
            this.sourceType = ImageSourceType.PATH;
        }
    }

    public ImageSourceInfo(File fileSource) {
        this.fileSource = fileSource;
        this.sourceType = ImageSourceType.FILE;
    }

    public ImageSourceInfo(Uri source) {
        this.uriSource = source;
        this.sourceType =ImageSourceType.URI;
    }

    @ImageSourceType
    public int getSourceType() {
        return sourceType;
    }

    public static ImageSourceInfo getImageSourceInfo(Object source) {
        if (source instanceof Integer) {
            return new ImageSourceInfo((Integer) source);
        } else if (source instanceof String) {
            boolean isOnline = RegexKit.getMatcher((String) source, RegexKit.REGEX_URL).find();
            return new ImageSourceInfo((String) source, isOnline);
        } else if (source instanceof File) {
            return new ImageSourceInfo((File) source);
        } else if (source instanceof Uri) {
            return new ImageSourceInfo((Uri) source);
        }
        return new ImageSourceInfo();
    }


    public Object getSource() {
        switch (sourceType) {
            case ImageSourceType.RES_ID:
                return resSource;
            case ImageSourceType.PATH:
                return pathSource;
            case ImageSourceType.URL:
                return urlSource;
            case ImageSourceType.FILE:
                return fileSource;
            case ImageSourceType.URI:
                return uriSource;
            default:
                return null;

        }
    }

    @Override
    public String toString() {
        return "ImageSourceInfo{" +
                "sourceType=" + sourceType +
                ", resSource=" + resSource +
                ", pathSource='" + pathSource + '\'' +
                ", fileSource=" + fileSource +
                ", uriSource=" + uriSource +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sourceType);
        dest.writeInt(this.resSource);
        dest.writeString(this.pathSource);
        dest.writeString(this.urlSource);
        dest.writeSerializable(this.fileSource);
        dest.writeParcelable(this.uriSource, flags);
    }

    protected ImageSourceInfo(Parcel in) {
        this.sourceType = in.readInt();
        this.resSource = in.readInt();
        this.pathSource = in.readString();
        this.urlSource = in.readString();
        this.fileSource = (File) in.readSerializable();
        this.uriSource = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<ImageSourceInfo> CREATOR = new Creator<ImageSourceInfo>() {
        @Override
        public ImageSourceInfo createFromParcel(Parcel source) {
            return new ImageSourceInfo(source);
        }

        @Override
        public ImageSourceInfo[] newArray(int size) {
            return new ImageSourceInfo[size];
        }
    };
}
