package com.parse.starter;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by miyaharahirokazu on 15/04/17.
 */
@ParseClassName("Posts")
public class GeoShare extends ParseObject {

    public String getText()
    {
        return getString("text");
    }

    public void setText(String value)
    {
        put("text",value);
    }

    public ParseUser getUser()
    {
        return getParseUser("user");
    }

    public void setUser(ParseUser value)
    {
        put("user",value);
    }

    public ParseGeoPoint getLocation()
    {
        return getParseGeoPoint("location");
    }

    public void setLocation(ParseGeoPoint value)
    {
        put("location",value);
    }

    public static ParseQuery<GeoShare> getQuery()
    {
        return ParseQuery.getQuery(GeoShare.class);
    }
}
