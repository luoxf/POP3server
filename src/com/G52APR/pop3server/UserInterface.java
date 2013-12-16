package com.G52APR.pop3server;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserInterface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame = new JFrame();
	
	private static JTextArea txtResult = new JTextArea();
	private static JTextField txtCmd;
	
	private static UserInterface ui = new UserInterface();
	private static String command;
	
	private static Socket socket;
	private static PrintWriter out;

	public UserInterface() {
		super();
		this.frame.setSize(800, 500);
		this.frame.setTitle("POP3 Client");
		this.frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(141, 39, 643, 422);
		this.frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(txtResult);
		txtResult.setEditable(false);
		txtResult.setAutoscrolls(true);
		DefaultCaret caret = (DefaultCaret) txtResult.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JButton btnUser = new JButton("USER");
		btnUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setCommand("USER ");
			}
		});
		btnUser.setBounds(32, 61, 89, 23);
		this.frame.getContentPane().add(btnUser);

		JButton btnPass = new JButton("PASS");
		btnPass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("PASS ");
			}
		});
		btnPass.setBounds(32, 95, 89, 23);
		this.frame.getContentPane().add(btnPass);

		JButton btnStat = new JButton("STAT");
		btnStat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("STAT");
			}
		});
		btnStat.setBounds(32, 129, 89, 23);
		this.frame.getContentPane().add(btnStat);

		JButton btnList = new JButton("LIST");
		btnList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("LIST ");
			}
		});
		btnList.setBounds(32, 163, 89, 23);
		this.frame.getContentPane().add(btnList);

		JButton btnRetr = new JButton("RETR");
		btnRetr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("RETR ");
			}
		});
		btnRetr.setBounds(32, 197, 89, 23);
		this.frame.getContentPane().add(btnRetr);

		JButton btnDele = new JButton("DELE");
		btnDele.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("DELE ");
			}
		});
		btnDele.setBounds(32, 231, 89, 23);
		this.frame.getContentPane().add(btnDele);

		JButton btnNoop = new JButton("NOOP");
		btnNoop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("NOOP");
			}
		});
		btnNoop.setBounds(32, 265, 89, 23);
		this.frame.getContentPane().add(btnNoop);

		JButton btnRset = new JButton("RSET");
		btnRset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("RSET");
			}
		});
		btnRset.setBounds(32, 299, 89, 23);
		this.frame.getContentPane().add(btnRset);

		JButton btnTop = new JButton("TOP");
		btnTop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("TOP ");
			}
		});
		btnTop.setBounds(32, 333, 89, 23);
		this.frame.getContentPane().add(btnTop);

		JButton btnUidl = new JButton("UIDL");
		btnUidl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("UIDL ");
			}
		});
		btnUidl.setBounds(32, 367, 89, 23);
		this.frame.getContentPane().add(btnUidl);

		JButton btnQuit = new JButton("QUIT");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCommand("QUIT");
			}
		});
		btnQuit.setBounds(32, 401, 89, 23);
		this.frame.getContentPane().add(btnQuit);

		txtCmd = new JTextField();
		txtCmd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						execCommand();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		txtCmd.setBounds(141, 8, 544, 20);
		this.frame.getContentPane().add(txtCmd);
		txtCmd.setColumns(10);

		JLabel lblInputCommand = new JLabel("Input Command:");
		lblInputCommand.setBounds(14, 11, 115, 14);
		this.frame.getContentPane().add(lblInputCommand);

		JButton btnExcute = new JButton("execute");
		btnExcute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					execCommand();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExcute.setBounds(695, 7, 89, 23);
		this.frame.getContentPane().add(btnExcute);

	}

	public void showWindow() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/************************* Frame definition over ************************************/

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		UserInterface.socket = socket;
	}

	private static void execCommand() throws IOException {
		if (txtCmd.getText().equals("")) {
			JOptionPane.showMessageDialog(ui.frame,
					"Cannot execute an empty command.", "Input Error",
					JOptionPane.ERROR_MESSAGE);

			return;
		}

		command = txtCmd.getText();
		txtResult.append("> " + command + "\n");
		txtCmd.setText(null);
		txtCmd.requestFocus();
		
		out.println(command);
	}

	// click button, add command to command bar
	protected void setCommand(String cmd) {
		txtCmd.setText(cmd);
		txtCmd.requestFocus();
	}

	public void printResult(String result) {
		txtResult.append(result + "\n");
	}

	public static void main(String[] args) throws IOException {
		ui.showWindow();
		ui.printResult("+OK POP3 server ready.");
		
		final int SBAP_PORT = 8888;
		try {
			socket = new Socket("localhost",SBAP_PORT);
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(ui.frame,
					"Cannot connected to server.", "Connection Refused",
					JOptionPane.ERROR_MESSAGE);
			ui.exit();
		}

		out = new PrintWriter(socket.getOutputStream(), true);
		
		Receiver receiver = new Receiver(ui, socket);
		Thread t = new Thread(receiver);
		t.start();
	}
	
	public void exit() {
		System.exit(1);
	}
}
