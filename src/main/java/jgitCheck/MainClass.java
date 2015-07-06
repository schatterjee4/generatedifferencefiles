package jgitCheck;


import java.io.IOException;

import org.apache.wicket.util.file.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

public class MainClass {

    public static void main(String[] args) {

       
            File gitWorkDir = new File("C:/temp/gittest/");
            Git git;
            System.out.println("hepl");
            try {
                git = Git.open(gitWorkDir);
                Repository repo = git.getRepository();
                System.out.println(repo);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
       

    }

}
