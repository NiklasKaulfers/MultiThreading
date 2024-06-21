import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the action listener in a separate file
 */
public class EventHandlerForT5 implements ActionListener {
    private Timer t5 = new Timer();
    private boolean isActive = false;

    /**
     * the actual thing that gets done
     * here starts and stops the timer
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            if (!t5.isAlive()) {
                t5 = new Timer();
                t5.start();
                isActive = true;
            }
            if (!t5.isRunning()) {
                t5.continueTimer();
                isActive = true;
            } else {
                t5.stopTimer();
                isActive = false;
            }
        }
    }

    /**
     * gets the current time
     * @return the time formated for output
     */
    public String getTime() {
        return t5.getTime();
    }

    /**
     * lets it stop the thread of t5
     */
    public void interruptTimer() {
        t5.interrupt();
    }

    /**
     * if the timer is active for checking in GUI
     * @return true if its active
     */
    public boolean isActive() {
        return isActive;
    }

    public Thread getThread() {
        return t5;
    }
}
