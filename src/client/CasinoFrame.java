package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

public class CasinoFrame extends JFrame {
	private JButton buttonBid;
    private JButton buttonCash;
    private JButton buttonConnect;
    private JButton buttonJoin;
    private JButton buttonLeave;
    private JButton buttonList;
    private JButton buttonState;
    private JButton buyButton;
    private JTextField fieldBid;
    private JTextField fieldPseudo;
    private JList tableList;
    private JList gameList;
    private JLabel labelCash;
    private JLabel labelState;
    private JScrollPane listGamePanel;
    private JScrollPane listTablesPanel;
    private ClientCore client;
    
    public CasinoFrame() {
        initComponents();
    }
    public CasinoFrame(ClientCore client) {
        this.client = client;
    	initComponents();
    	this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        fieldPseudo = new JTextField();
        listTablesPanel = new JScrollPane();
        tableList = new JList();
        buttonConnect = new JButton();
        listGamePanel = new JScrollPane();
        gameList = new JList();
        fieldBid = new JTextField();
        buttonBid = new JButton();
        buttonState = new JButton();
        labelState = new JLabel();
        buttonLeave = new JButton();
        buttonList = new JButton();
        buttonJoin = new JButton();
        buyButton = new JButton();
        labelCash = new JLabel();
        buttonCash = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fieldPseudo.setText("pseudo");
        
        /*
        tableList.setModel(new AbstractListModel() {
            String[] strings = { "Item 60", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        */
        //listTablesPanel.setViewportView(tableList);
        buttonConnect.setText("Connect");
        
        /*
        gameList.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        */
        //listGamePanel.setViewportView(gameList);

        fieldBid.setText("bid");

        buttonBid.setText("Bid");

        buttonState.setText("State");

        labelState.setText("jLabel1");

        buttonLeave.setText("Leave");

        buttonList.setText("List");

        buttonJoin.setText("Join");

        buyButton.setText("Buy");

        labelCash.setText("jLabel1");

        buttonCash.setText("Cash");

        
        this.buttonConnect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendPseudo(fieldPseudo.getText());
			}
        	
        });
        this.buttonCash.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				labelCash.setText(String.valueOf(client.doCash()));
			}
        	
        });
        this.buyButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				client.doBuy();
			}
        });
        this.buttonBid.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				client.doBid(Integer.parseInt(fieldBid.getText()));
			}
        });
        this.buttonLeave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				client.doLeave();
			}
        });
        this.buttonList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				tableList.setModel(new AbstractListModel<String>() {
					String[] strings = client.doList();
					@Override
					public int getSize() {
						return strings.length;
					}
					@Override
					public String getElementAt(int index) {
						return strings[index];
					}
				});
				listTablesPanel.setViewportView(tableList);
			}
        });
        this.buttonJoin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gameList.setModel(new AbstractListModel<String>() {
					String[] strings = client.doJoin(tableList.getSelectedValue().toString());
					@Override
					public int getSize() {
						return strings.length;
					}
					@Override
					public String getElementAt(int index) {
						return strings[index];
					}
				});
				listGamePanel.setViewportView(gameList);
			}
        });
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(listTablesPanel)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(fieldPseudo, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(buttonConnect))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelCash)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyButton)))
                    .addComponent(buttonCash))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(buttonList)
                    .addComponent(buttonJoin))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(labelState)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(listGamePanel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(fieldBid)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonBid)))))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(buttonState, GroupLayout.Alignment.TRAILING)
                            .addComponent(buttonLeave, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPseudo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonConnect)
                    .addComponent(fieldBid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBid))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(listGamePanel, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addComponent(listTablesPanel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonList)
                        .addGap(20, 20, 20)
                        .addComponent(buttonJoin)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buyButton)
                        .addComponent(labelCash))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonState)
                        .addComponent(labelState)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCash)
                    .addComponent(buttonLeave))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info :UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CasinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CasinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CasinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CasinoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CasinoFrame().setVisible(true);
            }
        });
    }
}
