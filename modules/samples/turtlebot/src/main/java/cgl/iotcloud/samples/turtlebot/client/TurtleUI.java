package cgl.iotcloud.samples.turtlebot.client;


import cgl.iotcloud.client.robot.ActionController;
import cgl.iotcloud.client.robot.RootFrame;
import cgl.iotcloud.core.IOTRuntimeException;
import cgl.iotcloud.samples.turtlebot.sensor.Velocity;


public class TurtleUI {

    private TurtleClient client;

    private ActionController actController = new ActionController() {
        @Override
        public void up() {
            client.setVelocity(new Velocity(.1, 0.0, 0.0), new Velocity(0.0, 0.0, 0));
        }

        @Override
        public void down() {
            client.setVelocity(new Velocity(-.1, 0.0, 0.0), new Velocity(0.0, 0.0, 0));
        }

        @Override
        public void left() {
            client.setVelocity(new Velocity(0, 0.0, 0.0), new Velocity(0, 0.0, -.5));
        }

        @Override
        public void right() {
            client.setVelocity(new Velocity(0, 0.0, 0.0), new Velocity(0.0, 0.0, .5));
        }

        @Override
        public void stop() {
            client.setVelocity(new Velocity(0, 0.0, 0.0), new Velocity(0.0, 0.0, 0));
        }
    };

    public void start() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                client = new TurtleClient();
                try {
                    client.start();
                } catch (IOTRuntimeException e) {
                    e.printStackTrace();

                }
            }
        });

        t.start();
        RootFrame rootFrame = RootFrame.getInstance();
        rootFrame.addActionController(actController);

        rootFrame.setVisible(true);
    }

    public static void main(String[] args) {
        TurtleUI ui = new TurtleUI();

        ui.start();
    }
}
