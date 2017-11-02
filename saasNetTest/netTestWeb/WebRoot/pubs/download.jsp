<%
    String inputfilename = request.getParameter("filename");
    String filename = "";
    String minetype = "text/plain";
    String realpath = request.getSession().getServletContext().getRealPath("/");
    if("question_txt_demo.txt".equals(inputfilename)){
    	filename = realpath+"/demoFile/question/question_txt_demo.txt";
    }else if("question_excel_demo.xls".equals(inputfilename)){
    	filename = realpath+"/demoFile/question/question_excel_demo.xls";
    	minetype = "application/vnd.ms-excel";
    }else if("paper_import_demo.txt".equals(inputfilename)){
    	filename = realpath+"/demoFile/paper/paper_import_demo.txt";
    }else {
    	// error, not support the file
    }
    
    byte[] b = new byte[1024];
    response.setContentType(minetype);  
    response.setHeader("Content-Disposition","attachment;filename = "+inputfilename);
    java.io.File file = new java.io.File(filename);
    long fileLength = file.length();
    String length = String.valueOf(fileLength);
    response.setHeader("Content_Length", length);
    
    java.io.FileInputStream in = null;
    java.io.OutputStream output = null;
    try{
       in = new java.io.FileInputStream(file);
       int n = 0;
       output = response.getOutputStream();
       while((n = in.read(b)) != -1) {
    	   output.write(b, 0, n);
       }
       output.flush();
    }catch(Exception e) {
        e.printStackTrace();
    }
    finally
    {
        if(in != null)
        {
        	in.close();
        	in = null;
        }
        if(output != null)
        {
            output.close();
            output = null;
        }
    }
%>