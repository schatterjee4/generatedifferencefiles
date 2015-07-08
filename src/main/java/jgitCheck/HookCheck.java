package jgitCheck;

import java.util.Collection;

import org.eclipse.jgit.transport.PostReceiveHook;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;

public class HookCheck implements PostReceiveHook{

    public void onPostReceive(ReceivePack arg0, Collection<ReceiveCommand> arg1) {
        
        System.out.println("hhh");
    }

}
