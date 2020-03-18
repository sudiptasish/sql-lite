package com.sc.hm.sqll.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.sc.hm.sqll.config.ConfigurationUtil;
import com.sc.hm.sqll.config.ConnectionConfig;
import com.sc.hm.sqll.config.DatabaseType;
import com.sc.hm.sqll.util.JdbcUtil;
import javax.swing.JComboBox;

public class ConfigurationPanel extends JPanel {

	private final int spacing = 10;
	
	private final ConnectionPanel connPanel;
	private final int width;
	private final int height;
	
	private Font font = new Font("Arial", Font.PLAIN, 11);
	
	private JLabel displayLabel = new JLabel("Fields marked with red (*) are Mandetory");
	
	private JPanel ctxPanel;
	private JPanel credPanel;
	
	private JLabel connectionLabel;
	private JTextField connectionText;
	private JLabel dbTypeLabel;
	private JComboBox dbTypeCombo;
	private JLabel hostLabel;
	private JTextField hostText;
	private JLabel portLabel;
	private JTextField portText;
	private JLabel sidLabel;
	private JTextField sidText;
	private JLabel serviceLabel;
	private JTextField serviceText;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel pwdLabel;
	private JTextField pwdText;
	
	private JLabel messageLabel;
	
	private JButton registerButton;
	private JButton testButton;
	
	public ConfigurationPanel(ConnectionPanel connPanel, int width, int height) {
		this.connPanel = connPanel;
		this.width = width;
		this.height = height;
		
		init();
		addListener();
		addComponents();
	}

	/**
	 * Initialize the configuration panel.
	 */
	private void init() {
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		
		displayLabel.setBounds(2 * spacing, spacing, 400, 2 * spacing);
		
		ctxPanel = new JPanel();
		ctxPanel.setLayout(null);
		ctxPanel.setBounds(2 * spacing, 2 * spacing, width - 4 * spacing, 21 * spacing);
		ctxPanel.setBorder(new TitledBorder(new EtchedBorder(), "Connection Details", 0, 0, font, Color.BLUE));
		
		connectionLabel = new JLabel("Connection Name");
        connectionLabel.setForeground(Color.red);
		connectionLabel.setBounds(4 * spacing, 2 * spacing, 200, 2 * spacing);
		connectionText = new JTextField();
		connectionText.setBounds(spacing + 300 + spacing, 2 * spacing, 200, 2 * spacing);
        
        dbTypeLabel = new JLabel("Database Type");
		dbTypeLabel.setForeground(Color.red);
		dbTypeLabel.setBounds(4 * spacing, 5 * spacing, 200, 2 * spacing);
		dbTypeCombo = new JComboBox(DatabaseType.values());
		dbTypeCombo.setBounds(spacing + 300 + spacing, 5 * spacing, 200, 2 * spacing);
		
		hostLabel = new JLabel("Host");
		hostLabel.setForeground(Color.red);
		hostLabel.setBounds(4 * spacing, 8 * spacing, 200, 2 * spacing);
		hostText = new JTextField();
		hostText.setBounds(spacing + 300 + spacing, 8 * spacing, 200, 2 * spacing);
        
		portLabel = new JLabel("Port");
		portLabel.setForeground(Color.red);
		portLabel.setBounds(4 * spacing, 11 * spacing, 200, 2 * spacing);
		portText = new JTextField();
		portText.setBounds(spacing + 300 + spacing, 11 * spacing, 200, 2 * spacing);
        
		sidLabel = new JLabel("Sid");
		sidLabel.setBounds(4 * spacing, 14 * spacing, 200, 2 * spacing);
		sidText = new JTextField();
		sidText.setBounds(spacing + 300 + spacing, 14 * spacing, 200, 2 * spacing);
		
        serviceLabel = new JLabel("Service Name");
		serviceLabel.setBounds(4 * spacing, 17 * spacing, 200, 2 * spacing);
		serviceText = new JTextField();
		serviceText.setBounds(spacing + 300 + spacing, 17 * spacing, 200, 2 * spacing);
		
		credPanel = new JPanel();
		credPanel.setLayout(null);
		credPanel.setBounds(2 * spacing, 25 * spacing, width - 4 * spacing, 9 * spacing);
		credPanel.setBorder(new TitledBorder(new EtchedBorder(), "Credential Details", 0, 0, font, Color.BLUE));
		
		userLabel = new JLabel("User Name");
		userLabel.setBounds(4 * spacing, 2 * spacing, 200, 2 * spacing);
		userText = new JTextField();
		userText.setBounds(spacing + 300 + spacing, 2 * spacing, 200, 2 * spacing);
		pwdLabel = new JLabel("Password");
		pwdLabel.setBounds(4 * spacing, 5 * spacing, 200, 2 * spacing);
		pwdText = new JTextField();
		pwdText.setBounds(spacing + 300 + spacing, 5 * spacing, 200, 2 * spacing);
		
		messageLabel = new JLabel("");
		messageLabel.setBounds(2 * spacing, 35 * spacing, width - 4 * spacing, 2 * spacing);
		
		registerButton = new JButton("Register");
		registerButton.setBounds(2 * spacing + 100, 38 * spacing, 180, 2 * spacing);
		testButton = new JButton("Test Connection");
		testButton.setBounds(2 * spacing + 300, 38 * spacing, 180, 2 * spacing);
	}

	/**
	 * Add the action listeners.
	 */
	private void addListener() {
		registerButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent event) {
				ConnectionConfig config = new ConnectionConfig(connectionText.getText()
                        , DatabaseType.values()[dbTypeCombo.getSelectedIndex()]
						, hostText.getText()
						, portText.getText()
						, sidText.getText()
						, serviceText.getText()
						, userText.getText()
						, pwdText.getText());
				
				ConfigurationUtil.putConfig(config.getName(), config);
				connPanel.addNewConfigAndUpdateTree(config);
				
				ConfigurationPanel.this.reset();
			}
		});
		
		testButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent event) {
				ConnectionConfig config = new ConnectionConfig(connectionText.getText()
                        , DatabaseType.values()[dbTypeCombo.getSelectedIndex()]
						, hostText.getText()
						, portText.getText()
						, sidText.getText()
						, serviceText.getText()
						, userText.getText()
						, pwdText.getText());
				try {
					JdbcUtil.testConnection(config);
					messageLabel.setForeground(Color.GREEN);
					messageLabel.setText("Connection Successful !");
				}
				catch (SQLException e) {
					String errMsg = e.getMessage();
					if (errMsg.length() > width - 4 * spacing) {
						errMsg = errMsg.substring(0,  width - 4 * spacing - 1);
					}
					messageLabel.setForeground(Color.RED);
					messageLabel.setText(errMsg);
				}
			}
		});
	}

	/**
	 * Add all the components to this panel.
	 */
	private void addComponents() {
		//add(displayLabel);
		
		add(ctxPanel);
		add(credPanel);
		ctxPanel.add(connectionLabel);
		ctxPanel.add(connectionText);
		ctxPanel.add(dbTypeLabel);
		ctxPanel.add(dbTypeCombo);
		ctxPanel.add(hostLabel);
		ctxPanel.add(hostText);
		ctxPanel.add(portLabel);
		ctxPanel.add(portText);
		ctxPanel.add(sidLabel);
		ctxPanel.add(sidText);
		ctxPanel.add(serviceLabel);
		ctxPanel.add(serviceText);
		
		credPanel.add(userLabel);
		credPanel.add(userText);
		credPanel.add(pwdLabel);
		credPanel.add(pwdText);
		
		add(messageLabel);
		add(registerButton);
		add(testButton);
	}
	
	/**
	 * Populate the text fields of this panel taking the
	 * corresponding attributes from the config object.
	 *  
	 * @param config
	 */
	public void populate(ConnectionConfig config) {
		connectionText.setText(config.getName());
        for (DatabaseType type : DatabaseType.values()) {
            if (type == config.getDbType()) {
                dbTypeCombo.setSelectedIndex(type.ordinal());
                break;
            }
        }
        
		hostText.setText(config.getHost());
		portText.setText(config.getPort());
		sidText.setText(config.getSid());
		serviceText.setText(config.getServiceName());
		userText.setText(config.getUser());
		pwdText.setText(config.getPassword());
	}
	
	/**
	 * Enable all components.
	 */
	public void enableComponents() {
		connectionText.setEnabled(true);
        dbTypeCombo.setEnabled(true);
		hostText.setEnabled(true);
		portText.setEnabled(true);
		sidText.setEnabled(true);
		serviceText.setEnabled(true);
		userText.setEnabled(true);
		pwdText.setEnabled(true);
		
		registerButton.setEnabled(true);
	}
	
	/**
	 * Disable all components.
	 */
	public void disableComponents() {
		connectionText.setEnabled(false);
        dbTypeCombo.setEnabled(false);
		hostText.setEnabled(false);
		portText.setEnabled(false);
		sidText.setEnabled(false);
		serviceText.setEnabled(false);
		userText.setEnabled(false);
		pwdText.setEnabled(false);
		
		registerButton.setEnabled(false);
	}
	
	/**
	 * Reset all the text fields.
	 */
	public void reset() {
		connectionText.setText("");
		hostText.setText("");
		portText.setText("");
		sidText.setText("");
		serviceText.setText("");
		userText.setText("");
		pwdText.setText("");
		messageLabel.setText("");
	}
}
