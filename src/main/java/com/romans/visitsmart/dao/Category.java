package com.romans.visitsmart.dao;

import com.google.gson.annotations.SerializedName;
import com.romans.visitsmart.exceptions.LanguageNotFoundException;

/**
 * Created by Romans on 07/04/14.
 */
public class Category {
    String id;
    String thumb_url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public Description[] getObjectDescription() {
        return objectDescription;
    }

    public Description getObjectDescription(String lang) throws LanguageNotFoundException{
        for (Description description : objectDescription)
        {
            if (description.lang.equalsIgnoreCase(lang))
                return description;
        }
        throw new LanguageNotFoundException("Cannot find description for view: " + id + " with language " + lang);
    }

    public void setObjectDescription(Description[] objectDescription) {
        this.objectDescription = objectDescription;
    }

    @SerializedName("object_description")
    Description[] objectDescription;

    public boolean hasLangSupport(String language) {
        for (Description descr : objectDescription)
        {
            if (descr.lang.equalsIgnoreCase(language))
                return true;
        }
        return false;
    }

    public class Description {
        public String lang;
        public String name;

        public Description(){}
        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

