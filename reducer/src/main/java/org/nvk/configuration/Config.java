package org.nvk.configuration;

//1 worker local
//public class Config {
//    public static final String NODE_TYPE = "REDUCER";
//    public static final String MASTER_IP = "127.0.0.1";
//    public static final int MASTER_PORT = 43254;
//    public static int REDUCER_PORT = 53254;
//    public static final int TOTAL_WORKERS = 1;
//}


//3 workers local
//public class Config {
//    public static final String NODE_TYPE = "REDUCER";
//
//    public static final String MASTER_IP = "127.0.0.1";
//
//    public static final int MASTER_PORT = 43254;
//
//    public static int REDUCER_PORT = 53254;
//
//    public static final int TOTAL_WORKERS = 3;
//}


//3 workers remote
public class Config {
    public static final String NODE_TYPE = "REDUCER";

    public static final String MASTER_IP = "192.168.11.246";

    public static final int MASTER_PORT = 43254;

    public static int REDUCER_PORT = 53254;

    public static final int TOTAL_WORKERS = 3;
}