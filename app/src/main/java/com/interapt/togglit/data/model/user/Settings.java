
package com.interapt.togglit.data.model.user;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Settings {

    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("descr")
    @Expose
    private String descr;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     * 
     * @return
     *     The appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 
     * @param appName
     *     The app_name
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 
     * @return
     *     The descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 
     * @param descr
     *     The descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

}
