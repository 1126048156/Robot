package MainFrame.dao;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





public class robotdao {
    public void listener(JButton submit,JTextArea  enterQuestion,List list,BubbleModel mModel)
    {
    	
    	submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	List list2=new ArrayList<>();
                String answer = new String();
                String q = enterQuestion.getText().trim();
                list.add(q);
                if(q.isEmpty())
                	JOptionPane.showMessageDialog(null, "发送内容不能为空，请重新输入","发送失败", JOptionPane.ERROR_MESSAGE);
                else{
                	  try { 
                		 connection con=new connection();
                		 if(con.isConnect())
                		 {
                         answer = machine(q);;   
                		 }
                		 else
                		 {
                			 answer=readmysql(q);
                		 }
                         int j=list.size();
                         list.add(answer);
                         for( int i=0;i<list.size();i++)
                           {
                        	 if(i%2==0)
                        	 {
                        		 list2.add("我："+list.get(i));                        		 
                        	 }
                        	 else
                        		 list2.add("机器人："+list.get(i));                       	 
                            }                                             
                         for (int i = j-1; i < list2.size(); i++) {  
                        	 if(i<list2.size()-1)
                        		 insertsql(list.get(i).toString(),list.get(i+1).toString());
              	           mModel.addRow(list2.get(i).toString());              	     
              	           }
                	     
                		
                         enterQuestion.setText("");
                		 
                                           
                      } catch (IOException e1) {
                          e1.printStackTrace();
                      } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}                                   
                }
            }
        });        
        enterQuestion.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10 || e.getKeyCode()==38) {
                    String answer = new String();
                    String q = enterQuestion.getText();
                    try {
                        answer = machine(q);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                   
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
    }
    public static String machine(String questiton) throws IOException {      
    	String q=URLEncoder.encode(questiton,"utf-8");
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	String serverAddr="http://www.tuling123.com/openapi/api?key="
				+ "dfbf7c71042b401c91d64e037a7924e2&info="+q;
    	HttpGet httpget = new HttpGet(serverAddr);
		//3.执行方法，获得响应
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		//4.处理得到的http实体
		String responseStr=EntityUtils.toString(entity);		
		JsonObject obj= new JsonParser().parse(responseStr).
				getAsJsonObject();  
		//(2)取对象中的字段，根据类型进行返回
		String code=obj.get("code").getAsString();
		//System.out.println(code);
		String code2="";
		if(code.equals("100000"))
		{
			code2=obj.get("text").getAsString();	
		}
		
		else if(code.equals("200000"))
		{
			String a=obj.get("text").getAsString();	
			String b=obj.get("url").getAsString();
			code2=a+"\n"+b;			
		}
		else if(code.equals("302000"))
		{			
			JsonArray result= obj.getAsJsonArray("list");
			  Iterator<JsonElement>  li = result.iterator(); 
			String responseStr2=li.next().toString();		
			JsonObject obj2= new JsonParser().parse(responseStr2).
						getAsJsonObject(); 
			String b=obj2.get("article").getAsString();
			String c=obj2.get("source").getAsString();		
			String e=obj2.get("detailurl").getAsString();
			code2=b+"\n"+c+"\n"+e;					
		}
		else if(code.equals("308000"))
		{
			JsonArray result= obj.getAsJsonArray("list");
			  Iterator<JsonElement>  li = result.iterator(); 
			String responseStr2=li.next().toString();		
			JsonObject obj2= new JsonParser().parse(responseStr2).
						getAsJsonObject(); 
			String b=obj2.get("name").getAsString();
			String d=obj2.get("info").getAsString();
			String e=obj2.get("detailurl").getAsString();	
			code2=b+"\n"+d+"\n"+e;		
		}
		else
			code2="抱歉我的主人，还没有这个功能哟";
	    return code2;		     
    }
    public static String readmysql(String question) throws Exception{
    	boolean flag=true;
    	String code2="";
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/robot?useSSL=false",
				"root","x77x09487");
		PreparedStatement ps=con.prepareStatement("select * from conversation where my like '%%"+question+"%%'");
		ResultSet rs=ps.executeQuery();
    	while(rs.next())
    	{
    		
    			code2=rs.getNString("robot");
    			System.out.println(code2);
    			flag=false;
    		
    	}
    	if(flag)
    	{
    		code2="抱歉主人，我没有该数据的记录！";
    	}
		return code2;
    }
    public static void insertsql(String a,String b) throws Exception{
    	boolean flag=true;
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/robot?useSSL=false",
				"root","x77x09487");
		PreparedStatement ps=con.prepareStatement("select my from conversation where my like '%%"+a+"%%'");
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			
			if(rs.getString("my").equals(a))
				flag=false;
			
		}
		if(flag)
		{
			PreparedStatement ps2=con.prepareStatement("Insert into conversation values('"+a
					+ "','"+b+"')");		 
			ps2.executeUpdate();		
			ps2.close();
		}		
		ps.close();
		con.close();
    }
}
