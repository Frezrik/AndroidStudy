package com.ming.androidstudy.entity;

import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class AppInfoEntity
        implements Parcelable
{

    private String      appName; // 应用名称
    private String      packageName; // 包名
    private PackageInfo packageInfo; // 包信息
    private int         versionCode; // 版本号
    private String      versionName; // 版本名称

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppInfoEntity{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", packageInfo=" + packageInfo +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appName);
        dest.writeString(this.packageName);
        dest.writeParcelable(this.packageInfo, flags);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
    }

    public AppInfoEntity() {
    }

    private AppInfoEntity(Parcel in) {
        this.appName = in.readString();
        this.packageName = in.readString();
        this.packageInfo = in.readParcelable(PackageInfo.class.getClassLoader());
        this.versionCode = in.readInt();
        this.versionName = in.readString();
    }

    public static final Parcelable.Creator<AppInfoEntity> CREATOR = new Parcelable.Creator<AppInfoEntity>() {
        @Override
        public AppInfoEntity createFromParcel(Parcel source) {
            return new AppInfoEntity(source);
        }

        @Override
        public AppInfoEntity[] newArray(int size) {
            return new AppInfoEntity[size];
        }
    };
}
