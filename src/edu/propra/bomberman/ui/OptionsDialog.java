/**
 * 
 */
package edu.propra.bomberman.ui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Nadescha
 * 
 */
public class OptionsDialog extends JDialog implements ActionListener{
	JTextField hostnameText;
	JTextField portnameText;
	JButton Ok;
	

	/**
	 * @param arg0
	 */
	public OptionsDialog(Frame arg0) {
		super(arg0);
		setTitle("Titel");
//		this.setPreferredSize(new Dimension(400,250));
		this.getContentPane().setLayout(new GridBagLayout());
		
		JLabel hostnameLabel=new JLabel("Hostname");
		hostnameText = new JTextField();
		hostnameText.setText(Options.HostName);
		hostnameText.addActionListener(this);
		hostnameText.setPreferredSize(new Dimension(300,20));
		GridBagConstraints gbc=new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);

		this.getContentPane().add(hostnameLabel, gbc);
		gbc.gridx=1;
		this.getContentPane().add(hostnameText, gbc);
		
		
		JLabel portnameLabel = new JLabel("Portname");
		portnameText = new JTextField();
		portnameText.setText(Options.PortName);
		portnameText.addActionListener(this);
		portnameText.setPreferredSize(new Dimension (60,20));
		gbc.gridx=0;
		gbc.gridy=1;
		this.getContentPane().add(portnameLabel, gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		this.getContentPane().add(portnameText, gbc);
		
		Ok= new JButton("Ok");
		Ok.addActionListener(this);
		Ok.setPreferredSize(new Dimension(50,25));
		gbc.gridx=1;
		gbc.gridy=3;
		gbc.insets=new Insets(50, 0, 0, 0);
		gbc.anchor=GridBagConstraints.SOUTHEAST;
		this.getContentPane().add(Ok,gbc);
		
		
		
		
		this.pack();
		// TODO Auto-generated constructor stub
	}

	public OptionsDialog(int i) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent event) {
			Options.HostName=this.hostnameText.getText();
			Options.PortName=this.portnameText.getText();
			this.setVisible(false);
			
			
		
		// TODO Auto-generated method stub
		
	}

}
