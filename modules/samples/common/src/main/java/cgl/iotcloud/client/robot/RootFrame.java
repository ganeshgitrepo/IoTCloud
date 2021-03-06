package cgl.iotcloud.client.robot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.lang.Exception;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @RootFrame : Swing UI to control robot and collect sensor data
 *	
 */
public class RootFrame extends JFrame {
	private static RootFrame rootFrame;

	//To Test UI.
	public static void main(String args[]){
		RootFrame frame = RootFrame.getInstance();
		frame.show();
	}
	
	public static RootFrame getInstance() {
		if(rootFrame == null)
			rootFrame = new RootFrame();
		return rootFrame;
	}

	/**
	 * Creates new form SwingClient
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private RootFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		try{
			UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(RootPanel.getInstance(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					);
			layout.setVerticalGroup(
					layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(RootPanel.getInstance(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					);

			pack();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

    /**
     * adds ActionController to add controller
     * that controls the robot's movement
     */
    public void addActionController(final ActionController controller) {
        ControlPanel.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.down();
            }
        });

        ControlPanel.getStarightButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.up();
            }
        });

        ControlPanel.getLeftButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.left();
            }
        });

        ControlPanel.getRightButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.right();
            }
        });

        ControlSessionPanel.getInstance().getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.stop();
            }
        });
    }
    
    /**
     * adds SensorDataController to initiate and stop 
     * collecting data from sensors.
     */
    public void addSensorDataController(final SensorDataController controller){
    	ControlSessionPanel.getInstance().getStartButton().addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			if(SensorsListPanel.getInstance().getSensorSelected() == null){
    				JOptionPane.showMessageDialog(RootFrame.getInstance(), "Select a sensor.", "WARNING", 1, null);
    			}else{
    				controller.start(SensorsListPanel.getInstance().getSensorSelected());
    			}
    		}
    	});

    	ControlSessionPanel.getInstance().getStopButton().addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			controller.stop(SensorsListPanel.getInstance().getSensorSelected());
    		}
    	});
    }

    /**
     * adds sensor to UI
     * @param sensorName
     */
    public void addSensor(String sensorName){
    	SensorsListPanel.getInstance().addSensor(sensorName);
    }

    /**
     * removes sensor from UI.
     * @param sensorName
     */
    public void removeSensor(String sensorName){
    	SensorsListPanel.getInstance().removeSensor(sensorName);
    }
    /**
     * updates sensorData Panel with data from sensor.
     * @param msg
     */
    public void update(String msg){
    	SensorDataContainerPanel.getInstance().updateData(msg);
    }

    public void setImage(BufferedImage im) {
        SensorDataContainerPanel.getInstance().setImage(im);
    }

    public JPanel getDataContainer() {
        return SensorDataContainerPanel.getInstance();
    }
}


//main control panel 
class ControlContainerPanel extends JPanel implements RobotUIPanelBuilder {
	private static ControlContainerPanel conContainerPanel;

	public static ControlContainerPanel getInstance(){
		if(conContainerPanel == null)
			conContainerPanel = new ControlContainerPanel();
		return conContainerPanel;
	}

	private ControlContainerPanel(){
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		addComponents();
	}

	@Override
	public void addComponents() {
		GroupLayout conContainerPanelLayout = new GroupLayout(this);
		this.setLayout(conContainerPanelLayout);
		conContainerPanelLayout.setHorizontalGroup(
				conContainerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(conContainerPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(ControlPanel.getInstance(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
						.addComponent(ControlSessionPanel.getInstance(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
						.addComponent(ControlTitlePanel.getInstance(), GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
				);
		conContainerPanelLayout.setVerticalGroup(
				conContainerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(conContainerPanelLayout.createSequentialGroup()
						.addComponent(ControlTitlePanel.getInstance(), GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(conContainerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(ControlSessionPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(ControlPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(19, 19, 19))
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout conContainerPanelLayout = (GroupLayout)conContainerPanel.getLayout();
		conContainerPanelLayout.removeLayoutComponent(ControlPanel.getInstance());
		conContainerPanelLayout.removeLayoutComponent(ControlTitlePanel.getInstance());
		conContainerPanelLayout.removeLayoutComponent(ControlSessionPanel.getInstance());
	}
}

class ControlPanel extends JPanel implements RobotUIPanelBuilder {
	private static ControlPanel conPanel;

	private static JButton starightButton = new JButton("UP");
	private static JButton leftButton = new JButton("LEFT");
	private static JButton backButton = new JButton("BACK");
	private static JButton rightButton = new JButton("RIGHT");

	public static ControlPanel getInstance(){
		if(conPanel == null)
			conPanel = new ControlPanel();
		return conPanel;
	}

	private ControlPanel(){
		this.setBackground(new java.awt.Color(225, 222, 222));
		this.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.darkGray, java.awt.Color.gray));
		this.addComponents();
	}

	@Override
	public void addComponents() {

		GroupLayout conPanelLayout = new GroupLayout(this);
		this.setLayout(conPanelLayout);
		conPanelLayout.setHorizontalGroup(
				conPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(conPanelLayout.createSequentialGroup()
						.addGap(123, 123, 123)
						.addGroup(conPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(starightButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(backButton, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(123, Short.MAX_VALUE))
								.addGroup(conPanelLayout.createSequentialGroup()
										.addComponent(leftButton, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(rightButton, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
				);
		conPanelLayout.setVerticalGroup(
				conPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(conPanelLayout.createSequentialGroup()
						.addComponent(starightButton)
						.addGap(2, 2, 2)
						.addGroup(conPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(leftButton)
								.addComponent(rightButton))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(backButton))
				);

	}

	@Override
	public void removeComponents() {
		GroupLayout conPanelLayout = (GroupLayout)conPanel.getLayout();
		conPanelLayout.removeLayoutComponent(starightButton);
		conPanelLayout.removeLayoutComponent(leftButton);
		conPanelLayout.removeLayoutComponent(rightButton);
		conPanelLayout.removeLayoutComponent(backButton);
	}

    public static JButton getStarightButton() {
        return starightButton;
    }

    public static JButton getLeftButton() {
        return leftButton;
    }

    public static JButton getBackButton() {
        return backButton;
    }

    public static JButton getRightButton() {
        return rightButton;
    }
}

class ControlTitlePanel extends JPanel implements RobotUIPanelBuilder{
	private static ControlTitlePanel conTitlePanel;
	private JLabel conTitleLabel = new JLabel("Control");

	public static ControlTitlePanel getInstance(){
		if(conTitlePanel == null)
			conTitlePanel = new ControlTitlePanel();
		return conTitlePanel;
	}

	private ControlTitlePanel(){
		this.setBackground(new java.awt.Color(0, 0, 0));
		conTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
		this.addComponents();
	}

	@Override
	public void addComponents() {
		GroupLayout conTitlePanelLayout = new GroupLayout(this);
		this.setLayout(conTitlePanelLayout);
		conTitlePanelLayout.setHorizontalGroup(
				conTitlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(conTitlePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(conTitleLabel)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		conTitlePanelLayout.setVerticalGroup(
				conTitlePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(conTitlePanelLayout.createSequentialGroup()
						.addComponent(conTitleLabel)
						.addGap(0, 6, Short.MAX_VALUE))
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout conTitlePanelLayout = (GroupLayout)this.getLayout();
		conTitlePanelLayout.removeLayoutComponent(conTitleLabel);
	}
}

class ControlSessionPanel extends JPanel implements RobotUIPanelBuilder{
	private static ControlSessionPanel conSessionPanel;
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");



	public static ControlSessionPanel getInstance(){
		if(conSessionPanel == null)
			conSessionPanel = new ControlSessionPanel();
		return conSessionPanel;
	}

	private ControlSessionPanel (){
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.addComponents();
	}

	@Override
	public void addComponents() {
		GroupLayout conSessionPanelLayout = new GroupLayout(this);
		this.setLayout(conSessionPanelLayout);
		conSessionPanelLayout.setHorizontalGroup(
				conSessionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(conSessionPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(conSessionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
								.addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
				);
		conSessionPanelLayout.setVerticalGroup(
				conSessionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(conSessionPanelLayout.createSequentialGroup()
						.addComponent(startButton)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(stopButton))
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout conTitlePanelLayout = (GroupLayout)this.getLayout();
		conTitlePanelLayout.removeLayoutComponent(startButton);
	}

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }
}

class SensorsListPanel extends JPanel implements RobotUIPanelBuilder{
	private static SensorsListPanel sensorsListPanel;	
	private Map<String,JButton> sensorNameToButtonMap  = new HashMap<String,JButton>() ;
	private String sensorSelected = null;
	private ParallelGroup parallelGrp;
	private SequentialGroup sequentialGrp;
	GroupLayout senPanelLayout = new GroupLayout(this);

	public static SensorsListPanel getInstance(){
		if(sensorsListPanel == null)
			sensorsListPanel = new SensorsListPanel();
		return sensorsListPanel;
	}

	public void removeSensor(String sensorName) {
		JButton sensorButton = null;
		if(sensorNameToButtonMap != null && sensorNameToButtonMap.containsKey(sensorName))
			sensorButton = sensorNameToButtonMap.remove(sensorName);
		removeSensorFromPanel(sensorButton);
	}

	private void removeSensorFromPanel(JButton sensorButton ) {
		parallelGrp = senPanelLayout.createParallelGroup();
		sequentialGrp = senPanelLayout.createSequentialGroup();
		
		Collection<JButton> sensorButtons = sensorNameToButtonMap.values();
		for(JButton _sensorButton:sensorButtons){
			parallelGrp.addComponent(_sensorButton,20, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE);
			sequentialGrp.addComponent(_sensorButton);
			sequentialGrp.addGap(20);
			
			senPanelLayout.setHorizontalGroup(senPanelLayout.createSequentialGroup().addGap(40).addGroup(parallelGrp).addGap(40));
			senPanelLayout.setVerticalGroup(sequentialGrp);
		}
	}

	public void addSensor(String sensorName) {
		JButton sensorButton = new JButton(sensorName);
		if(sensorNameToButtonMap != null)
			sensorNameToButtonMap.put(sensorName,sensorButton);
		sensorButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String sensorName = ((JButton)e.getSource()).getText();
				sensorSelected = sensorName;
			}
		});
		addSensorToPanel(sensorButton);
	}

	private void addSensorToPanel(JButton sensorButton) {
		parallelGrp.addComponent(sensorButton,20, javax.swing.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE);
		sequentialGrp.addComponent(sensorButton);
		sequentialGrp.addGap(20);
		
		senPanelLayout.setHorizontalGroup(senPanelLayout.createSequentialGroup().addGap(40).addGroup(parallelGrp).addGap(40));
		senPanelLayout.setVerticalGroup(sequentialGrp);
	}

	public String getSensorSelected() {
		return sensorSelected;
	}

	private SensorsListPanel(){
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.addComponents();
	}
	
	@Override
	public void addComponents() {
		this.setLayout(senPanelLayout);
		
		parallelGrp = senPanelLayout.createParallelGroup();
		sequentialGrp = senPanelLayout.createSequentialGroup();
		
		senPanelLayout.setHorizontalGroup(senPanelLayout.createSequentialGroup().addGap(40).addGroup(parallelGrp).addGap(40));
		senPanelLayout.setVerticalGroup(sequentialGrp);
	}

	@Override
	public void removeComponents() {
		
	}
}

class SensorContainerPanel extends JPanel implements RobotUIPanelBuilder{
	private static SensorContainerPanel senContainerPanel;

	public static SensorContainerPanel getInstance(){
		if(senContainerPanel == null)
			senContainerPanel = new SensorContainerPanel();
		return senContainerPanel;
	}

	private SensorContainerPanel (){
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		this.addComponents();
	}
	@Override
	public void addComponents() {
		GroupLayout senMainPanelLayout = new GroupLayout(this);

		this.setLayout(senMainPanelLayout);
		senMainPanelLayout.setHorizontalGroup(
				senMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(SensorTitlePanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(SensorsListPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		senMainPanelLayout.setVerticalGroup(
				senMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(senMainPanelLayout.createSequentialGroup()
						.addComponent(SensorTitlePanel.getInstance(), GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(SensorsListPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE)) 
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout sensorTitlePanelLayout = (GroupLayout)this.getLayout();
		sensorTitlePanelLayout.removeLayoutComponent(SensorTitlePanel.getInstance());
	}
}


class SensorTitlePanel extends JPanel implements RobotUIPanelBuilder{
	private static SensorTitlePanel senTitlePanel;
	private JLabel senLabel = new JLabel("Sensors");

	public static SensorTitlePanel getInstance(){
		if(senTitlePanel == null)
			senTitlePanel = new SensorTitlePanel();
		return senTitlePanel;
	}

	private SensorTitlePanel (){
		this.setBackground(new java.awt.Color(0, 0, 0));
		senLabel.setForeground(new java.awt.Color(255, 255, 255));
		this.addComponents();
	}
	@Override
	public void addComponents() {
		GroupLayout senTitlePanelLayout = new GroupLayout(this);
		this.setLayout(senTitlePanelLayout);
		senTitlePanelLayout.setHorizontalGroup(
				senTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(senTitlePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(senLabel)
						.addContainerGap(120, Short.MAX_VALUE))
				);
		senTitlePanelLayout.setVerticalGroup(
				senTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(senTitlePanelLayout.createSequentialGroup()
						.addComponent(senLabel)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout senTitleLayout  = (GroupLayout)this.getLayout();
		senTitleLayout.removeLayoutComponent(senLabel);
	}
}

class SensorDataContainerPanel extends JPanel implements RobotUIPanelBuilder{
	private static SensorDataContainerPanel senDataContainerPanel;
	private static JTextArea senData = new JTextArea();
	private JScrollPane scrollPane;

	public static SensorDataContainerPanel getInstance(){
		if(senDataContainerPanel == null)
			senDataContainerPanel = new SensorDataContainerPanel();
		return senDataContainerPanel;
	}

	private SensorDataContainerPanel (){
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		this.addComponents();
	}

    BufferedImage image;

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
	public void addComponents() {
		GroupLayout senDataMainPanelLayout = new GroupLayout(this);

		senData.setEditable(false);
		senData.setVisible(true);


		scrollPane = new JScrollPane (senData,
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.setLayout(senDataMainPanelLayout);
		senDataMainPanelLayout.setHorizontalGroup(
				senDataMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(SensorDataTitlePanel.getInstance(),GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				//.addComponent(senData,javax.swing.GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(scrollPane,javax.swing.GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		senDataMainPanelLayout.setVerticalGroup(
				senDataMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(senDataMainPanelLayout.createSequentialGroup()
						.addComponent(SensorDataTitlePanel.getInstance(), GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
						//.addComponent(senData,GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane,GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE))
				);
	}

	@Override
	public void removeComponents() {

	}

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
        repaint();
    }

    public static void updateData(String data){
		String currentData =senData.getText();
		senData.setText(currentData+"\n"+data);
	}
}


class SensorDataTitlePanel extends JPanel implements RobotUIPanelBuilder{
	private static SensorDataTitlePanel senDataTitlePanel;
	private JLabel senDataTitleLabel = new JLabel("Sensor Data");

	public static SensorDataTitlePanel getInstance(){
		if(senDataTitlePanel == null)
			senDataTitlePanel = new SensorDataTitlePanel();
		return senDataTitlePanel;
	}

	private SensorDataTitlePanel(){
		this.setBackground(new java.awt.Color(0, 0, 0));
		senDataTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
		this.addComponents();
	}

	@Override
	public void addComponents() {
		GroupLayout senDataTitlePanelLayout = new GroupLayout(this);
		this.setLayout(senDataTitlePanelLayout);
		senDataTitlePanelLayout.setHorizontalGroup(
				senDataTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(senDataTitlePanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(senDataTitleLabel)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		senDataTitlePanelLayout.setVerticalGroup(
				senDataTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(senDataTitlePanelLayout.createSequentialGroup()
						.addComponent(senDataTitleLabel)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout senDataTitleLayout  = (GroupLayout)senDataTitlePanel.getLayout();
		senDataTitleLayout.removeLayoutComponent(senDataTitleLabel);
	}
}

//panel that wraps SensorDataContainerPanel and ControlContainerPanel
class SenConPanel extends JPanel implements RobotUIPanelBuilder{
	private static SenConPanel senConPanel;

	public static SenConPanel getInstance(){
		if(senConPanel == null)
			senConPanel = new SenConPanel();
		return senConPanel;
	}

	private SenConPanel(){
		this.addComponents();
	}

	@Override
	public void addComponents() {
		GroupLayout senDataConPanelLayout = new GroupLayout(this);
		this.setLayout(senDataConPanelLayout);
		senDataConPanelLayout.setHorizontalGroup(
				senDataConPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(SensorDataContainerPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(ControlContainerPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		senDataConPanelLayout.setVerticalGroup(
				senDataConPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(senDataConPanelLayout.createSequentialGroup()
						.addComponent(SensorDataContainerPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(ControlContainerPanel.getInstance(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout senDataTitleLayout  = (GroupLayout)senConPanel.getLayout();
		senDataTitleLayout.removeLayoutComponent(SensorDataContainerPanel.getInstance());
		senDataTitleLayout.removeLayoutComponent(ControlContainerPanel.getInstance());
	}
}


class RootPanel extends JPanel implements RobotUIPanelBuilder{
	private static RootPanel rootPanel =null;

	public static RootPanel getInstance(){
		if(rootPanel == null)
			rootPanel = new RootPanel();
		return rootPanel;
	}

	private RootPanel(){
		try{
			this.addComponents();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addComponents() {
		GroupLayout rootPanelLayout = new GroupLayout(this);
		this.setLayout(rootPanelLayout);
		rootPanelLayout.setHorizontalGroup(
				rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(SenConPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(SensorContainerPanel.getInstance(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				);
		rootPanelLayout.setVerticalGroup(
				rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(SenConPanel.getInstance(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(SensorContainerPanel.getInstance(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap())
				);
	}

	@Override
	public void removeComponents() {
		GroupLayout rootPanelLayout  = (GroupLayout)this.getLayout();
		rootPanelLayout.removeLayoutComponent(SenConPanel.getInstance());
		rootPanelLayout.removeLayoutComponent(SensorContainerPanel.getInstance());
	}
	
	
	
}
