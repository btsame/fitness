package com.dkjs.fitness.util;

/**
 * Created by administrator on 16/8/7.
 */
public class URIUtil {

    public static String handleNetworkUri(String uri){
        if(uri == null) return null;
        if(uri.startsWith("http") || uri.startsWith("https")){
            return uri;
        }else{
            return "http://" + uri;
        }
    }

    public static String handleFileUrl(String uri){
        if(uri == null) return null;
        if(uri.startsWith("file://")){
            return uri;
        }else{
            return "file://" + uri;
        }
    }
}
