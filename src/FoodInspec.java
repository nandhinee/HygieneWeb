

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.Tool;


/**
 * Servlet implementation class FoodInspec
 */
@WebServlet("/FoodInspec")
public class FoodInspec extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodInspec() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String zipcode = request.getParameter("zipcode");
			String line = "sudo";
			String hadoopLocation = "/usr/local/hadoop/hadoop-1.0.3";
			String desktop = "/home/nandhinee/Desktop";

			
			FileWriter fw = new FileWriter(desktop+"/temp.txt");
			fw.write(zipcode);
			fw.close();
			
			//String line = "sudo /home/asim/Softwares/hadoop-1.0.3/bin/hadoop jar /home/asim/Desktop/hr.jar FoodInspections input output";
						
			CommandLine commandLine5 = CommandLine.parse(line);
			commandLine5.addArgument(hadoopLocation+"/bin/hadoop");
			commandLine5.addArgument("fs");
			commandLine5.addArgument("-put");
			commandLine5.addArgument(desktop+"/temp.txt");
			commandLine5.addArgument("zip");
			
			DefaultExecuteResultHandler resultHandler5 = new DefaultExecuteResultHandler();
			DefaultExecutor executor5 = new DefaultExecutor();
			executor5.setExitValue(1);
			executor5.execute(commandLine5, resultHandler5);
			resultHandler5.waitFor();
			int exitValue5 = resultHandler5.getExitValue();
			
			
			CommandLine commandLine = CommandLine.parse(line);
			commandLine.addArgument(hadoopLocation+"/bin/hadoop");
			commandLine.addArgument("jar");
			commandLine.addArgument(desktop+"/hr.jar");
			commandLine.addArgument("FoodInspections");
			commandLine.addArgument("input");
			commandLine.addArgument("output");
			
			
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(commandLine, resultHandler);
			resultHandler.waitFor();
			int exitValue = resultHandler.getExitValue();
			
			// /home/asim/Softwares/hadoop-1.0.3/bin/hadoop fs –get output /home/asim/Desktop/
			
			CommandLine commandLine1 = CommandLine.parse(line);
			commandLine1.addArgument(hadoopLocation+"/bin/hadoop");
			commandLine1.addArgument("fs");
			commandLine1.addArgument("-get");
			commandLine1.addArgument("final.txt");
			commandLine1.addArgument(desktop);
			
			DefaultExecuteResultHandler resultHandler1 = new DefaultExecuteResultHandler();
			DefaultExecutor executor1 = new DefaultExecutor();
			executor1.setExitValue(1);
			executor1.execute(commandLine1, resultHandler1);
			resultHandler1.waitFor();
			int exitValue1 = resultHandler1.getExitValue();
			
			CommandLine commandLine2 = CommandLine.parse(line);
			commandLine2.addArgument("chmod");
			commandLine2.addArgument("-R");
			commandLine2.addArgument("777");
			commandLine2.addArgument(desktop+"/final.txt");
			
			DefaultExecuteResultHandler resultHandler2 = new DefaultExecuteResultHandler();
			DefaultExecutor executor2 = new DefaultExecutor();
			executor2.setExitValue(1);
			executor2.execute(commandLine2, resultHandler2);
			resultHandler2.waitFor();
			int exitValue2 = resultHandler2.getExitValue();
			
		    
			FileReader inputFile = new FileReader(desktop+"/final.txt");
			BufferedReader br = new BufferedReader(inputFile);
			String line4;
			ArrayList <String> resta = new ArrayList<String>();
			ArrayList <String> location = new ArrayList<String>();
	 		while ((line4 = br.readLine()) != null){
	 			String []temp = line4.split("\\t");
				//temp[0] res,loc
				resta.add(temp[0]);
				String [] resloc =temp[0].split("\\|");
				location.add(resloc[1]);
			}
			br.close();
			
			request.getSession().setAttribute( "result", resta);
			request.getSession().setAttribute( "result_loc", location);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		    
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
