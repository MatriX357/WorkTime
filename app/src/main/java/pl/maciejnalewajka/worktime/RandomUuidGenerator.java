package pl.maciejnalewajka.worktime;

import java.util.UUID;

public class RandomUuidGenerator {
    static String ret;

    public String generator(){
        UUID uuid = UUID.randomUUID();
        ret = uuid.toString();
        return ret;
    }
}
