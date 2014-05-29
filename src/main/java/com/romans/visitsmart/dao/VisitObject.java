package com.romans.visitsmart.dao;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.romans.visitsmart.exceptions.LanguageNotFoundException;
import com.romans.visitsmart.utils.Prefs;

/**
 * Created by Romans on 05/05/14.
 */
public class VisitObject implements Measurable{

    private String id;
    private String categoryId;
    private String titleImageUrl;
    private ImagePair[] imagePairs;
    @SerializedName("objects")
    private VisitObjectDescription[] objectDescriptions;
  //  private Coordinates coordinates;
  @SerializedName("location")
  private LatLng coordinates;

    private float rating;
    private int ratingCount;

    private transient String distance;

    SharedPreferences prefs;

    @Override
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public LatLng getPosition() {
        return coordinates;
    }

    public String getDistance() {
        return distance;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public boolean hasLangSuport(String language) {

        for (VisitObjectDescription description : objectDescriptions)
        {
            if (description.getLang().equalsIgnoreCase(language))
            {
                return true;
            }
        }
        return false;
    }

    public class ImagePair
    {
        private String thumbUrl;

        private String imageUrl;

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }

    public class VisitObjectDescription
    {

        private String shortDescription;
        private String description;
        private String name;
        private String lang;
        public Hours[] workingHours;
        private ClientGroups[] groups;

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public Hours[] getWorkingHours() {
            return workingHours;
        }

        public void setWorkingHours(Hours[] workingHours) {
            this.workingHours = workingHours;
        }

        public ClientGroups[] getGroups() {
            return groups;
        }

        public void setGroups(ClientGroups[] groups) {
            this.groups = groups;
        }



        public class Hours {
            private String day;
            private String hours;

            public String getDay() {

                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }
        }

        public class ClientGroups {
            private String groupName;
            private String groupPrice;

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getGroupPrice() {
                return groupPrice;
            }

            public void setGroupPrice(String groupPrice) {
                this.groupPrice = groupPrice;
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public ImagePair[] getImagePairs() {
        return imagePairs;
    }

    public void setImagePairs(ImagePair[] imagePairs) {
        this.imagePairs = imagePairs;
    }

    public VisitObjectDescription[] getObjectDescriptions() {
        return objectDescriptions;
    }
    public VisitObjectDescription getObjectDescriptions(Context ctx) throws LanguageNotFoundException {
        prefs = ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
        for (VisitObjectDescription descr : objectDescriptions)
        {
            if (descr.getLang().equals(prefs.getString(Prefs.LANGUAGE, "lv")));
                return descr;
        }
        throw new LanguageNotFoundException();
    }

    public void setObjectDescriptions(VisitObjectDescription[] objectDescriptions) {
        this.objectDescriptions = objectDescriptions;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
