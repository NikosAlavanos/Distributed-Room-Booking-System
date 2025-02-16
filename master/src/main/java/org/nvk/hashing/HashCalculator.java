package org.nvk.hashing;

import org.nvk.configuration.Config;

import java.math.BigInteger;
import java.util.UUID;

public class HashCalculator {

    public static class HashResult {
        public final String uuid;
        public final int worker_offset;
        public final String ip;
        public final int port;

        public HashResult(String uuid, int worker_offset, String ip, int port) {
            this.uuid = uuid;
            this.worker_offset = worker_offset;
            this.ip = ip;
            this.port = port;
        }
    }

    public static HashResult selectWorkerByHashing(String username, String roomname) {
        String key = username + "_"+ roomname;

        String uuid = UUID.nameUUIDFromBytes(key.getBytes()).toString();

        int n = Config.WORKER_IPS.length;

        BigInteger uuidBigInt = new BigInteger(uuid.replace("-", ""), 16);
        BigInteger result = uuidBigInt.mod(BigInteger.valueOf(n));
        int worker_offset = result.intValue(); // 0... n-1

        return new HashResult(uuid, worker_offset, Config.WORKER_IPS[worker_offset], Config.WORKER_PORT[worker_offset]);
    }
}
