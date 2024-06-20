/**
 * a Timer class
 * it makes a Thread that constantly updates a String to the time the programm has been running
 * it also supports stopping the time while running
 */
public class Timer extends Thread{
    private long startTime;
    private long accumulatedTime = 0;
    private boolean running = false;
    private String currentTime = "00:00";

    /**
     * gets String of the Time at this moment
     * @return String of Time
     */
    public String getTime(){
        return currentTime;
    }

    /**
     * runs the Thread
     * calculates the Time
     */
    @Override
    public void run(){
        while (!Thread.interrupted()) {
            if (running) {
                long elapsed = (System.currentTimeMillis() - startTime) + accumulatedTime;
                updateCurrentTime(elapsed);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * stops the thread
     */
    @Override
    public void interrupt(){
        running = false;
        super.interrupt();
    }

    /**
     * stops the timer without fully stopping the programm and saves the time
     */
    public void stopTimer(){
        if (running) {
            accumulatedTime += (System.currentTimeMillis() - startTime);
            running = false;
        }
    }

    /**
     * if the timer is stopped or not
     * @return true if timer is active
     */
    public boolean isRunning(){
        return running;
    }

    /**
     * continues the timer after it was stopped
     */
    public void continueTimer(){
        if (!running) {
            running = true;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * updates the time
     * @param elapsed the time that has passed
     */
    private void updateCurrentTime(long elapsed) {
        long timeInMins = (elapsed / 60000) % 60;
        long timeInSecs = (elapsed / 1000) % 60;

        // construct from the internet because it was faulty :D
        String firstHalf = (timeInMins < 10) ? "0" + timeInMins : Long.toString(timeInMins);
        String secondHalf = (timeInSecs < 10) ? "0" + timeInSecs : Long.toString(timeInSecs);

        this.currentTime = firstHalf + ":" + secondHalf;
    }
}