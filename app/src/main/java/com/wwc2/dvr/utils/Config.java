package com.wwc2.dvr.utils;

public class Config {
    public static final String KEY_BRIGHTNESS = "2 ";
    public static final String KEY_SATURATION = "3 ";
    public static final String KEY_CONTRAST = "1 ";

    public static final String CAMERA_PARAMS_NODE = "/sys/class/gpiodrv/gpio_ctrl/cam_params";

    //视频设置
    public static final String VIDEO_SETTINGS= "Video_settings";
    public static final String ACTION_VIDEO_SETTINGS= "com.wwc2_dvr_Video_settings";

    public static final int CAMERA_NUM = 4;  // It not four Camera,just four camera data stream .

    //输入
    public static final String VIDEO_SWITCH_NODE = "/sys/class/gpiodrv/gpio_ctrl/video_switch";
    //类型
    public static final String CAMERA_360_TYPE_NODE = "/sys/class/gpiodrv/gpio_ctrl/360_camtype";
    //倒车
    public static final String SYSTEM_PLATFORM_65 = "mt6765";

    public static final String CAMERA_KEY = "camera_key";
    public static final String CAMERA_SUB_KEY = "camera_Sub_key";
    public static final String CAMERA_VALUE = "camera_value";
    public static final String CAMERA_KEY_DEF = "HD";
    public static final String CAMERA_VALUE_DEF = "33";
    public static final String VIDEO_KEY = "video_key";
    public static final String VIDEO_VALUE = "video_value";

    public static final int CAMERA_REBOOT_NOT = 0;
    public static final int CAMERA_REBOOT_PREVIEW = 1;
    public static final int CAMERA_REBOOT_VIDEO = 2;//重新开启录制
    public static final int CAMERA_REBOOT_RECORD = 3;

    //拍照，1.5秒后检查结果
    public static final String CAPTURE_FILE_NAME = "wwc2.capture.file.name";

    public static final String LOG_PATH ="dvr_log/";

    public static final String ACTION_REBOOT_SYSTEM = "com.wwc2.reboot.system"; //重启广播

    //开始预览
    public static final int PREVIEW_START = 7;
    //预览完成
    public static final int PREVIEW_OVER = 8;
    //预览失败
    public static final int PREVIEW_FAILED = 9;

    /**
     * DVR模式
     */
    public static final int CAMERA_DVR = 1;
    /**
     * 倒车模式
     */
    public static final int CAMERA_REVERSING = 2;
    /**
     * 开始录像
     */
    public static final int START_RECORDING = 3;
    /**
     * 转向灯模式
     */
    public static final int TURN_LIGHT_MODE = 4;
    /**
     * 碰撞
     */
    public static final String ACTION_CRASH = "com.www2.dvr.ACTION_CRASH";
    public static final String CRASH_INTENT = "crash_intent";

    //全屏
    public static final int FULL_SCREEN = 1;
    //分屏左右
    public static final int SPLIT_SCREEN = 2;
    //分屏上下
    public static final int SPLIT_SCREEN_UP_DOWN = 3;

     //倒车
//    public static final String ACTION_MSG_SAVE_OK = "com.www2.dvr.MSG_SAVE_OK";
    public static final String FULL_TYPE = "com.www2.dvr.FULL_TYPE";
    public static final String SPLIT_TYPE = "com.www2.dvr.SPLIT_TYPE";
    public static final String SPLIT_TYPE_UP_DOWN = "com.www2.dvr.SPLIT_TYPE_UP_DOWN";
    public static final String IS_SPLIT = "split_status"; //分屏状态
    public static final int AUTO_CLOSE = 888;
    public static final long DELAY_TIME = 4000;

    public static final int MSG_WRITE_TIMER    = 601;

    public static final int MSG_WRITE_OK = 604;
    public static final int CLEARVIDEO = 605;
    public static final int TAKEPICSIGNAL = 606;
    public static final int ISAUTOCHECK_FIAG = 607;
    public static final int MSG_CHECK_FAI = 608;
    public static final int MSG_MEMORY_CHECK = 609;

    public static final int MSG_DESTROY= 610;
    public static final int MSG_SHOW_DOALOG = 611;

    public static final int MSG_SHOW_RECORD_STATUS = 612;

    //参数设置同步完成
    public static final int MSG_SYNC_OK = 700;
    public static final int MSG_NO_SIGNAL = 800;
    public static final int MSG_OK_SIGNAL = 801;

    public static final long MEMORY_CHECK_INTERVAL = 20 * 1000 ;  //20S
    public static final long ACC_DELAY_INTERVAL = 20 * 1000 ;  //20S

    public static final int MIN_FRAME_BYTE = 50; //帧率单位 为 0.1Hz
    public static final int MAX_FRAME_BYTE = 250;

    public static final int MIN_BIT_BYTE = 80; //1Kbps
    public static final int MAX_BIT_BYTE = 1024;

    /**
     * sensor
     */
    public static final String SENSOR_NODE = "/sys/devices/virtual/sensor/m_acc_misc/hitthreshold";
    public static final int SENSOR_CLOSE = 0;
    //轻微
    public static final int SENSOR_KEY_1 = 9807 * 2;
    //中等
    public static final int SENSOR_KEY_2 = 9807 * 3;
    //严重
    public static final int SENSOR_KEY_3 = 9807 * 4;
    //切换广播
    public static final String SENSOR_TYPE = "com.www2.dvr.SENSOR_TYPE";
    public static final String SENSOR_TYPE_VALUE = "com.www2.dvr.SENSOR_VALUE";

    //退出DVR
    public static final String ACTION_EXIT_DVR = "com.wwc2.dvr.exit.broadcast";

    public static final String TAKEPHOTO_V9 = "com.wwc2.action.TAKEPHOTO";
    public static final String ACTION_YY_AI = "com.wwc2.action.CAMERACHANGE";
    public static final String ACTION_YY_AI_KEY = "INDEX";

    //DVR视频播放广播
    public static final String ACTION_DVR_VIDEO = "com.wwc2.dvr.video.play";
    public static final String KEY_DVR_VIDEO = "play_status";

    /**镜像类型*/
    public static final int CAMERA_MIRROR_NOR = 0;//正常
    public static final int CAMERA_MIRROR_L_R = 1;//左右镜像
    public static final int CAMERA_MIRROR_U_D = 2;//上下镜像
    public static final int CAMERA_MIRROR_ALL = 3;//上下左右镜像
    /**录像质量*/
    public static final int RECORD_BPS_5 = 5 * 1024 * 1024;//超高清（5Mbps）
    public static final int RECORD_BPS_2 = (int) (2.5 * 1024 * 1024);//高清（2.5Mbps）
    public static final int RECORD_BPS_1 = (int) (1.25 * 1024 * 1024);//普清（1.25Mbps）
    /**录像类型*/
    //改为同步系统属性
    public static final String TYPE_QUART_STREAM = "quart_stream";  // ch010 四合一："quart_stream"
    public static final String TYPE_FOUR_STREAM = "four_stream";    // ch010 四路:"four_stream"
                                                                    // ch010 双路:"two_front"
                                                                    // ch009  "two_front"
                                                                    // ch006  "two_back"
    /**存储位置*/
    public static final int DIR_LOCAL = 1;
    public static final int DIR_LOCAL_USB0 = 14;//ACC OFF时对应USB
    public static final int DIR_LOCAL_USB1 = 15;
    public static final int DIR_LOCAL_USB2 = 16;
    public static final int DIR_LOCAL_USB3 = 17;
    public static final int DIR_LOCAL_TFCARD = 18;
    public static final String USB_PATH = "dvr_usbotg";
    public static final String SD_PATH = "dvr_sdcard";

    /**摄像头相关模式设置*/
    public static final int MODE_WWC2_DISPLAY       = 0;//显示设置
    public static final int MODE_WWC2_RECORD        = 1;//记录设置
    public static final int MODE_WWC2_CAPTURE       = 2;//拍照设置
    public static final int MODE_WWC2_H264          = 3;//推流设置
    public static final int WWC2_UNKNOW             = 4;
    public static final int WWC2_CHANNELWATERMARK   = 10;//通道水印
    public static final int WWC2_TIMEWATERMARK      = 11;//时间水印
    public static final int WWC2_GPSWATERMARK       = 12;//gps水印
    public static final int WWC2_CARDWATERMARK      = 13;//车牌水印
    public static final int WWC2_AUDIOENABLE        = 14;//是否记录声音
    public static final int WWC2_RECORD_TIMEOUT     = 15;
    public static final int WWC2_CH0_FLIP           = 16;//通道0镜像
    public static final int WWC2_CH1_FLIP           = 17;//通道1镜像
    public static final int WWC2_CH2_FLIP           = 18;//通道2镜像
    public static final int WWC2_CH3_FLIP           = 19;//通道3镜像
    public static final int WWC2_RECORD_BPS = 20;//录像质量
    public static final int WWC2_RECORD_DIR = 21;//录制视频路径
    public static final int WWC2_CAPTURE_DIR = 22;//拍照路径

    /**摄像头参数相关设置*/
    /**display_mode 显示模式*/
    public static final int DISABLE_DISPLAY = 0;
    public static final int FRONT_DISPLAY   = 1;
    public static final int BACK_DISPLAY    = 2;
    public static final int LEFT_DISPLAY    = 3;
    public static final int RIGHT_DISPLAY   = 4;
    public static final int QUART_DISPLAY   = 5;
    public static final int FOUR_DISPLAY    = 6;
    /**record_mode 录像模式*/
    public static final int DISABLE_RECORD  = 0;
    public static final int FRONT_RECORD    = 1;
    public static final int BACK_RECORD     = 2;
    public static final int LEFT_RECORD     = 3;
    public static final int RIGHT_RECORD    = 4;
    public static final int QUART_RECORD    = 5;
    public static final int FOUR_RECORD     = 6;
    public static final int START_RECORD    = 10;
    public static final int STOP_RECORD     = 11;
    /**capture_mode 拍照模式*/
    public static final int DISABLE_CAPTURE = 0;
    public static final int FRONT_CAPTURE   = 1;
    public static final int BACK_CAPTURE    = 2;
    public static final int LEFT_CAPTURE    = 3;
    public static final int RIGHT_CAPTURE   = 4;
    public static final int QUART_CAPTURE   = 5;
    public static final int FOUR_CAPTURE    = 6;
    /**h264_mode 直播模式*/
    public static final int DISABLE_H264    = 0;
    public static final int FRONT_H264      = 1;
    public static final int BACK_H264       = 2;
    public static final int LEFT_H264       = 3;
    public static final int RIGHT_H264      = 4;
    public static final int QUART_H264      = 5;
    public static final int FOUR_H264       = 6;
    public static final int UNKNOW_H264     = 10;
    public static final int START_H264      = 11;
    public static final int STOP_H264       = 12;
    /**water_mark 水印模式*/
    /**audio_enable 静音录制*/
    public static final int MODE_PARAM_CLOSE    = 0;
    public static final int MODE_PARAM_OPEN     = 1;

    // current display state ..
    public static final String DISPLAY_STATE_FOUR = QUART_DISPLAY + "";

    public static final int PREVIEW_WIDTH =1280;
    public static final int PREVIEW_HEIGHT =720;

    public static final String RECORD_STATUS_OPEN = "0";
    public static final String RECORD_STATUS_START ="1";
    public static final String RECORD_STATUS_RUNING ="2";
    public static final String RECORD_STATUS_SUCCEES ="3";
    public static final String RECORD_STATUS_FAIL ="4";
    public static final String RECORD_STATUS_CRASH ="5";//关闭状态
    public static final String RECORD_STATUS_MAX ="10";

    public static final String RECORD_CLASS= "com.wwc2.dvr.fourCamera.FourRecord";
    public static final String QUART_CLASS= "com.wwc2.dvr.fourCamera.QuartRecord";

    public static final String KEY_LOCATION = "location";
    public static final String KEY_SENSOR = "sensor";
    public static final String KEY_SAVETIME = "save_time";
    public static final String KEY_SUB_MIRROR = "sub_mirror";
    public static final String KEY_MAIN_MIRROR = "main_mirror";
    public static final String KEY_LEFT_MIRROR = "left_mirror";
    public static final String KEY_RIGHT_MIRROR = "right_mirror";
    public static final String KEY_RECORD_QUALITY = "record_quality";
    public static final String KEY_RECORD_TYPE = "platform_type";

    public static final char[] CAR_NUMBER_ARRAY = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '.',
            '京', '津', '渝', '沪', '冀', '晋', '辽', '吉', '黑', '苏', '浙', '皖', '闽', '赣', '鲁', '豫', '鄂',
            '湘', '粤', '琼', '川', '贵', '云', '陕', '甘', '青', '蒙', '桂', '宁', '新', '藏', '使', '领', '警',
            '学', '港', '澳',
    };

    public static final String VIDEO_FLAG = ".mp4";
    public static final String VIDEO_FLAG_TS = ".ts";
}
