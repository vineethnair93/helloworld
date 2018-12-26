
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Program {


	public static void main(String[] args) {

		Copy(args[0], args[1]);
	}

	public static void Copy(String source, String target)
    {
		File Target = new File(target);
		File Source=new File(source);

        CopyAll(Source, Target);
    }

	public static void CopyAll(File Source, File Target)
	{
		Target.mkdir();

		//		if(Target.list().length==0) {


		// Copy each file into the new directory.
		for(File file:Source.listFiles()) {
			System.out.println("Copying to "+Target.getName()+"--"+file.getName());
			Path t=Paths.get(Target.getPath()+"\\"+file.getName());
			Path s=Paths.get(file.getPath());
			try {
				Files.copy(s,t,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		 // Copy each subdirectory using recursion.
		for(File file:Source.listFiles()) {
			
			if(file.isDirectory()) {
				File TargetSubDir=new File(Target.getPath()+"//"+file.getName());
				File SourceSubDir=new File(file.getPath());
				CopyAll(SourceSubDir,TargetSubDir);
			}
			
		}

	}
}
