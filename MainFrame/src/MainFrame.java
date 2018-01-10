import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * ���ʾ��
 * 
 * @author jianggujin
 *
 */
public class MainFrame extends JFrame
{
   // Ĭ�ϱ��ģ��
   private DefaultTableModel model = null;
   private JTable table = null;

   private JButton addBtn = null;

   public MainFrame()
   {
      super("TableDemo");
      String[][] datas = {};
      String[] titles = { "","" };
      model = new DefaultTableModel(datas, titles);
      table = new JTable(model);

      addBtn = new JButton("�������");
      addBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            model.addRow(new String[] { getRandomData(), getRandomData() });
         }
      });

      add(addBtn, BorderLayout.NORTH);
      add(new JScrollPane(table));

      setSize(400, 300);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);

   }

   public static void main(String[] args)
   {
      new MainFrame();
   }

   /**
    * �������ַ���,�÷��������ڻ������ַ��������Ժ���
    * 
    * @return
    */
   private String getRandomData()
   {
      String source = "0123456789abcdefghijklmnopqrstuvwxyz";
      int len = source.length();
      Random random = new Random(System.currentTimeMillis());
      return MessageFormat.format("{0}{0}{0}", source.charAt(random.nextInt(len)));
   }
}