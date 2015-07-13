package jgitCheck;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.util.diff.Diff;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class CommonDifference {

    public static void main(String[] args) {
        try {
            Git git = Git.open( new File( "R:/Desktop/generatedifferencefiles/.git" ) );
            final LogCommand log = git.log();
            
            final Repository repo = git.getRepository();
            String head = repo.getFullBranch();
            if (head.startsWith("refs/heads/")) {
                    // Print branch name with "refs/heads/" stripped.
                    System.out.println("Current branch is " + repo.getBranch());
                    ObjectId id = repo.readOrigHead().toObjectId();
                    System.out.println("Branch " + repo.getBranch() + " points to " + git.branchList().setListMode(ListMode.REMOTE).call());
            }
         // The {tree} will return the underlying tree-id instead of the commit-id itself!
            ObjectId oldHead = repo.resolve("HEAD~1^{tree}");
            ObjectId newHead = repo.resolve("HEAD^{tree}");

            System.out.println("Printing diff between tree: " + oldHead + " and " + newHead);

            // prepare the two iterators to compute the diff between
    ObjectReader reader = repo.newObjectReader();
    CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
    oldTreeIter.reset(reader, oldHead);
    CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
    newTreeIter.reset(reader, newHead);
    ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
    DiffFormatter formatter = new DiffFormatter(arrayOutputStream );
    formatter.setRepository( repo );

    // finally get the list of changed files
    List<DiffEntry> diffs= new Git(repo).diff()
                        .setNewTree(newTreeIter)
                        .setOldTree(oldTreeIter)
                        .call();
    for( DiffEntry entry : diffs ) {
        System.out.println( "Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId() );
        formatter.format( entry );
        formatter.setContext(0);
        String diff=new String(arrayOutputStream.toByteArray(), "UTF-8");
       
       
        ObjectLoader ldr = reader.open(entry.getOldId().toObjectId()); 
       final byte[] cachedBytes = ldr.getCachedBytes(1*1024*1024); 
       RawText a = new RawText(cachedBytes);
       ObjectLoader ldrOne = reader.open(entry.getNewId().toObjectId()); 
       final byte[] cachedBytes2 = ldrOne.getCachedBytes(1*1024*1024); 
       RawText b = new RawText(cachedBytes2);

       final EditList diff2 = MyersDiff.INSTANCE.diff(RawTextComparator.DEFAULT, a, b);
       
       
       for(Edit edit:diff2)
       {
           final String extractBefore = extract(a, edit);
           final String extractAfter = extract(b, edit);
        //   System.out.println("before\n"+extractBefore+":"+"after\n"+extractAfter);

       }
       formatter.format(diff2, a, b);

       System.out.println(arrayOutputStream.toString());

       System.out.println("-----------------------------");

       Iterator<Edit> editIte = diff2.iterator();
       while (editIte.hasNext()) {
       Edit edit = editIte.next();
       System.out.println(edit.getType().toString() + " at: "
       + edit.getEndB());
       }
      
       /* //System.out.println(diff);
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append(diff);
        htmlBuilder.append(entry.getChangeType().name());
        htmlBuilder.append("</body></html>\n");

        FileWriter writer = new FileWriter( "hello.html");
        writer.write(htmlBuilder.toString());
        writer.close();*/
      }
            System.out.println("Done");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (GitAPIException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param a
     * @param b
     * @param edit
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private static String extract(RawText a, Edit edit) throws IOException, UnsupportedEncodingException {
        ByteArrayOutputStream bas = new ByteArrayOutputStream(); 
          
               a.writeLine(bas,   edit.getBeginA()); 
               bas.write('\n'); 
               a.writeLine(bas,   edit.getEndA()); 
               bas.write('\n');
               final String string = new String(bas.toByteArray(), "utf-8"); 
               System.out.println(string);
            return string;
    }

}
