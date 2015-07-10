package jgitCheck;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
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
    DiffFormatter formatter = new DiffFormatter( System.out );
    formatter.setRepository( repo );

    // finally get the list of changed files
    List<DiffEntry> diffs= new Git(repo).diff()
                        .setNewTree(newTreeIter)
                        .setOldTree(oldTreeIter)
                        .call();
    for( DiffEntry entry : diffs ) {
        System.out.println( "Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId() );
        formatter.format( entry );
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

}
