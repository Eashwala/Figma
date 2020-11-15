package com.ipl.user.commonutils;

public class Constants {

    public static final String BASE_URL = "https://0len6jubzk.execute-api.us-east-1.amazonaws.com/Prod/";
            //"https://zvcg1rgvk7.execute-api.us-east-1.amazonaws.com/Prod/";

    public static final String WEBSOCKET_BASE_URL ="wss://c2orqz00xh.execute-api.us-east-1.amazonaws.com/Prod/";
           //wss://c2orqz00xh.execute-api.us-east-1.amazonaws.com/Prod?gameId=f73a8350-150c-11eb-aaf0-c1f3244830cf

    //wss://ho9zc85taa.execute-api.us-east-1.amazonaws.com/Prod/

    public static final String domainName = "c2orqz00xh.execute-api.us-east-1.amazonaws.com";
    public static final String stage = "Prod";

    public static final String UNKNOWN = "UNKNOWN";
    public static final String ADD_COMMENT = "Please add comment";
    public static final String UNABLE_TO_UPLOAD_IMAGE = "Unable to upolad image,Please choose another image";
    public static final String POST = "post";
    public static final String ONE_DAY = "1 Day";
    public static final String ONE_WEEK = "1 Week";
    public static final String CUSTOM = "Custom";
    public static final String CANCEL = "Cancel";
    public static final String USERS_NEAR_YOU = "Users_NEAR_YOU";
    public static final String OFFERS_NEAR_YOU = "Offers Near You";
    public static final String EVENTS_NEAR_YOU = "Events_near_you" ;

    public static final String EMPTY_FEEDS_MESSAGE = "Feeds not available in your neighbourhood.";
    public static final String NO_MORE_FEEDS_AVAILABLE = "No more feeds available.";
    public static final String NO_MEMBERS_AVAILABLE = "No Users available.";
    public static final String NO_SELL_ITEMS = "No Items Available" ;
    public static final String NO_BUSINESS_NEAR_U = "No Business Available" ;


    public static String[] pollList = {ONE_DAY, ONE_WEEK, CUSTOM, CANCEL};

    public static final String CUSTOM_FONT_PATH_BOLD = "fonts/mulishbold.ttf";
    public static final String CUSTOM_FONT_PATH_MEDIUM = "fonts/mulishmedium.ttf";
    public static final String CUSTOM_FONT_PATH_LIGHT = "fonts/mulishlight.ttf";

    public static final int VIEW_TYPE_IMAGE = 0;
    public static final int VIEW_TYPE_TEXT = 1;
    public static final int VIEW_TYPE_VIDEO = 2;
    public static final int VIEW_TYPE_IMAGE_TEXT = 3;
    public static final int VIEW_TYPE_VIDEO_TEXT = 4;
    public static final int VIEW_TYPE_POLL = 5;
    public static final int VIEW_TYPE_BUSINESS = 6;
    public static final int VIEW_TYPE_SPONSERED = 7;

    public static final int VIEW_TYPE_NEIGHBOUR = 8;
    public static final int VIEW_TYPE_SELL = 9;
    public static final int LOADING = 10;
    public static final int EMPTYVIEW = 11;
    public static final int VIEW_TYPE_USERS = 12;
    public static final int VIEW_TYPE_OFFERS = 13 ;
    public static final int VIEW_TYPE_EVENTS = 14 ;

    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String TEXT_IMAGE = "text_image";
    public static final String TEXT_VIDEO = "text_video";
    public static final String POLL = "poll";
    public static final String SPONSERED = "sponsored";
    public static final String BUSINESS_NEAR_YOU = "business_near_you";
    public static final String NEIGHBOURS_NEAR_YOU = "neighbours_near_you";
    public static final String SELL_NEAR_YOU = "sell_near_you";

    public static final String ORIG_X = "ORIG_X";
    public static final String ORIG_Y = "ORIG_Y";
    public static final String ORIG_WIDTH = "ORIG_WIDTH";
    public static final String ORIG_HEIGHT = "ORIG_HEIGHT";


    public static String Facebook = "Facebook";
    public static String Google = "Google";

    public static final String COMMENT = "comment";
    public static final String LIKE = "like";
    public static final String FOLLOW = "follow";

    public static final String SSECTION_HEADER = "sectionHeader";
    public static final int VIEW_TYPE_SECTIONHEADER = 1;
    public static final int VIEW_TYPE_DATA = 2;

    public static final int WRITE_REQUEST_CODE = 123;


    public static final String FEATURE_IN_PROGRESS = "Feature In-Progress";
    public static final String EXCEPTION_MESSAGE = "Something went wrong, Please try again later.";
    public static final String FEEDS_ = "all ";
    public static final String ERROR_LOADING_IMAGE = "Error while loading image ";

    public static final String OTHERS = "Others";


//    public static int getIcon(String type){
//        if(!TextUtils.isEmpty(type)) {
//
//            if (type.contains("Appliances")) {
//                return R.drawable.ic_appliances;
//            } else if (type.contains("Bicycles")) {
//                return R.drawable.ic_bicycles;
//            } else if (type.contains("Electronics")) {
//                return R.drawable.ic_electronics;
//            } else if (type.contains("Baby & Kids")) {
//                return R.drawable.ic_toys;
//            } else if (type.contains("Furniture")) {
//                return R.drawable.ic_furniture;
//            } else if(type.contains("Post")){
//                return R.drawable.ic_post_icon;
//            } else if(type.contains("Poll")){
//                return R.drawable.ic_poll_icon;
//            } else if(type.contains("Event")){
//                return R.drawable.ic_event_icon;
//            } else if(type.contains("Complaint")){
//                return R.drawable.ic_complaint;
//            }
//
//        }
//        return 0;
//    }

}
