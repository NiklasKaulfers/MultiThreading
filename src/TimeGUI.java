import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for 6 timers different
 */
public class TimeGUI implements ActionListener {
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();
    private final JButton startTimer1 = new JButton("Start");
    private final JButton startTimer2 = new JButton("Start");
    private final JButton startTimer3 = new JButton("Start");
    private final JButton startTimer4 = new JButton("Start");

    /**
     * start timer 5
     * needed in EventHandlerForT5
     */
    public final JButton startTimer5 = new JButton("Start");
    private final JButton startTimer6 = new JButton("Start");

    private final JButton stopTimer1 = new JButton("STOP");
    private final JButton stopTimer2 = new JButton("STOP");
    private final JButton stopTimer3 = new JButton("STOP");
    private final JButton stopTimer4 = new JButton("STOP");
    private final JButton stopTimer5 = new JButton("STOP");
    private final JButton stopTimer6 = new JButton("STOP");

    private final JLabel timer1Label, timer2Label, timer3Label, timer4Label, timer6Label;
    /**
     * label for timer 5
     * needed in EventHandlerForT5
     */
    public JLabel timer5Label;

    private Timer t1;
    private Timer t2;
    private Timer t3;
    private Timer t4;
    private final EventHandlerForT5 ev;
    private final EventListenerForT6 ev6;
    private final Thread thread4;

    /**
     * builds the standard gui with all timers on 0
     */
    public TimeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Timer");
        frame.setSize(800, 600);

        panel.setLayout(new GridLayout(6, 3));

        timer1Label = new JLabel();
        timer1Label.setText("not started");

        t1 = new Timer();
        // implementation ActionListener
        startTimer1.addActionListener(this);
        stopTimer1.addActionListener(this);
        stopTimer1.setEnabled(false);


        panel.add(startTimer1);
        panel.add(stopTimer1);
        panel.add(timer1Label);

        timer2Label = new JLabel("not started");
        t2 = new Timer();
        Thread thread2 = new Thread(() -> {
            while(Thread.currentThread().isAlive()){
                timer2Label.setText(t2.getTime());
            }
        });
        // lambda action listener
        startTimer2.addActionListener(e -> {
            if (!t2.isAlive()){
                t2 = new Timer();
                t2.start();
                stopTimer2.setEnabled(true);
            }
            if (!t2.isRunning()){
                t2.continueTimer();
                if (!thread2.isAlive())
                    thread2.start();
            } else {
                t2.stopTimer();
                if (thread2.isAlive())
                    thread2.interrupt();
            }
        });
        stopTimer2.addActionListener(e -> {
            stopTimer2.setEnabled(false);
            t2.interrupt();
        });
        stopTimer2.setEnabled(false);

        panel.add(startTimer2);
        panel.add(stopTimer2);
        panel.add(timer2Label);

        t3 = new Timer();
        timer3Label = new JLabel("not started");
        Thread thread3 = new Thread(() -> {
            while(Thread.currentThread().isAlive()){
                timer3Label.setText(t3.getTime());
            }
        });
        // anonymous class
        startTimer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t3.isAlive()){
                    t3 = new Timer();
                    t3.start();
                    stopTimer3.setEnabled(true);
                }
                if (!t3.isRunning()){
                    t3.continueTimer();
                    if (!thread3.isAlive())
                        thread3.start();
                } else {
                    t3.stopTimer();
                    if (thread3.isAlive())
                        thread3.interrupt();
                }
            }
        });
        stopTimer3.setEnabled(false);
        stopTimer3.addActionListener(e -> {
            thread3.interrupt();
            t3.interrupt();
            stopTimer3.setEnabled(false);
        });
        panel.add(startTimer3);
        panel.add(stopTimer3);
        panel.add(timer3Label);

        // non-static inner class
        t4 = new Timer();
        timer4Label = new JLabel("not started");
        thread4 = new Thread(() -> {
            while (Thread.currentThread().isAlive()){
                timer4Label.setText(t4.getTime());
            }
        });
        class EventHandlerForT4 implements ActionListener {
            final Thread thread4 = new Thread(){
                @Override
                public void run() {
                    while(Thread.currentThread().isAlive()){
                        timer4Label.setText(t4.getTime());
                    }
                }
            };
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (!t4.isAlive()){
                        t4 = new Timer();
                        t4.start();
                        stopTimer4.setEnabled(true);
                    }
                    if (!t4.isRunning()){
                        t4.continueTimer();
                        if (!thread4.isAlive())
                            thread4.start();
                    } else {
                        t4.stopTimer();
                        if (thread4.isAlive())
                            t4.stopTimer();
                    }
            }
        }
        startTimer4.addActionListener(new EventHandlerForT4());
        stopTimer4.addActionListener(this);

        stopTimer4.setEnabled(false);
        panel.add(startTimer4);
        panel.add(stopTimer4);
        panel.add(timer4Label);

        ev = new EventHandlerForT5();
        Thread thread5 = new Thread(() -> {
            while (Thread.currentThread().isAlive()){
                if (ev.isActive()){
                    stopTimer5.setEnabled(true);
                    timer5Label.setText(ev.getTime());
                } else {
                    if (ev.getTime().equals("00:00")) {
                        stopTimer5.setEnabled(false);
                        timer5Label.setText("00:00");
                    }
                }
            }
        });
        thread5.start();
        timer5Label = new JLabel("not started");
        startTimer5.addActionListener(ev);
        stopTimer5.addActionListener(this);
        stopTimer5.setEnabled(false);
        panel.add(startTimer5);
        panel.add(stopTimer5);
        panel.add(timer5Label);


        timer6Label = new JLabel("not started");
        ev6 = new EventListenerForT6();
        Thread thread6 = new Thread(() -> {
            while (Thread.currentThread().isAlive()){
                if (ev6.getTime() != null) {
                    timer6Label.setText(ev6.getTime());
                } else {
                    timer6Label.setText("not started");
                }
            }
        });
        thread6.start();
        startTimer6.addActionListener(ev6);
        stopTimer6.addActionListener(this);

        panel.add(startTimer6);
        panel.add(stopTimer6);
        panel.add(timer6Label);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * action listeners
     * @param e the event to be processed
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread thread1 = new Thread(() -> {
            while (Thread.currentThread().isAlive()) {
                timer1Label.setText(t1.getTime());
            }
        });

        if (e.getSource() == startTimer1) {
            if (!t1.isAlive()) {
                t1 = new Timer();
                t1.start();
                stopTimer1.setEnabled(true);
            }
            if (!t1.isRunning()) {
                thread1.start();
                t1.continueTimer();
            } else {
                thread1.interrupt();
                t1.stopTimer();
            }
        }
        if (e.getSource() == stopTimer1) {
            t1.interrupt();
            thread1.interrupt();
            try {
                thread1.join();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            t1 = new Timer();
            stopTimer1.setEnabled(false);
        }
        if (e.getSource() == stopTimer4){
            thread4.interrupt();
            stopTimer4.setEnabled(false);
        }
        if (e.getSource() == stopTimer5){
            ev.interruptTimer();
        }
        if (e.getSource() == stopTimer6){
            ev6.interruptTimer();
        }
    }

    /**
     * ActionListener for timer 6
     * is a static inner class
     */
    static class EventListenerForT6 implements ActionListener{
        Timer t = new Timer();

        /**
         * start button for t6 with its features
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!t.isAlive()){
                t = new Timer();
                t.start();
            }
            if (!t.isRunning()){
                t.continueTimer();

            } else {
                t.stopTimer();
            }
        }

        /**
         * gets the Time for output
         * @return formated time
         */
        public String getTime(){
            if (t.isAlive()) return t.getTime();
            else return "00:00";
        }

        /**
         * interrupts the timer
         */
        public void interruptTimer(){
            t.interrupt();
        }
    }
}
