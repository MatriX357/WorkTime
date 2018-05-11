package pl.maciejnalewajka.worktime;

import java.util.UUID;

public class RandomUuidGenerator {
    static String ret;

    public String generator(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
