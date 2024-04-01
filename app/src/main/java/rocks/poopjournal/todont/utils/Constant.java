package rocks.poopjournal.todont.utils;


import java.util.ArrayList;
public class Constant {

    // ********************************** Main Shared Pref Constants ********************************
    public static final String CHANNEL_ID = "MiSentinel_Channel_id";
    public static final String EMPLOYEE_NAME = "employeeName";
    public static final String FIREBASE_URL_LINK = "firebaseurl";
    public static final String FIREBASE_URL_LINK_REM = "firebaseurlrem";
    public static final String FIREBASE_URL_LINK_REM_CHECK = "firebaseurlCheck";
    public static final String EMPLOYEE_ID = "employeeId";
    public static final String EMPLOYEE_EMAIL = "employeeEmail";
    public static final String EMPLOYEE_SIGNATURE = "signature";
    public static final String EMPLOYEE_PASSWORD = "employeePassword";
    public static final String EMPLOYEE_PROFILE_IMAGE = "employeeProfileImage";
    public static final String COMPANY_NAME = "companyName";
    public static final String AUTH = "auth";
    public static final String PIN_ID = "p_id";
    public static final String SIGN_OUT = "sign_out";
    public static final String UK_TIME = "uk_time";
    public static final String OFFICER_TYPE = "officerType";
    public static final String REMEMBER_ME = "remember_me";
    public static final String USER_PASSWORD = "user_password";
    public static final String COMPANY_LOGO = "company_logo";
    public static final String ADDRESS = "company_address";
    public static final String DOB = "date_of_birth";
    public static final String SIN = "sin";
    public static final String IS_TYPE_MANAGER = "1";
    public static final int VEHICLE_ACCEPTANCE = 1;
    public static final int VEHICLE_REJECTION = 2;
    public static final int VEHICLE_WARNING = 3;
    public static final int TYPE_RESPONDER = 1;
    public static final int TYPE_OBSERVER = 2;
    public static final int BOOK_OFF_REQ_CODE = 100090;
    public static final int CHKCALL_REQ_CODE = 100091;


    public final static int SCAN_CODE = 1234;

    public static final String NOTIFICATION_IDENTIFIER ="com.example.ft.ireportsmain.notify";
    public static final String NOTIFICATION_IDENTIFIER_CHK ="com.example.ft.ireportsmain.chk";
    public static final String NOTIFICATION_IDENTIFIER_MSG ="com.example.ft.ireportsmain.msg";


    public static final String TOKEN = "token";
    public static String REQUEST = "request";
    public static String POST_ID = "post_id";
        public static String DOB_SINGLE_ENTRY = "2";
    public static String DOB_INCIDENT = "4";
    public static String DOB_HAND_OVER = "3";
    public static String POST_SCHEDULING_ID = "post_scheduling_id";
    public static String DATE_DUTY = "date_duty";
    public static String SCHEDULE_DATE_DUTY = "schedule_date_duty";
    public static String SUBMITED_DATE = "submit_date";
    public static String SOS_CONTACT_NUM = "sos_contact_num";
    public static String QR_STRING= "qr_string";

    // Routine Petrol...
    public static String SITE_ID = "site_id";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String SITE_KEYS = "site_keys";
    public static String CAMERAS = "cameras";
    public static String FILE_EXTINGUISHER = "fire_extinguisher";
    public static String LIGHTS = "lights";
    public static String DOOR_LOCKS = "door_locks";
    public static String ENTRANCE_DOORS = "enterance_doors";
    public static String CAR_PARKING = "car_parking";
    public static String ALARM_RESET = "alarm_reset";
    public static String ACCESS_ISSUES = "access_issues";
    public static String CALLED_ADT = "called_adt";
    public static String POUCH_DELIVERED = "pouch_delivered";
    public static String STICKER_CHECKS = "sticker_checks";
    public static String VARIANCE = "variance";

    public static String SIGNATURE = "signature";
    public static String REFERENCE_NUMBER = "reference_number";
    public static String DATE = "date";

    // Site Visit Report
    public static String SIGNATURE_OFFICER = "officer_signature";
    public static String SIGNATURE_MANAGER = "manager_signature";
    public static String UNIFORM = "uniform";
    public static String PPE = "ppe";
    public static String MANAGER_NAME = "site_manager_name";
    public static String COMPLETED_DOB = "completed_dob";
    public static final String ASSIGNMENT_INSTRUCTION = "assignment_instruction";
    public static final String FAMILIAR_ASSIGN_INSTRUCTION = "familiar_assignment_instruction";
    public static String COMMENT = "comment";
    public static String OFFICER_H_S = "officers_h_s";


    // Incident Report.....
    public static String REASON_OF_ATTENDANCE = "reason_of_attendance";
    public static String FORCED_ENTRY = "forced_entry";
    public static String APPARENT_REASON = "apparent_reason";
    public static String NUMBER_OF_INTRUDERS = "number_of_intruders";
    public static String RESPONCE_TEAM_ARRIVAL_TIME = "responce_team_arrival_time";
    public static String ENGINEERING_REQUIRED = "engineering_required";
    public static String POLICE_ATTENDANCE = "police_attendance";
    public static String NUMBER_OF_POLICE_OFFICERS = "number_of_police_officers";
    public static String POLICE_ARRIVAL_TIME = "police_arrival_time";
    public static String SYSTEM_RESET = "system_reset";
    public static String OLD_SEAL_NUMBER = "old_seal_number";
    public static String NEW_SEAL_NUMBER = "new_seal_number";
    public static String INCIDENT_TIME = "incident_time";
    public static String SYSTEM_RESET_TIME = "system_reset_time";
    public static String INCEDENT_PRIORITY = "incident_priority";
    public static String IMAGE = "image";
    public static String COMMENTS = "comments";
    public static String RESONCE_OFFICER_NAME = "responce_officer_name";

    public static String DESCRIPTIONS = "description";
    public static String SIGNATURE_VIDEO = "video";
    public static String SIGNATURE_IMAGES = "images_";
    public static String COUNT_IMAGES = "count_images";
    public static String SIGNATURE_AUDIO = "audio";

    /********************** Site Audit Report ************/

    public static String SA_Date_Audit = "date_audit";
    public static String SA_TimeCompleted = "time_completed";
    public static String SA_TimeCommenced = "time_commenced";
    public static String SA_AUDITOR_NAME = "auditor_name";
    public static String SA_ACCOUNT_NAME = "account_name";
    public static String SA_MANAGER_NAME = "manager_name";
    public static String SA_ATTENDANCE_TIME = "attendance_times";
    public static String SA_CONSIDERATION = "consideration";


    public static String SA_OFFICER_SWITCH = "duty_officer_switch";
    public static String SA_OFFICER_SCORE = "duty_officer_score";
    public static String SA_OFFICER_COMMENT = "duty_officer_comment";
    public static String SA_OFFICER_ACTION = "duty_officer_action";

    public static String SA_SIA_SWITCH = "sia_switch";
    public static String SA_SIA_SCORE = "sia_compliance_score";
    public static String SA_SIA_COMMENT = "sia_compliance_comment";
    public static String SA_SIA_ACTION = "sia_compliance_action";

    public static String SA_PRESENTATION_SWITCH = "presentation_switch";
    public static String SA_PRESENTATION_SCORE = "presentation_score";
    public static String SA_PRESENTATION_COMMENT = "presentation_comment";
    public static String SA_PRESENTATION_ACTION = "presentation_action";

    public static String SA_SKILLS_SWITCH = "skill_switch";
    public static String SA_SKILLS_SCORE = "skill_score";
    public static String SA_SKILLS_COMMENT = "skill_comment";
    public static String SA_SKILLS_ACTION = "skill_action";

    public static String SA_PASS_SWITCH = "pass_switch";
    public static String SA_PASS_SCORE = "pass_score";
    public static String SA_PASS_COMMENT = "pass_comment";
    public static String SA_PASS_ACTION = "pass_action";

    public static String SA_VISITOR_SWITCH = "visitor_switch";
    public static String SA_VISITOR_SCORE = "visitor_score";
    public static String SA_VISITOR_COMMENT = "visitor_comment";
    public static String SA_VISITOR_ACTION = "visitor_action";

    public static String SA_TRAINING_SWITCH = "training_switch";
    public static String SA_TRAINING_SCORE = "training_score";
    public static String SA_TRAINING_COMMENT = "training_comment";
    public static String SA_TRAINING_ACTION = "training_action";

    public static String SA_RECORD_SWITCH = "record_switch";
    public static String SA_RECORD_SCORE = "record_score";
    public static String SA_RECORD_COMMENT = "record_comment";
    public static String SA_RECORD_ACTION = "record_action";

    public static String SA_ASSIGNMENT_SWITCH = "ssignment_switch";
    public static String SA_ASSIGNMENT_SCORE = "assignment_score";
    public static String SA_ASSIGNMENT_COMMENT = "assignment_comment";
    public static String SA_ASSIGNMENT_ACTION = "assignment_action";

    public static String SA_HEALTH_SAFETY_SWITCH = "h_s_switch";
    public static String SA_HEALTH_SAFETY_SCORE = "h_s_score";
    public static String SA_HEALTH_SAFETY_COMMENT = "h_s_comment";
    public static String SA_HEALTH_SAFETY_ACTION = "h_s_action";

    public static String SA_RISK_ASS_SWITCH = "risk_assessment_switch";
    public static String SA_RISK_ASS_SCORE = "h_s_risk_score";
    public static String SA_RISK_ASS_COMMENT = "h_s_risk_comment";
    public static String SA_RISK_ASS_ACTION = "h_s_risk_action";

    public static String SA_ESCALATING_RISK_SWITCH = "escalating_switch";
    public static String SA_ESCALATING_RISK_SCORE = "h_s_escalation_score";
    public static String SA_ESCALATING_RISK_COMMENT = "h_s_escalation_comment";
    public static String SA_ESCALATING_RISK_ACTION = "h_s_escalation_action";

    public static String SA_OCCURRENCE_BOOK_SWITCH = "occurrence_switch";
    public static String SA_OCCURRENCE_BOOK_SCORE = "occurrence_score";
    public static String SA_OCCURRENCE_BOOK_COMMENT = "occurrence_comment";
    public static String SA_OCCURRENCE_BOOK_ACTION = "occurrence_action";


    public static String SA_INCIDENT_SHEET_SWITCH = "incident_switch";
    public static String SA_INCIDENT_SHEET_SCORE = "incident_score";
    public static String SA_INCIDENT_SHEET_COMMENT = "incident_comment";
    public static String SA_INCIDENT_SHEET_ACTION = "incident_action";

    public static String SA_HANDOVER_SHEET_SWITCH = "handover_switch";
    public static String SA_HANDOVER_SHEET_SCORE = "handover_score";
    public static String SA_HANDOVER_SHEET_COMMENT = "handover_comment";
    public static String SA_HANDOVER_SHEET_ACTION = "handover_action";

    public static String SA_VISITOR_BOOK_SWITCH = "contactor_switch";
    public static String SA_VISITOR_BOOK_SCORE = "contactor_score";
    public static String SA_VISITOR_BOOK_COMMENT = "contactor_comment";
    public static String SA_VISITOR_BOOK_ACTION = "contactor_action";

    public static String SA_COURIER_SWITCH = "courier_switch";
    public static String SA_COURIER_SCORE = "courier_score";
    public static String SA_COURIER_COMMENT = "courier_comment";
    public static String SA_COURIER_ACTION = "courier_action";

    public static String SA_ASSET_SWITCH = "asset_switch";
    public static String SA_ASSET_SCORE = "asset_score";
    public static String SA_ASSET_COMMENT = "asset_comment";
    public static String SA_ASSET_ACTION = "asset_action";

    public static String SA_PROPERTY_SWITCH = "property_switch";
    public static String SA_PROPERTY_SCORE = "property_score";
    public static String SA_PROPERTY_COMMENT = "property_comment";
    public static String SA_PROPERTY_ACTION = "property_action";

    public static String SA_KEY_PRESS_SWITCH = "key_press_switch";
    public static String SA_KEY_PRESS_SCORE = "key_press_score";
    public static String SA_KEY_PRESS_COMMENT = "key_press_comment";
    public static String SA_KEY_PRESS_ACTION = "key_press_action";

    public static String SA_CCTV_SWITCH = "cctv_switch";
    public static String SA_CCTV_SCORE = "cctv_score";
    public static String SA_CCTV_COMMENT = "cctv_comment";
    public static String SA_CCTV_ACTION = "cctv_action";

    public static String SA_ABNORMAL_EVENT_SWITCH = "event_switch";
    public static String SA_ABNORMAL_EVENT_SCORE = "event_score";
    public static String SA_ABNORMAL_EVENT_COMMENT = "event_comment";
    public static String SA_ABNORMAL_EVENT_ACTION = "event_action";

    public static String SA_HOUSE_KEEPING_SWITCH = "h_k_switch";
    public static String SA_HOUSE_KEEPING_SCORE = "h_k_score";
    public static String SA_HOUSE_KEEPING_COMMENT = "h_k_comment";
    public static String SA_HOUSE_KEEPING_ACTION = "h_k_action";

    public static String SA_WELFARE_SWITCH = "welfare_switch";
    public static String SA_WELFARE_SCORE = "welfare_score";
    public static String SA_WELFARE_COMMENT = "welfare_comment";
    public static String SA_WELFARE_ACTION = "welfare_action";

    public static String SA_MANAGEMENT_SWITCH = "management_switch";
    public static String SA_MANAGEMENT_SCORE = "management_score";
    public static String SA_MANAGEMENT_COMMENT = "management_comment";
    public static String SA_MANAGEMENT_ACTION = "management_action";

    public static String SA_IMPRESSION = "impression";
    public static String SA_CONVERSATION = "conversation";
    public static String SA_TOTAL_SCORE = "total_score";

    /********************** Vehicle Check Report ************/
    public static String VC_VEHICLE_REG = "registration_number";
    public static String VC_LOCATION = "location";
    public static String VC_CHECK_IN_OUT = "check_in_out";
    public static String VC_HANDOVER_NAME = "name";
    public static String VC_MILEAGE = "mileage";
    public static String VC_MILEAGE_NOTES = "notes";
    public static String VC_CLEANLINESS = "cleanliness";

    public static String VC_WHEEL_BRACE = "wheel_brace";
    public static String VC_CAR_JACK = "car_jack";
    public static String VC_KEY_SAFE = "key_box";
    public static String VC_TORCH = "torch";
    public static String VC_MOBILE = "mobile";
    public static String VC_VEHICLE_KEYS = "vehicle_keys";
    public static String VC_FUEL_CARD = "fuel_card";
    public static String VC_HAT = "hat";
    public static String VC_JACKET = "jacket";
    public static String VC_AID = "aid";
    public static String VC_FIRE_EXT = "fire_extinguisher";

    public static String VC_FUEL_LEVEL = "fuel_level";
    public static String VC_OIL_LEVEL = "oil_level";
    public static String VC_RADIATOR = "radiator";
    public static String VC_WASHER = "washer";
    public static String VC_TYRE = "tyre";
    public static String VC_INTERNAL_LIGHTS = "internal_light";
    public static String VC_LIGHTS= "lights";
    public static String CAR_BITMAP= "carBitmap";


    /********************** Vehicle Inspection Report ************/
    public static String VC_DESC_USE = "description";
    public static String VC_CLEAN_INSIDE = "clean_inside";
    public static String VC_CLEAN_OUTSIDE = "clean_outside";
    public static String VC_FRONT_LICENCE = "front_license";
    public static String VC_REAR_LICENCE = "rear_license";
    public static String VC_SPARE_TYRE = "spare_tyre";

    public static String VC_PASS_SIDE_BODY= "p_side_body";
    public static String VC_DRIVER_SIDE_BODY= "d_side_body";
    public static String VC_FRONT_BODY= "f_side_body";
    public static String VC_REAR_BODY= "r_side_body";
    public static String VC_ROOF= "roof";
    public static String VC_WINDSCREEN= "windscreen";
    public static String VC_WINDOWS= "windows";
    public static String VC_DRIVER_SIDE_MIRROR= "d_side_mirror";
    public static String VC_PASS_SIDE_MIRROR= "p_side_mirror";
    public static String VC_FRONT_WHEEL= "wheel_front";
    public static String VC_REAR_WHEEL= "wheel_rear";
    public static String VC_PATROL_CAP= "cap_door";


    public static String VC_HORN= "horn";
    public static String scheudle_lat= "scheudle_lat";
    public static String scheudle_lng= "scheudle_lng";
    public static String scheudle_psid= "scheudle_psid";
    public static String scheudle_pid= "scheudle_pid";
    public static String VC_DASH= "dash";
    public static String VC_INTERIOR_MIRROR= "interior_mirror";
    public static String VC_GAUGE = "guage";
    public static String VC_WIPER= "wiper";
    public static String VC_FRONT_SEAT= "front_seat";
    public static String VC_ROW_SEAT= "row_seat";
    public static String VC_FLOOR= "carpet";
    public static String VC_HEADLINER= "roof_headliner";
    public static String VC_FRONT_SEAT_BELT= "f_belt";
    public static String VC_REAR_SEAT_BELT= "r_belt";
    public static String VC_INTERIOR_LIGHTS= "interior_light";


    public static String VC_OIL= "oil";
    public static String VC_STEERING_FLUID= "s_fluid";
    public static String VC_BREAK_FLUID= "b_fluid";
    public static String VC_WATER_COOLANT= "coolant";
    public static String VC_BATTERY= "battery";
    public static String VC_HEATER= "h_ac";
    public static String VC_LIGHT= "light";
    public static String VC_INDICATOR= "indicatior";
    public static String VC_REV_LIGHTS= "r_light";
    public static String VC_HAZARD_LIGHTS= "h_light";
    public static String VC_fUEL_TANK= "fuel";

    public static String VC_MILEAGE_IN= "mileage_in";
    public static String VC_MILEAGE_OUT= "mileage_out";
    public static String VI_MILEAGE_NOTES= "mileage_note";
    public static String VC_NAME_CHK_IN= "check_in_name";
    public static String VC_LOC_CHK_IN= "check_in_location";
    public static String VC_DATE_CHK_IN= "check_in_date";
    public static String VC_TIME_CHK_IN= "check_in_time";
    public static String VC_NAME_CHK_OUT= "check_out_name";
    public static String VC_LOC_CHK_OUT= "check_out_location";
    public static String VC_DATE_CHK_OUT= "check_out_date";
    public static String VC_TIME_CHK_OUT= "check_out_time";

    public static String DRIVER_NAME = "";
    public static String GATE_NUM = "";
    public static String START_FORM_TIME = "";
    public static String VEHICLE_COMPANY = "";
    public static final int vehicleType = 1;
    public static final int bulk = 2;
    public static final int others = 3;
    public static final int vehicleSafty = 4;
    public static final int vehicleClocs = 5;
    public static final int vehicleHS2 = 6;
    public static final int vehicleEK = 7;
    public static final int vehicleFors = 8;
    public static final int loadSecurity = 9;
    public static final int driverSafty = 10;

    public static final int RESPONSE_NO = 0;
    public static final int RESPONSE_OK = 1;
    public static final int RESPONSE_NA = 2;

    public static int PREBOOK_STATUS = 0;
    public static int PREBOOK_ID = 0;
    public static String QUESTIONS = null;
    public static int CHANGE_VEHICLE_TYPE = 0;
    public static int CHANGE_BULK_TYPE = 0;

    public static final int SURVEY_FORM_NAME_VIEW_TYPE = 0;
    public static final int SURVEY_FORM_COMMENT_VIEW_TYPE = 1;
    public static final int SURVEY_FORM_RATING_VIEW_TYPE = 2;
    public static final int SURVEY_FORM_YES_NO_VIEW_TYPE = 3;

    public static int IS_DELIVERY = 1;


    public static final String BROADCAST_DETECTED_ACTIVITY = "activity_intent";
    public static final int CONFIDENCE = 70;

    //book_on_status used for chat book-on status
    public static final String BOOK_ON_STATUS = "book_on_status";
    public static final int CHAT_DATE = 0;
    public static final int CHAT_MOBILE = 1;
    public static final int CHAT_SERVER = 2;
    public static final int THREAD_STATUS_CLOSE = 3;
    public static final String WELCOME_MSG = "chat_welcome_msg";

    public static final String QR_IMAGE = "qr_image";


    public static final String IS_SURVEY_FORM_SUBMIT = "survey_form";

    public static final String APP_THEME_PRIMARY_COLOR = "app_theme_clr";
    public static final String APP_THEME_SECONDARY_COLOR = "app_theme_clr2";
    public static final String APP_DISABLE_PRIMARY_COLOR = "app_disable_clr";
    public static final String APP_DISABLE_SECONDARY_COLOR = "app_disable_clr2";

    public static final String APP_TAG_LINE = "app_tag_line";
    public static final String DEFAULT_APP_COLOR = "#08a27b";
    public static final String DEFAULT_APP_COLOR2 = "#26bf62";
    public static final String DEFAULT_APP_DISABLE_COLOR = "#DCDCDC";
    public static final String DEFAULT_APP_DISABLE_COLOR2 = "#FFFFFF";

    // Profile Constants
    public static final String PROFILE = "profile";
    public static final String PROF_IMAGE = "profile_image";
    public static final String PROF_D_NAME = "profile_display_name";
    public static final String PROF_PIN = "profile_pin";
    public static final String PROF_EMAIL = "profile_email";
    public static final String PROF_CONTACT = "profile_contact";
    public static final String PROF_ADDRESS = "profile_address";
    public static final String PROF_DOB = "profile_dob";

    public static final String NOT_PREMISES = "You are not in the premises of QR.";
    public static final String INVALID_QR = "Invalid QR-Code scanned.";

}
