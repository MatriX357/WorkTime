package pl.maciejnalewajka.worktime.Stopwatch;

import pl.maciejnalewajka.worktime.ManagerApplication;

public class Stopwatch{

    private static long start;
    private static long stop;

    public static void start(String task_id) {
        start = System.currentTimeMillis();
        ManagerApplication.startTime = start;
        ManagerApplication.startedTask = task_id;
    }

    public static void  stop(String task_id) {
        start = ManagerApplication.startTime;
        stop = System.currentTimeMillis();
        ManagerApplication.startedTask = "";
    }

    public static long getTime(){
        return stop - start;
    }

    public static Boolean isStarted(String task_id){
        return ManagerApplication.startedTask.equals(task_id);
    }

    public static Boolean isStarted(){
        return !ManagerApplication.startedTask.equals("");
    }
}
