package com.codepath.instagramclient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jie on 11/26/15.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public String userImageUrl;
    public int width;
    public int height;
    public long createdTime;

    public static InstagramPhoto buildFromJson(JSONObject object) throws JSONException {
        InstagramPhoto photo = new InstagramPhoto();
        photo.imageUrl = object.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        photo.width = object.getJSONObject("images").getJSONObject("standard_resolution").getInt("width");
        photo.height = object.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
        photo.username = object.getJSONObject("user").getString("username");
        photo.userImageUrl = object.getJSONObject("user").getString("profile_picture");
        photo.caption = object.getJSONObject("caption").getString("text");
        photo.createdTime = object.getJSONObject("caption").getLong("created_time");
        return photo;
    }
}
