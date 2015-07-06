package jgitCheck;

import java.util.Collection;

import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;

public class HookCheckCommit implements PreReceiveHook {

    public void onPreReceive(ReceivePack arg0, Collection<ReceiveCommand> arg1) {
System.out.println("arg0"+arg0);        
    }

    

}
