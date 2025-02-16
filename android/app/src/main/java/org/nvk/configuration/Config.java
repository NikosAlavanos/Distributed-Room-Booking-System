package org.nvk.configuration;

//// 1 Worker local
//public class Config {
//    public static final String NODE_TYPE = "CLIENT";
//    public static final String MASTER_IP = "127.0.0.1";
//    public static final int MASTER_PORT = 43254;
//    public static final boolean NETWORK_MODE = true;
//}

import org.nvk.structures.Profile;

//
// 3 Workers - local
//
public class Config {
    public static final String NODE_TYPE = "CLIENT";
    public static final String MASTER_IP = "192.168.1.27";
    public static final int MASTER_PORT = 43254;
    public static final boolean NETWORK_MODE = true;
    public static String USERNAME = "Bob";
    public static boolean IS_OWNER = false;
    public static final Profile PROFILE = new Profile(USERNAME, IS_OWNER);
}

//
// 3 Workers - remote
//
//public class Config {
//    public static final String NODE_TYPE = "CLIENT";
//    public static final String MASTER_IP = "192.168.26.246";
//    public static final int MASTER_PORT = 43254;
//    public static final boolean NETWORK_MODE = true;
//    public static final String USERNAME = "Bob";
//    public static final boolean IS_OWNER = true;
//    public static final Profile PROFILE = new Profile(USERNAME, IS_OWNER);
//}