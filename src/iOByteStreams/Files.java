package iOByteStreams;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Files {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
	//using bytestreams is preferred when using databases
		//the below commended is an example of a characterStream
		FileInputStream  inStream=null;  //FileReader F1=null;
		FileOutputStream outStream=null;//FileWriter f2=null
		
		try {
			inStream=new FileInputStream("C:\\Users\\Hi-tek\\Downloads\\java-tuts\\Test.txt");//same same
			outStream=new FileOutputStream("C:\\Users\\Hi-tek\\Downloads\\java-tuts\\output.txt");
			
			int content;
			while((content=inStream.read())!=-1){
				outStream.write((byte)content);//this is implicit conversion ..since content variable stores inStream as Integer
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		finally {
			if(inStream!=null){
				System.out.println("Done");
				inStream.close();
			}
			
		if(outStream!=null) {
			outStream.close();
		}
		}
	}
}


