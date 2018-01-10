package MainFrame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

import MainFrame.dao.BubbleModel;
import MainFrame.dao.BubbleRenderer;
import MainFrame.dao.robotdao;
import net.miginfocom.swing.MigLayout;




public class Driver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		    BubbleModel mModel = new BubbleModel();
	        JFrame frame = new JFrame("与伊伊聊天");
	        frame.setSize(500, 400);
	        frame.setLocation(400, 200);	           
	        JPanel contentPane = new JPanel();
			contentPane.setLayout(new BorderLayout(0, 0));
	        JScrollPane scrollPane = new JScrollPane();
			contentPane.add(scrollPane, BorderLayout.CENTER);
			
			JButton submit = new JButton("发送");			
			final JTextArea  enterQuestion = new JTextArea();
			enterQuestion.setFont(new Font("宋体",Font.BOLD,15));
		    enterQuestion.setLineWrap(true);
			JPanel pnlSend = new JPanel(new MigLayout("ins 4"));			
			pnlSend.add(new JScrollPane(enterQuestion), "hmin 45px,growx,pushx");
			pnlSend.add(submit, "growy,pushy");
			contentPane.add(pnlSend, BorderLayout.SOUTH);
			
	        JTable table = new JTable();
	        table.setTableHeader(null);
	        table.setModel(mModel);	      
	        table.getColumnModel().getColumn(0).setPreferredWidth(150);
	        table.setDefaultRenderer(Object.class,new BubbleRenderer());
	        	     	        
	        scrollPane.setViewportView(table);
			table.setBackground(Color.white);
			table.setOpaque(true);
			table.setShowHorizontalLines(false);
			scrollPane.getViewport().setBackground(Color.WHITE);
							      									       
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	            	       
	        frame.add(contentPane);
	        robotdao dao=new robotdao();
	        List list=new ArrayList();
	        dao.listener(submit,enterQuestion,list,mModel);		     
	}
	
	}
	

