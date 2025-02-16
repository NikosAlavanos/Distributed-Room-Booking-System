package org.nvk.configuration;

//Local with 1 Worker
//public class Config {
//    public static final String NODE_TYPE = "MASTER";
//    // Master local
//    public static String MASTER_IP = "127.0.0.1";
//    public static int MASTER_PORT = 43254;
//
//    public static String[] WORKER_IPS = {"127.0.0.1"};
//    public static Integer[] WORKER_PORT = {43255};
//}

//
//Local with 2 Workers
//
//    public class Config{
//        public static final String NODE_TYPE = "MASTER";
//        public static int MASTER_PORT = 43254;
//        public static String MASTER_IP = "127.0.0.1";
//        public static String [] WORKER_IPS = { "127.0.0.1", "127.0.0.1" };
//        public static Integer [] WORKER_PORT = { 43255, 43256 };
//    }


//Local with 3 workers
//
//    public class Config{
//        public static final String NODE_TYPE = "MASTER";
//        public static int MASTER_PORT = 43254;
//        public static String MASTER_IP = "127.0.0.1";
//        public static String [] WORKER_IPS = { "127.0.0.1", "127.0.0.1", "127.0.0.1" };
//        public static Integer [] WORKER_PORT = { 43255, 43256, 43257 };
//    }


//
//Remote with 3 workers
//
public class Config {
    public static final String NODE_TYPE = "MASTER";
    public static int MASTER_PORT = 43254;
    public static String MASTER_IP = "192.168.11.246";
    public static String[] WORKER_IPS = {"192.168.11.125", "192.168.11.246", "192.168.11.73"};
    public static Integer[] WORKER_PORT = {43255, 43256, 43257};
}