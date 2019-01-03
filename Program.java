

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.api.Git;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String repoUrl = "https://github.com/vineethnair93/helloworld";
		File f = new File("C:\\Users\\aruja.sanjay.toke\\Downloads\\GitRepo");
		deleteAll(f);
		f.delete();
		File f1 = new File("C:\\Add");
		deleteAll(f1);
		f1.delete();
		String cloneDirectoryPath = "C:\\Users\\aruja.sanjay.toke\\Downloads\\GitRepo";
		try {
			System.out.println("Cloning " + repoUrl + " into " + repoUrl);
			Git.cloneRepository().setURI(repoUrl).setDirectory(Paths.get(cloneDirectoryPath).toFile()).call();
			System.out.println("Completed Cloning");
			Copy(cloneDirectoryPath, "C:\\Add");
		} catch (Exception e) {
			System.out.println("Exception occurred while cloning repository");
			e.printStackTrace();
		}
	}
	public static void deleteAll(File fName) {
		if(fName.isDirectory()) {
			for(File f:fName.listFiles()) {
				deleteAll(f);
				f.delete();
			}
		}
	}
	public static void Copy(String source, String target) {
		File Target = new File(target);
		File Source = new File(source);
		CopyAll(Source, Target);
	}

	public static void CopyAll(File Source, File Target) {
		Target.mkdir();

		// if(Target.list().length==0) {

		// Copy each file into the new directory.
		for (File file : Source.listFiles()) {
			if (!file.isHidden()) {
				System.out.println("Copying to " + Target.getName() + "--" + file.getName());
				Path t = Paths.get(Target.getPath() + "\\" + file.getName());
				Path s = Paths.get(file.getPath());
				try {
					Files.copy(s, t, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }

				}

				// Copy each subdirectory using recursion. for(File file:Source.listFiles())
				{

					if (file.isDirectory()) {
						File TargetSubDir = new File(Target.getPath() + "//" + file.getName());
						File SourceSubDir = new File(file.getPath());
						CopyAll(SourceSubDir, TargetSubDir);
					}

				}
			}
			for (File f : Target.listFiles()) {
			f.setWritable(false);
			Path path = Paths.get(f.getPath());
			AclFileAttributeView aclAttrView = Files.getFileAttributeView(path, AclFileAttributeView.class);
		    UserPrincipalLookupService upls = path.getFileSystem().getUserPrincipalLookupService();
		    UserPrincipal user = upls.lookupPrincipalByName(System.getProperty("user.name"));
		    AclEntry allowed = AclEntry.newBuilder().setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.READ_ACL,  AclEntryPermission.READ_NAMED_ATTRS, AclEntryPermission.READ_ATTRIBUTES).setPrincipal(user).setType(AclEntryType.ALLOW).build();
		    AclEntry denied = AclEntry.newBuilder().setPermissions(AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_ACL, AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.DELETE, AclEntryPermission.EXECUTE).setPrincipal(user).setType(AclEntryType.DENY).build();
		    List<AclEntry> acl = aclAttrView.getAcl();
		    Files.setOwner(path, user);
		    acl.add(0, allowed);
		    acl.add(1, denied);
		    aclAttrView.setAcl(acl);
		}
	}
}
		}
		
	}
}